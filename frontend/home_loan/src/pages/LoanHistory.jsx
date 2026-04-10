import { useEffect, useState } from "react";
import { getLoan } from "../api/api";

const LoanHistory = () => {
  const user = JSON.parse(localStorage.getItem("user"));

  const [loan, setLoan] = useState(null);

  const auth = {
    auth: {
      username: user.username,
      password: user.password
    }
  };

  useEffect(() => {
    loadLoan();
  }, []);

  const loadLoan = async () => {
    const res = await getLoan(1, auth);
    setLoan(res.data);
  };

  return (
    <div className="container mt-4">
      <h3>Loan History</h3>

      <table className="table table-bordered shadow">
        <thead>
          <tr>
            <th>ID</th>
            <th>Amount</th>
            <th>EMI</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {loan && (
            <tr>
              <td>{loan.id}</td>
              <td>{loan.loanAmount}</td>
              <td>{loan.emi}</td>
              <td>{loan.status}</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default LoanHistory;