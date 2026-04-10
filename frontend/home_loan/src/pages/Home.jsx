import { useEffect, useState } from "react";
import { getAccount, getLoanByAccount } from "../api/api";
import { Link } from "react-router-dom";

const Home = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  const [hasAccount, setHasAccount] = useState(false);
  const [hasLoan, setHasLoan] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    checkUserStatus();
  }, []);

  const checkUserStatus = async () => {
    try {
      if (!user) return;
      const acc = await getAccount(user.userId);
      setHasAccount(true);

      try {
        const loan = await getLoanByAccount(acc.data.accountId);
        if (loan.data) setHasLoan(true);
      } catch {
        setHasLoan(false);
      }

    } catch {
      setHasAccount(false);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container-fluid p-0 ">
      <div id="homeCarousel" className="carousel slide " data-bs-ride="carousel">

        <div className="carousel-inner">

          <div className="carousel-item active">
            <img
              src="https://images.unsplash.com/photo-1560518883-ce09059eeffa"
              className="d-block w-100"
              style={{ height: "400px", objectFit: "cover" }}
              alt="home loan"
            />
            <div className="carousel-caption text-start">
              <h2>Own Your Dream Home</h2>
              <p>Low interest rates & flexible EMI options</p>
            </div>
          </div>

          <div className="carousel-item">
            <img
              src="https://images.unsplash.com/photo-1600585154340-be6161a56a0c"
              className="d-block w-100"
              style={{ height: "400px", objectFit: "cover" }}
              alt="loan"
            />
            <div className="carousel-caption">
              <h2>Quick Loan Approval</h2>
              <p>Apply online in minutes</p>
            </div>
          </div>
        </div>

        <button className="carousel-control-prev" data-bs-target="#homeCarousel" data-bs-slide="prev">
          <span className="carousel-control-prev-icon"></span>
        </button>

        <button className="carousel-control-next" data-bs-target="#homeCarousel" data-bs-slide="next">
          <span className="carousel-control-next-icon"></span>
        </button>

      </div>
      <div className="container mt-5">
        <div className="row align-items-center">
          <div className="col-md-6">
            <h1 className="fw-bold mb-3">Home Loan Made Easy</h1>

            <p className="text-muted">
              Get your dream home with our easy and flexible home loan plans.
              We provide low interest rates, quick approvals, and flexible repayment options.
            </p>

            {!user && (
              <>
                <Link to="/login" className="btn btn-primary me-2">Login</Link>
                <Link to="/register" className="btn btn-success">Register</Link>
              </>
            )}

            {user && user.role === "USER" && (
              <>
                {!hasAccount && (<Link to="/create-account" className="btn btn-warning">Create Account</Link>)}
                {hasAccount && !hasLoan && (<Link to="/apply-loan" className="btn btn-success"> Apply Loan </Link>)}
                {hasLoan && ( <Link to="/dashboard" className="btn btn-info">Go to Dashboard</Link>)}
              </>
            )}
            {user && user.role === "ADMIN" && (<Link to="/admin" className="btn btn-dark">Go to Admin Panel</Link>)}

          </div>
          <div className="col-md-6 text-center">
            <img
              src="https://cdn-icons-png.flaticon.com/512/1040/1040230.png"
              alt="loan"
              style={{ width: "70%" }}
            />
          </div>
        </div>
      </div>
      <div className="container mt-5">
        <h3 className="text-center mb-4">Why Choose Us?</h3>
        <div className="row text-center">
          <div className="col-md-4">
            <div className="card shadow p-3">
              <h5>Low Interest</h5>
              <p>Affordable rates for everyone</p>
            </div>
          </div>

          <div className="col-md-4">
            <div className="card shadow p-3">
              <h5>Fast Approval</h5>
              <p>Instant loan processing system</p>
            </div>
          </div>

          <div className="col-md-4">
            <div className="card shadow p-3">
              <h5>Flexible EMI</h5>
              <p>Choose tenure as per your comfort</p>
            </div>
          </div>
        </div>
      </div>
      <div className="container mt-5 mb-5 text-center">

        <div className="card p-4 shadow bg-light">

          <h4>Start Your Journey Today </h4>

          {!user && (
            <Link to="/register" className="btn btn-success mt-2">
              Get Started
            </Link>
          )}

          {user && user.role === "USER" && hasAccount && !hasLoan && (
            <Link to="/apply-loan" className="btn btn-primary mt-2">
              Apply Loan Now
            </Link>
          )}

        </div>

      </div>

    </div>
  );
};

export default Home;