import { useState } from "react";
import { registerUser } from "../api/api";

const Register = () => {
  const [data, setData] = useState({
    username: "",
    password: "",
    role: "USER",
    email: ""
  });

  const handleRegister = () => {
    try {
      registerUser(data);
      alert("Registered Successfully!");
      window.location.href = "/login";
    } catch (err) {
      alert("Error while registering");
    }
  };


  return (
    <div className="container-fluid" style={{ height: "100vh" }}>
      <div className="row h-100">
        <div
          className="col-md-7 d-none d-md-flex align-items-center justify-content-center text-white"
          style={{
            backgroundImage: "url('https://images.unsplash.com/photo-1554224155-8d04cb21cd6c')",
            backgroundSize: "cover",
            backgroundPosition: "center"
          }}
        >
          <div style={{ background: "rgba(0,0,0,0.6)", padding: "40px", borderRadius: "15px" }}>
            <h1>Join Home Loan System</h1>
            <p>Create your account and get access to powerful loan management tools...</p>
            <ul>
              <li>Apply Loan Easily</li>
              <li>Track EMI Schedule</li>
              <li>Prepayment Options</li>
              <li>Loan Foreclosure</li>
            </ul>
          </div>
        </div>

        <div className="col-md-5 d-flex align-items-center justify-content-center bg-light">
          <div className="card p-4 shadow-lg" style={{width: "90%", borderRadius: "20px" }}>
            <h3 className="text-center mb-3">Register</h3>
            <label>Username</label>
            <input placeholder="Enter username"
              className="form-control my-2"
              onChange={(e) => setData({ ...data, username: e.target.value })}/>

            <label>Email</label>
            <input placeholder="Enter email"
              className="form-control my-2"
              onChange={(e) => setData({ ...data, email: e.target.value })}/>

            <label>Password</label>
            <input type="password" placeholder="Enter password"
              className="form-control my-2"
              onChange={(e) => setData({ ...data, password: e.target.value })}/>

            <button className="btn btn-success w-100 mt-3" onClick={handleRegister}> Register</button>
            <div className="text-center mt-3">
              <small>Already have an account?</small>
              <br />
              <a href="/login" className="text-decoration-none fw-bold">
                Login Here
              </a>
            </div>

            <hr />

            <div className="text-center">
              <small className="text-muted">
                Secure Banking | Encrypted Passwords
              </small>
            </div>
          </div>

        </div>

      </div>
    </div>
  );
};

export default Register;