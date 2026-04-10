import { useEffect, useState } from "react";
import {getAllApplications,approveLoan,cancelLoan} from "../api/api";

const AdminPanel = () => {
  const [apps, setApps] = useState([]);

  useEffect(() => {
    loadApps();
  }, []);

const loadApps = async () => {
  try {
    const res = await getAllApplications();
    setApps(res.data);
  } catch (err) {
    console.error(err);
  }
};

  const handleApprove = async (id) => {
    await approveLoan(id);
    alert("Approved");
    loadApps();
  };

  const handleReject = async (id) => {
    await cancelLoan(id);
    alert("Rejected");
    loadApps();
  };

  const downloadDoc = async (path) => {
  try {
    const user = JSON.parse(localStorage.getItem("user"));
    const encodedPath = encodeURIComponent(path);

    const res = await fetch(
      `http://localhost:8080/loan/document?path=${encodedPath}`,
      {
        headers: {
          Authorization:
            "Basic " + btoa(user.username + ":" + user.password)
        }
      }
    );

    const blob = await res.blob();

    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");

    a.href = url;
    a.download = path.split("\\").pop();
    a.click();

  } catch (err) {
    console.error(err);
    alert("Download failed");
  }
};

  return (
    <div className="container mt-4">
      <h3 className="text-center">Admin Panel - Loan Applications</h3>

      <div className="card shadow p-3 mt-3">
        <table className="table table-bordered table-hover">
          <thead className="table-dark">
            <tr>
              <th>ID</th>
              <th>User</th>
              <th>Amount</th>
              <th>Eligible</th>
              <th>Tenure</th>
              <th>Status</th>
              <th>Property</th>
              <th>Document</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {apps.map((a) => (
              <tr key={a.id}>
                <td>{a.id}</td>
                <td>{a.account?.name}</td>
                <td>{a.requestedAmount}</td>
                <td>{a.eligibleAmount}</td>
                <td>{a.tenure}</td>

                <td>
                  <span
                    className={
                      a.status === "APPROVED"
                        ? "badge bg-success"
                        : a.status === "REJECTED"
                        ? "badge bg-danger"
                        : "badge bg-warning"
                    }
                  >
                    {a.status}
                  </span>
                </td>

                <td>{a.propertyDetails}</td>
                <td>
                  <button className="btn btn-primary btn-sm" onClick={() => downloadDoc(a.documentPath)}>Download</button>
                </td>

                <td>
                  <button className="btn btn-success btn-sm me-2" onClick={() => handleApprove(a.id)}>Approve </button>
                  <button className="btn btn-danger btn-sm" onClick={() => handleReject(a.id)}>Reject </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AdminPanel;