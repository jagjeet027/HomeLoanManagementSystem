package com.homeLoan.services;

import com.homeLoan.model.RepaymentSchedule;
import com.homeLoan.repository.RepaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    private RepaymentScheduleRepository repo;

    public Resource exportScheduleFile(int loanId) {
        List<RepaymentSchedule> list = repo.findByLoanId(loanId);

        String fileName = "schedule_" + loanId + ".csv";
        String path = System.getProperty("user.dir") + "/" + fileName;

        try (PrintWriter writer = new PrintWriter(new File(path))) {

            writer.println("Month,EMI,Principal,Interest,Balance,Status");

            for (RepaymentSchedule s : list) {
                writer.println(
                        s.getMonth() + "," +
                                s.getEmi() + "," +
                                s.getPrincipal() + "," +
                                s.getInterest() + "," +
                                s.getOutstanding() + "," +
                                s.getStatus()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("CSV generation failed");
        }

        try {
            return new UrlResource(new File(path).toURI());
        } catch (Exception e) {
            throw new RuntimeException("File read failed");
        }
    }
}