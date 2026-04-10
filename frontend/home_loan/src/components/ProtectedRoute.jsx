import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = ({ role }) => {
  const user = JSON.parse(localStorage.getItem("user"));

  if (!user) return <Navigate to="/login" />;

  if (role && user.role !== role) return <Navigate to="/" />;

  return <Outlet />;
};

export default ProtectedRoute;