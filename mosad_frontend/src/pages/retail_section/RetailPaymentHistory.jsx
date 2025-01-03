import React from 'react';
import './RetailPayment.css';

function RetailCustomer() {
  return (
    <div className="container">
      
      <div className="navbar">
        <input
          type="text"
          placeholder="Search by name/cnumber"
          className="search-bar"
        />
        <button className="btn search-btn">Search</button>
        
      </div>

      
      <div className="table-container">
        <table className="data-table">
          <thead>
            <tr>
            
              <th>Date</th>
              <th>Amount</th>
              <th>Payment Method</th>
              <th>Status</th>
              <th>Due Date</th>
            </tr>
          </thead>
          <tbody>
         
            {Array(10).fill(0).map((_, index) => (
              <tr key={index}>
                <td>Cell Data</td>
                <td>Cell Data</td>
                <td>Cell Data</td>
                <td>Cell Data</td>
                <td>Cell Data</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default RetailCustomer;


