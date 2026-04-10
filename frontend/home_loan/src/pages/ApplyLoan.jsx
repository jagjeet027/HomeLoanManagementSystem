import { useState, useEffect } from "react";
import { applyLoan, getLoanByAccount, getAccount } from "../api/api";
import { useNavigate } from "react-router-dom";

const ApplyLoan = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  const [file, setFile] = useState(null);
  const [data, setData] = useState({
    requestedAmount: "",
    tenure: "",
    salary: "",
    propertyDetails: "1BHK"
  });
  const [loading, setLoading] = useState(false);
  const [account, setAccount] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    loadUserData();
  }, []);

  const loadUserData = async () => {
    try {
      const accRes = await getAccount(user.userId);
      setAccount(accRes.data);
      try {
        const loanRes = await getLoanByAccount(accRes.data.accountId);
        setActiveLoan(loanRes.data);
      } catch {
        setActiveLoan(null);
      }

    } catch (err) {
      console.log("No account found");
    }
  };

  const handleApply = async () => {
    try {
      if (!file) {
        return alert("Please upload document");
      }
      if (!data.requestedAmount || !data.salary || !data.tenure) {
        return alert("Fill all fields");
      }
      if (data.tenure < 5 || data.tenure > 20) {
        return alert("Tenure must be between 5-20 years");
      }
      setLoading(true);
      const formData = new FormData();

      formData.append("requestedAmount", Number(data.requestedAmount));
      formData.append("salary", Number(data.salary));
      formData.append("tenure", Number(data.tenure));
      formData.append("propertyDetails", data.propertyDetails);
      formData.append("file", file);

      await applyLoan(formData);

      alert("Loan Applied Successfully!");
      setLoading(false);
      navigate('/dashboard')
      loadUserData();

    } catch (err) {
      setLoading(false);
      alert(err.response?.data || "Error");
    }
  };

  return (
    <div>
      <div
        style={{
          backgroundImage:
            "url('https://images.unsplash.com/photo-1560518883-ce09059eeffa')",
          backgroundSize: "cover",
          backgroundPosition: "center",
          height: "400px",
          position: "relative"
        }}
      >
        <div
          style={{
            background: "rgba(0,0,0,0.6)",
            height: "100%",
            color: "white",
            display: "flex",
            alignItems: "center",
            padding: "50px"
          }}
        >
          <div>
            <h1>🏠 Apply for Your Dream Home Loan</h1>
            <p style={{ maxWidth: "600px" }}>
              Get instant approval based on your salary and eligibility.
              Upload your documents and track your EMI schedule easily.
            </p>
          </div>
        </div>
      </div>
      <div className="container" style={{ marginTop: "-150px" }}>
        <div className="row">
          <div className="col-md-6 text-white">
            <div className="p-4 rounded shadow"
              style={{
                background: "rgba(0,0,0,0.7)"
              }}
            >
              <h3>Why Choose Our Loan?</h3>
              <ul>
                <li>Instant Eligibility Check</li>
                <li>Low Interest Rate (7%)</li>
                <li>Flexible Tenure (5-20 Years)</li>
                <li>Prepayment & Foreclosure Available</li>
                <li>Transparent EMI Schedule</li>
              </ul>

              <h5 className="mt-4">Eligibility Formula:</h5>
              <p>Salary x 50 = Eligible Loan Amount</p>
            </div>
          </div>

          <div className="col-md-6">
            <div
              className="card p-4 shadow-lg"
              style={{
                borderRadius: "20px",
                backdropFilter: "blur(10px)",
                background: "rgba(255,255,255,0.95)"
              }}
            >
              <h3 className="text-center mb-3">Apply Loan</h3>

              <input
                className="form-control my-2"
                placeholder="Loan Amount"
                onChange={(e) =>
                  setData({ ...data, requestedAmount: e.target.value })}
              />

              <input
                className="form-control my-2"
                placeholder="Monthly Salary"
                onChange={(e) =>
                  setData({ ...data, salary: e.target.value })}
              />

              <input
                className="form-control my-2"
                placeholder="Tenure (5–20 years)"
                onChange={(e) =>
                  setData({ ...data, tenure: e.target.value })}
              />

              <select
                className="form-control my-2"
                onChange={(e) =>
                  setData({ ...data, propertyDetails: e.target.value })}
              >
                <option>1BHK</option>
                <option>2BHK</option>
                <option>3BHK</option>
                <option>Villa</option>
              </select>

              <input
                type="file"
                className="form-control my-2"
                onChange={(e) => setFile(e.target.files[0])}
              />

              <button
                className="btn btn-success w-100 mt-3"
                onClick={handleApply}
                disabled={loading}
              >
                {loading ? "Applying..." : "Apply Loan"}
              </button>
            </div>
          </div>
        </div>
      <div className="container mt-5">
        <div className="text-center mb-5">
          <h2>Loan Process</h2>
          <p>Simple 4-step process to get your loan approved</p>
        </div>

        <div className="row text-center">
          <div className="col-md-3">
            <div className="card p-3 shadow h-100">
              <h5>1. Apply</h5>
              <p>Fill the loan application form and upload documents.</p>
            </div>
          </div>
          <div className="col-md-3">
            <div className="card p-3 shadow h-100">
              <h5>2. Verification</h5>
              <p>Admin verifies your eligibility and documents.</p>
            </div>
          </div>
          <div className="col-md-3">
            <div className="card p-3 shadow h-100">
              <h5>3. Approval</h5>
              <p>Loan gets approved and EMI schedule is generated.</p>
            </div>
          </div>

          <div className="col-md-3">
            <div className="card p-3 shadow h-100">
              <h5>4. Repayment</h5>
              <p>Pay EMI monthly or prepay anytime.</p>
            </div>
          </div>
        </div>
        <div className="mt-5">
          <div className="card shadow p-4">
            <h4>Understanding EMI</h4>
            <p>
              EMI (Equated Monthly Installment) is calculated based on your loan
              amount, interest rate, and tenure. It includes both principal and
              interest components.
            </p>

            <ul>
              <li>Fixed monthly payment</li>
              <li>Auto deduction from your account</li>
              <li>Transparent schedule available in dashboard</li>
            </ul>
          </div>
        </div>

        <div className="row mt-5 text-center">

          <div className="col-md-4">
            <div className="card shadow p-3 h-100">
              <h5>Fast Approval</h5>
              <p>Loan approval within minutes after verification.</p>
            </div>
          </div>

          <div className="col-md-4">
            <div className="card shadow p-3 h-100">
              <h5>Low Interest</h5>
              <p>Competitive interest rates starting from 7%.</p>
            </div>
          </div>

          <div className="col-md-4">
            <div className="card shadow p-3 h-100">
              <h5>Flexible Payments</h5>
              <p>Prepay or foreclose anytime with ease.</p>
            </div>
          </div>

        </div>

      </div>

      </div>
    </div>
    
  );
};

export default ApplyLoan;