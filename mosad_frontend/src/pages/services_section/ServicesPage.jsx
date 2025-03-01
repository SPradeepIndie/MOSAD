import React, { useState } from "react";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs"; // Change to AdapterDayjs

const ServicesPage = () => {
    const [date, setDate] = useState(null);  // Initialize date as null
    const [amount, setAmount] = useState("");
    const [error, setError] = useState("");

    const handleAmountChange = (e) => {
        const value = e.target.value;
        if (value === "" || (/^\d*\.?\d*$/.test(value) && parseFloat(value) >= 0)) {
            setAmount(value);
            setError("");
        } else {
            setError("Please enter a valid amount");
        }
    };

    const handleSave = () => {
        if (!date || !amount) {
            setError("All fields are required");
            return;
        }
        console.log("Saved Data:", { date, amount });
        alert("Data Saved!");
        setError("");
    };

    const handleReset = () => {
        setDate(null);  // Reset date to null
        setAmount("");
        setError("");
    };

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>  {/* Use AdapterDayjs */}
            <div>
                <h1>Services Page</h1>
                
                <table>
                    
                    <tbody>
                        
                    <tr>
                        <td style={{ paddingRight: '20px' }}>
                            <label style={{ fontSize: "18px" }}>Date: </label>
                        </td>
                        <td style={{ paddingRight: '40px' }}> 
                            <DatePicker
                                label="Select Date"
                                value={date}
                                onChange={setDate}
                                slotProps={{ textField: { variant: "outlined", fullWidth: true } }}  
                            />
                        </td>

                        <td style={{ paddingRight: '20px' }}>
                            <label style={{ fontSize: "18px" }}>Amount: </label>
                        </td>

                        <td>
                            <input
                                type="text"
                                value={amount}
                                onChange={handleAmountChange}
                                style={{
                                    padding: '16px',
                                    width: '100%',  // Ensures the width is full for both inputs
                                    backgroundColor: 'white',
                                    border: '1px solid #ccc',
                                    borderRadius: '4px',
                                    color: 'black',
                                    fontSize: '26px',
                                    boxSizing: 'border-box',  // Prevents padding from affecting the width
                                }}
                            />
                        </td>

                        <td>
                        
                        <button onClick={handleReset} style={{ marginLeft: '10px' }}>Reset</button>
                        </td>
                        <td>
                        <button onClick={handleSave} style={{ marginLeft: '20px' }}>Save</button>
                    
                

                        </td>
                    
</tr>
                    </tbody>
                </table>

                {error && <p style={{ color: 'red' }}>{error}</p>}

                
            </div>
        </LocalizationProvider>
    );
};

export default ServicesPage;
