import { useEffect, useState } from "react";
import {getAccount,getLoan,getSchedule,exportCSV,forecloseLoan,prepayLoan,payEmi } from "../api/api";

const Dashboard = () => {
  const user = JSON.parse(localStorage.getItem("user"));
  const [account, setAccount] = useState(null);
  const [loan, setLoan] = useState(null);
  const [schedule, setSchedule] = useState([]);
  const [prepayAmount, setPrepayAmount] = useState("");

  const [loading, setLoading] = useState(false);

  const [showModal, setShowModal] = useState(false);
  const [selectedEmiId, setSelectedEmiId] = useState(null);
  const [paymentMethod, setPaymentMethod] = useState("");
  const [upiId, setUpiId] = useState("");
  const [paymentStatus, setPaymentStatus] = useState("");
  const [cardDetails, setCardDetails] = useState({
    number: "",
    name: "",
    expiry: "",
    cvv: ""
  });

 useEffect(() => {
  loadData();
}, []);


  const loadData = async () => {
    try {
      setLoading(true);
      const accRes = await getAccount(user.userId);
      setAccount(accRes.data);

      try {
        const loanRes = await getLoan(accRes.data.accountId);
        setLoan(loanRes.data);

        const schRes = await getSchedule(loanRes.data.id);
        setSchedule(schRes.data);

      } catch {
        setLoan(null);
        setSchedule([]);
      }

    } catch (err) {
      console.log("No account found");
    } finally {
      setLoading(false);
    }
  };


  const handlePaymentSubmit = async () => {
  if (!paymentMethod) {
    return alert("Select payment method");
  }

  if (paymentMethod === "CARD") {
    const { number, name, expiry, cvv } = cardDetails;

    if (!number || number.length !== 16) {
      return alert("Invalid Card Number");
    }
    if (!name) {
      return alert("Enter Card Holder Name");
    }
    if (!expiry.match(/^\d{2}\/\d{2}$/)) {
      return alert("Invalid Expiry (MM/YY)");
    }
    if (!cvv || cvv.length !== 3) {
      return alert("Invalid CVV");
    }
  }


  try {
    await payEmi(selectedEmiId);

    alert("Payment Successful");
    setShowModal(false);
    setPaymentMethod("");
    setCardDetails({ number: "", name: "", expiry: "", cvv: "" });

    loadData(); 

  } catch (err) {
    alert(err.response?.data || "Payment Failed");
  }
};

  const handlePay = async(id) => {
    try {
      await payEmi(id);
      alert("EMI Paid Successfully");
      loadData();
    } catch (err) {
      alert(err.response?.data || "Payment Failed");
    }
  };

  const handlePrepay = async () => {
    if (!loan) return;
    if (!prepayAmount) {
      return alert("Enter amount");
    }

    if (prepayAmount < loan.emi * 3) {
      return alert("Minimum prepayment is 3x EMI");
    }
    try {
      await prepayLoan({
        loanId: loan.id,
        amount: prepayAmount
      });

      alert("Prepayment Successful");
      setPrepayAmount("");
      loadData();

    } catch (err) {
      alert(err.response?.data || "Prepayment Failed");
    }
  };


  const handleForeclose = async () => {
    if (!loan) return;
    const paidCount = schedule.filter(s => s.status === "PAID").length;
    if (paidCount < 3) {
      return alert("Foreclosure allowed after 3 EMIs");
    }
    try {
      await forecloseLoan(loan.id);
      alert("Loan Closed Successfully");
      loadData();

    } catch (err) {
      alert(err.response?.data || "Foreclose Failed");
    }
  };

  const downloadCSV = async () => {
    if (!loan) return alert("No loan available");
    try {
      const res = await exportCSV(loan.id);

      const url = window.URL.createObjectURL(new Blob([res.data]));
      const a = document.createElement("a");
      a.href = url;
      a.download = "Loan_Schedule.csv";
      a.click();

    } catch (err) {
      alert("CSV Download Failed");
    }
  };

  const activeSchedule = schedule.filter(s => s.status !== "CANCELLED");
  const cancelledSchedule = schedule.filter(s => s.status === "CANCELLED");

  const paidCount = schedule.filter(s => s.status === "PAID").length;
  const pendingCount = schedule.filter(s => s.status === "PENDING").length;

  return (
    <div className="container mt-4">
      {loading && (
        <div className="alert alert-info text-center">
          Loading...
        </div>
      )}
      <div className="row text-center mb-3">

        <div className="col-md-3">
          <div className="card shadow p-3">
            <h6>Account Balance</h6>
            <h4>{account?.balance || 0}</h4>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card shadow p-3 text-100">
            <h6>Loan Amount</h6>
            <h4>{loan?.loanAmount || 0}</h4>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card shadow p-3">
            <h6>Paid EMIs</h6>
            <h4>{paidCount}</h4>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card shadow p-3">
            <h6>Pending EMIs</h6>
            <h4>{pendingCount}</h4>
          </div>
        </div>

      </div>
      {!loan && (
        <div className="alert alert-warning text-center">
        No Loan Found OR Waiting for Approval
        </div>
      )}
      {loan && (
        <>
          <div className="card mt-3 shadow p-3">
            <h5>Loan Info</h5>
            <table className="table table-bordered text-center">
              <thead>
                <tr>
                  <th>Status</th>
                  <th>Interest</th>
                  <th>Tenure</th>
                  <th>EMI</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <span className={
                      loan.status === "ONGOING"
                        ? "badge bg-success"
                        : "badge bg-secondary"
                    }>
                      {loan.status}
                    </span>
                  </td>
                  <td>{loan.interestRate}%</td>
                  <td>{loan.tenure} yrs</td>
                  <td>{loan.emi}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div className="card mt-3 shadow p-3">
            <h5>Loan Actions</h5>
            <div className="d-flex gap-2 flex-wrap">
              <input type="number" placeholder="Prepay Amount"
                className="form-control w-25"
                value={prepayAmount}
                onChange={(e) => setPrepayAmount(e.target.value)}
              />
              <button className="btn btn-danger" onClick={handleForeclose}> Foreclose </button>
              <button className="btn btn-warning"onClick={handlePrepay}>Prepay</button>
              <button className="btn btn-success" onClick={downloadCSV}> Download CSV </button>
            </div>
          </div>

          <div className="card mt-3 shadow p-3">
            <h5>Active EMI Schedule</h5>
            <div style={{ maxHeight: "300px", overflowY: "scroll" }}>
              <table className="table table-striped text-center">
                <thead>
                  <tr>
                    <th>Month</th>
                    <th>EMI</th>
                    <th>Principal</th>
                    <th>Interest</th>
                    <th>Balance</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>

                <tbody>
                  {activeSchedule.map((s) => (
                    <tr key={s.id}>
                      <td>{s.month}</td>
                      <td>{s.emi}</td>
                      <td>{s.principal}</td>
                      <td>{s.interest}</td>
                      <td>{s.outstanding}</td>
                      <td>
                        <span className={
                          s.status === "PAID"
                            ? "badge bg-success"
                            : "badge bg-warning"
                        }>
                          {s.status}
                        </span>
                      </td>

                      <td>
                        {s.status === "PENDING" && (
                          <button className="btn btn-sm btn-primary"
                          onClick={() => { setSelectedEmiId(s.id);
                            setShowModal(true);
                              }}
                            >Pay</button>
                        )}
                      </td>

                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
          <div className="card mt-3 shadow p-3">
            <h5>Cancelled EMI History (After Prepayment)</h5>
            <div style={{ maxHeight: "200px", overflowY: "scroll" }}>
              <table className="table table-bordered text-center">
                <thead>
                  <tr>
                    <th>Month</th>
                    <th>EMI</th>
                    <th>Status</th>
                  </tr>
                </thead>

                <tbody>
                  {cancelledSchedule.map((s) => (
                    <tr key={s.id}>
                      <td>{s.month}</td>
                      <td>{s.emi}</td>
                      <td className="text-danger">{s.status}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>

        </>
      )}

      {showModal && (
          <div className="modal show d-block" tabIndex="-1">
            <div className="modal-dialog">
              <div className="modal-content">

                <div className="modal-header">
                  <h5 className="modal-title">Pay EMI</h5>
                  <button className="btn-close" onClick={() => setShowModal(false)}></button>
                </div>

                <div className="modal-body">
                  <div className="mb-3">
                    <label>Select Payment Method</label>
                    <select
                      className="form-control"
                      value={paymentMethod}
                      onChange={(e) => setPaymentMethod(e.target.value)}
                    >
                      <option value="">--Select--</option>
                      <option value="UPI">UPI</option>
                      <option value="CARD">Card</option>
                    </select>
                  </div>
                  {paymentMethod === "UPI" && (
                  <>
                    <input
                      type="text"
                      placeholder="Enter UPI ID (example@upi)"
                      className="form-control mb-2"
                      value={upiId}
                      onChange={(e) => setUpiId(e.target.value)}
                    />

                    {paymentStatus === "processing" && (
                      <div className="alert alert-warning">Processing payment...</div>
                    )}

                    {paymentStatus === "success" && (
                      <div className="alert alert-success">Payment Done</div>
                    )}
                  </>
                    )}    
                  {paymentMethod === "CARD" && (
                    <>
                      <input
                        type="text"
                        placeholder="Card Number"
                        className="form-control mb-2"
                        value={cardDetails.number}
                        onChange={(e) =>
                          setCardDetails({ ...cardDetails, number: e.target.value })
                        }
                      />

                      <input
                        type="text"
                        placeholder="Card Holder Name"
                        className="form-control mb-2"
                        value={cardDetails.name}
                        onChange={(e) =>
                          setCardDetails({ ...cardDetails, name: e.target.value })
                        }
                      />

                      <input type="text" placeholder="Expiry (MM/YY)"
                        className="form-control mb-2"
                        value={cardDetails.expiry}
                        onChange={(e) =>
                          setCardDetails({ ...cardDetails, expiry: e.target.value })
                        }
                      />

                      <input type="password"placeholder="CVV"
                        className="form-control mb-2"
                        value={cardDetails.cvv}
                        onChange={(e) =>
                          setCardDetails({ ...cardDetails, cvv: e.target.value })
                        }
                      />
                    </>
                  )}

                </div>

                <div className="modal-footer">
                  <button className="btn btn-secondary" onClick={() => setShowModal(false)}>
                    Cancel
                  </button>
                  <button className="btn btn-success" onClick={handlePaymentSubmit}>
                    Pay Now
                  </button>
                </div>

              </div>
            </div>
          </div>
        )}
    </div>
  );
};

export default Dashboard;