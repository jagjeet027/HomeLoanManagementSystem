import { useState } from "react";
import { loginUser } from "../api/api";

const Login = () => {
  const [data, setData] = useState({ username: "", password: "" });

  const handleLogin = async () => {
    try {
      const res = await loginUser(data);

      const userData = {
        ...res.data,
        password: data.password
      };

      localStorage.setItem("user", JSON.stringify(userData));
      window.location.href = "/";
    } catch (err) {
      alert("Something is missing ... backend error");
    }
  };

  return (
    <div className="container-fluid" style={{ height: "100vh" }}>
      <div className="row h-100">
        <div
          className="col-md-7 d-none d-md-flex align-items-center justify-content-center text-white"
          style={{
            backgroundImage: "url('https://images.unsplash.com/photo-1560518883-ce09059eeffa')",
            backgroundSize: "cover",
            backgroundPosition: "center",
            position: "relative"
          }}
        >
          <div style={{
            background: "rgba(0,0,0,0.6)",
            padding: "40px",
            borderRadius: "15px"
          }}>
            <h1>Home Loan System</h1>
            <p>
              Manage your loans, track EMI, prepay, and foreclose — all in one place.
            </p>

            <ul>
              <li>Easy Loan Application</li>
              <li>EMI Tracking Dashboard</li>
              <li>Secure Document Upload</li>
              <li>Admin Approval System</li>
            </ul>
          </div>
        </div>

        <div className="col-md-5 d-flex align-items-center justify-content-center bg-light">

          <div
            className="card p-4 shadow-lg"
            style={{
              width: "90%",
              borderRadius: "20px",
              backdropFilter: "blur(10px)"
            }}
          >
            <h3 className="text-center mb-3">Login</h3>

            <label>Username</label>
            <input
              placeholder="Enter username"
              className="form-control my-2"
              onChange={(e) =>
                setData({ ...data, username: e.target.value })}
            />

            <label>Password</label>
            <input
              type="password"
              placeholder="Enter password"
              className="form-control my-2"
              onChange={(e) =>
                setData({ ...data, password: e.target.value })}
            />

            <button className="btn btn-primary w-100 mt-3" onClick={handleLogin}>
              Login
            </button>
            <div className="text-center mt-3">
              <small>Don't have an account?</small>
              <br />
              <a href="/register" className="text-decoration-none fw-bold">
                Register Here
              </a>
            </div>

            <hr />

            <div className="text-center">
              <small className="text-muted">
                Secure Login | Trusted Banking System
              </small>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
};

export default Login;