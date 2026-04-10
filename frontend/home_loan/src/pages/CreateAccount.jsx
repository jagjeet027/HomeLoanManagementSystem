import { useState } from "react";
import { createAccount } from "../api/api";

const CreateAccount = () => {
  const user = JSON.parse(localStorage.getItem("user"));

  const [data, setData] = useState({
    accountNumber: "",
    name: "",
    email: ""
  });

  const handleCreate = async () => {
    try {
      await createAccount(user.userId, data);

      alert("Account Created!");
      window.location.href = "/apply-loan";

    } catch (err) {
      console.error(err);
      alert("Error creating account");
    }
  };

  return (
    <div>
      <div
        style={{
          minHeight: "100vh",
          backgroundImage:
            "url('https://images.unsplash.com/photo-1560518883-ce09059eeffa')",
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundAttachment: "fixed",
          position: "relative"
        }}>
        <div
          style={{
            background: "rgba(0,0,0,0.6)",
            minHeight: "100vh",
            display: "flex",
            alignItems: "center"
          }}>
          <div className="container">
            <div className="row align-items-center">
              <div className="col-md-5">
                <div
                  className="p-4 shadow-lg"
                  style={{
                    borderRadius: "20px",
                    background: "rgba(255,255,255,0.15)",
                    backdropFilter: "blur(10px)",
                    color: "white"
                  }}>
                  <h3 className="text-center mb-3">Create Your Account</h3>
                  <input
                    placeholder="Account Number"
                    className="form-control my-2"
                    onChange={(e) =>setData({...data,accountNumber: e.target.value})}/>
                  <input
                    placeholder="Full Name"
                    className="form-control my-2"
                    onChange={(e) =>setData({...data,name: e.target.value})}/>
                  <input
                    placeholder="Email Address"
                    className="form-control my-2"
                    onChange={(e) => setData({...data,email: e.target.value})}/>

                  <button className="btn btn-primary w-100 mt-3" onClick={handleCreate}>Create Account</button>
                  <p className="text-light text-center mt-3">Required to apply for a home loan</p>
                </div>
              </div>
              <div className="col-md-7 text-white">
                <h1 className="fw-bold">Start Your Home Loan Journey</h1>
                <p className="mt-3 fs-5">
                  Creating an account is the first step toward owning your dream home.
                  Manage your loan, track EMI, and monitor your financial journey
                  all in one place.
                </p>

                <div className="mt-4">
                  <ul style={{ lineHeight: "2" }}>
                    <li>Secure and fast account creation</li>
                    <li>Easy EMI tracking</li>
                    <li>Transparent loan management</li>
                    <li>Instant eligibility check</li>
                  </ul>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
      <div className="container mt-5">
        <div className="row">
          <div className="col-md-4">
            <div className="card shadow p-3 text-center h-100">
              <img
                src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                alt="account"
                style={{ width: "60px", margin: "auto" }}
              />
              <h5 className="mt-3">Secure Account</h5>
              <p>
                Your data is protected with strong security and encryption.
              </p>
            </div>
          </div>

          <div className="col-md-4">
            <div className="card shadow p-3 text-center h-100">
              <img
                src="https://cdn-icons-png.flaticon.com/512/3062/3062634.png"
                alt="emi"
                style={{ width: "60px", margin: "auto" }}
              />
              <h5 className="mt-3">EMI Tracking</h5>
              <p>
                Easily monitor your monthly payments and loan progress.
              </p>
            </div>
          </div>
          <div className="col-md-4">
            <div className="card shadow p-3 text-center h-100">
              <img
                src="https://cdn-icons-png.flaticon.com/512/2950/2950655.png"
                alt="loan"
                style={{ width: "60px", margin: "auto" }}
              />
              <h5 className="mt-3">Loan Insights</h5>
              <p>Get detailed insights into your loan and repayment schedule.</p>
            </div>
          </div>
        </div>
        <div className="mt-5 text-center">
          <h4>Why Account is Required?</h4>
          <p style={{ maxWidth: "700px", margin: "auto" }}>
            A savings account is mandatory to process your loan. It helps
            automate EMI deductions, track repayments, and ensures a smooth
            loan lifecycle without manual intervention.
          </p>
        </div>

      </div>

    </div>
  );
};

export default CreateAccount;