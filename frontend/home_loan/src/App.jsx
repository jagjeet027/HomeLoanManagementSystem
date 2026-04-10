import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import ApplyLoan from "./pages/ApplyLoan";
import AdminPanel from "./pages/AdminPanel";
import ProtectedRoute from "./components/ProtectedRoute";
import LoanHistory from "./pages/LoanHistory";
import CreateAccount from "./pages/CreateAccount";

function App() {
  return (
    <div className="container">
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route element={<ProtectedRoute role="USER" />}>
          <Route path="/create-account" element={<CreateAccount />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/apply-loan" element={<ApplyLoan />} />
          <Route path="/history" element={<LoanHistory />} />
        </Route>

        {/* ADMIN */}
        <Route element={<ProtectedRoute role="ADMIN" />}>
          <Route path="/admin" element={<AdminPanel />} />
        </Route>
      </Routes>
    </BrowserRouter>
    </div>
  );
}

export default App;