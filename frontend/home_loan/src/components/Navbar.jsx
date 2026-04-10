import { Link, NavLink, useNavigate } from "react-router-dom";

const Navbar = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("user");
    navigate("/login");
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow-lg sticky-top">
      <div className="container">
        <Link className="navbar-brand fw-bold fs-4" to="/">
          HomeLoan System
        </Link>
        <button
          className="navbar-toggler"
          data-bs-toggle="collapse"
          data-bs-target="#nav"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="nav">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <NavLink to="/" className="nav-link">
                Home
              </NavLink>
            </li>
            {user && user.role === "USER" && (
              <>
                <li className="nav-item">
                  <NavLink to="/dashboard" className="nav-link">
                    Dashboard
                  </NavLink>
                </li>
              </>
            )}
            {user && user.role === "ADMIN" && (
              <li className="nav-item">
                <NavLink
                  to="/admin"
                  className="nav-link text-warning fw-bold"
                >
                  Admin Panel
                </NavLink>
              </li>
            )}
          </ul>
          <ul className="navbar-nav ms-auto align-items-center">
            {!user && (
              <>
                <li className="nav-item">
                  <NavLink to="/login" className="nav-link">
                    Login
                  </NavLink>
                </li>

                <li className="nav-item">
                  <NavLink to="/register" className="nav-link">
                    Register
                  </NavLink>
                </li>
              </>
            )}
            {user && (
              <>
                <li className="nav-item me-2">
                  <span className="badge bg-info text-dark p-2">
                    👤 {user.username} ({user.role})
                  </span>
                </li>
                {user.role === "USER" && (
                  <li className="nav-item me-2">
                    <Link to="/apply-loan">
                      <button className="btn btn-success btn-sm">
                        Apply Loan
                      </button>
                    </Link>
                  </li>
                )}
                <li className="nav-item">
                  <button className="btn btn-danger btn-sm" onClick={logout}> Logout</button>
                </li>
              </>
            )}

          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;