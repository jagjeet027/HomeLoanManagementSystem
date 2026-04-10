package com.homeLoan.controller;

import com.homeLoan.dto.LoanApplicationRequest;
import com.homeLoan.dto.PrepaymentRequest;
import com.homeLoan.model.*;
import com.homeLoan.repository.UserRepository;
import com.homeLoan.services.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = {"http://localhost:5173"})
public class LoanController {

    private static final Logger log = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LoanApplicationService applicationService;

    @Autowired
    private LoanAccountService loanService;

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private CsvService csvService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavingAccountService savingAccountService;

    @PostMapping("/apply")
    public LoanApplication apply(
            @RequestParam("requestedAmount") Double requestedAmount,
            @RequestParam("tenure") Integer tenure,
            @RequestParam("salary") Double salary,
            @RequestParam("propertyDetails") String propertyDetails,
            @RequestParam("file") MultipartFile file,
            Principal principal
    ) {

        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is required");
        }
        Users user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        SavingAccount account = savingAccountService.getAccountByUser(user.getUserId());
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + fileName;

        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
        LoanApplicationRequest req = new LoanApplicationRequest(
                requestedAmount,
                tenure,
                salary,
                propertyDetails,
                filePath
        );

        return applicationService.applyLoan(req, account);
    }

    @GetMapping("/applications")
    public List<LoanApplication> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/account/{accountId}")
    public LoanAccount getLoanByAccount(@PathVariable int accountId) {
        return loanService.getLoanByAccountId(accountId);
    }

    @PostMapping("/approve/{id}")
    public LoanAccount approve(@PathVariable int id) {
        return applicationService.acceptEligibleAmount(id);
    }

    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable int id) {
        applicationService.rejectApplication(id);
        return "Rejected";
    }

    @GetMapping("/{id}")
    public LoanAccount getLoan(@PathVariable int id) {
        return loanService.getLoan(id);
    }

    @GetMapping("/schedule/{loanId}")
    public List<RepaymentSchedule> schedule(@PathVariable int loanId) {
        return repaymentService.getSchedule(loanId);
    }

    @PostMapping("/pay-emi/{id}")
    public String pay(@PathVariable int id) {
        repaymentService.payEmiById(id);
        return "Paid";
    }

    @PostMapping("/foreclose/{loanId}")
    public String foreclose(@PathVariable int loanId) {
        repaymentService.forecloseByLoanId(loanId);
        return "Closed";
    }

    @PostMapping("/prepay")
    public String prepay(@RequestBody PrepaymentRequest req) {
        repaymentService.prepayLoan(req.getLoanId(), req.getAmount());
        return "Prepaid";
    }

    @GetMapping("/export/{loanId}")
    public ResponseEntity<Resource> export(@PathVariable int loanId) {

        Resource file = csvService.exportScheduleFile(loanId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getFilename())
                .body(file);
    }

    @GetMapping("/document")
    public ResponseEntity<Resource> download(@RequestParam String path) {
        try {
            String decodedPath = java.net.URLDecoder.decode(path, "UTF-8");
            File file = new File(decodedPath);

            if (!file.exists()) {
                throw new RuntimeException("File not found: " + decodedPath);
            }

            Resource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + file.getName())
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error downloading file: " + e.getMessage());
        }
    }
}