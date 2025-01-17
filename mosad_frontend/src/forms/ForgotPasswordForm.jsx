import { Typography, Button, TextField, Box, Alert } from "@mui/material";
import React, { useState, useEffect } from "react";

const ForgotPasswordForm = () =>{

    const [step, setStep] = useState(1); // Step management: 1 - Email, 2 - OTP, 3 - New Password, 4 - Success
    const [email, setEmail] = useState("");
    const [otp, setOtp] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [timer, setTimer] = useState(30); // Countdown timer for OTP
    const [isResendDisabled, setIsResendDisabled] = useState(true);
    const [passwordStrength, setPasswordStrength] = useState("");


    
    const handleEmailSubmit = () => {
        // Simulating a backend response
        const emailExists = true; // Replace with backend call result
      
        if (email.includes(' ')) {
          setErrorMessage('Email cannot contain spaces.');
          return false;
        }
        if (!email) {
          setErrorMessage("Email cannot be empty.");
          

        }
        else if (!email.includes("@")) {
          setErrorMessage("Enter a valid email.");
          
        }
        
        
        else if (!emailExists){
          setErrorMessage("Email does not exist.");
        }
        else if (emailExists) {
          setStep(2);
          setErrorMessage("");
          startTimer();
        }     

      };


      const handleOtpSubmit = () => {
        const otpIsValid = true; // Replace with backend call result
        
        if (!otp) {
          setErrorMessage("OTP cannot be empty.");
          return;
        }

        if (!/^\d+$/.test(otp)) {
          setErrorMessage("OTP must contain only numbers.");
          return;
        }

        if (!/^\d{6}$/.test(otp)) {
          setErrorMessage("OTP must have only 6 digit.");
          return;
        }
        if (otpIsValid) {
          setStep(3);
          setErrorMessage("");
        } else {
          setErrorMessage("Invalid OTP. Please try again.");
        }
      };

      const startTimer = () => {
        let countdown = 30;
        setTimer(countdown);
        const interval = setInterval(() => {
          countdown -= 1;
          setTimer(countdown);
          if (countdown <= 0) clearInterval(interval);
        }, 1000);
      };

      const handleResendOtp = () => {
        if (!isResendDisabled) {
          setOtp(""); 
          setTimer(30);
          setIsResendDisabled(true);
          startResendTimer();
        console.log("Resend OTP triggered.");//Implement here as new OTP sending process
        }
      };
    
      const startResendTimer = () => {
        const interval = setInterval(() => {
          setTimer((prev) => {
            if (prev === 1) {
              clearInterval(interval);
              setIsResendDisabled(false);
              return 0;
            }
            return prev - 1;
          });
        }, 1000);
      };






      const handlePasswordSubmit = () => {

        if (newPassword.length < 8) {
          setErrorMessage("Password must be at least 8 characters long.");
          return;
        }

        if (newPassword.includes(' ')) {
          setErrorMessage('Password cannot contain spaces.');
          return false;
        }
        
        if (!/\d/.test(newPassword)) {
          setErrorMessage("Password must contain at least one number.");
          return;
        }

        if (newPassword === confirmPassword) {
          setStep(4);
          setErrorMessage("");
        } else {
          setErrorMessage("Passwords do not match.");
        }
      };

      const handlePasswordChange = (password) => {
        setNewPassword(password);

        if (password === "") {
          setPasswordStrength(""); // Reset password strength if empty
          return;
        }
    
        // Check password strength
        const hasUpperCase = /[A-Z]/.test(password);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
        if (hasUpperCase && hasSpecialChar) {
          setPasswordStrength("strong");
        } else {
          setPasswordStrength("weak");
        }
      };



      const handleContinueToLogin = () => {
        window.location.reload();
      };




      useEffect(() => {
        let interval;
        if (timer > 0) {
          interval = setInterval(() => {
            setTimer((prev) => prev - 1);
          }, 1000);
        } else {
          setIsResendDisabled(false); // Enable resend button after timer ends
        }
    
        return () => clearInterval(interval); // Cleanup interval on component unmount
      }, [timer]);

     
      
    

    return(
  
      
        <Box>

            {/* Email Entering section */}
            {step === 1 && (     

                <Box>
                    <Typography variant="h5" gutterBottom>
                      Forgot Password
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                      Enter your email for the verification process, we will send the OTP code to your email.
                    </Typography>
                    <TextField
                      fullWidth
                      label="Email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      onKeyDown={(e) => {
                        if (e.key === "Enter") {
                          handleEmailSubmit();
                        }
                      }}
                      margin="normal"
                    />
                      {errorMessage && <Typography color="error">{errorMessage}</Typography>}
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={handleEmailSubmit}
                      fullWidth
                    >
                      Continue
                    </Button>
                </Box>
            )}


            {/* OTP Entering Section */}

            {step === 2 && (

                <Box>
                    <Typography variant="h5" gutterBottom>
                        Verification
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        Enter your OTP code that you received on your email.
                    </Typography>
                    
                    <TextField
                        fullWidth
                        label="Enter OTP"
                        value={otp}
                        onChange={(e) => setOtp(e.target.value)}
                        onKeyDown={(e) => {
                          if (e.key === "Enter") {
                            handleOtpSubmit();
                          }
                        }}
                        margin="normal"
                    />
                    {errorMessage && <Typography color="error">{errorMessage} 
                    </Typography>}
                    <Typography color="textSecondary">
                        00:{timer < 10 ? `0${timer}` : timer}
                        </Typography>
                    <Button
                        variant="contained"
                        color="primary"
                        onClick={handleOtpSubmit}
                        fullWidth
                    >
                        Continue
                    </Button>
                    <Typography variant="body2" mt={2}>
                        If you didn’t receive a code, 
                        <Button
                            variant="text"
                            fullWidth
                            onClick={handleResendOtp}
                            disabled={isResendDisabled}
                         >
                        Resend Code
                         </Button>
                    </Typography>
                </Box>
                )
            }


            {/* New Password Create */}


            {step === 3 && (
            <Box>
                <Typography variant="h5" gutterBottom>
                    New Password
                </Typography>
                <Typography variant="body1" gutterBottom>
                    Set the new password for your account so you can log in and access all features.
                </Typography>
                <TextField
                    fullWidth
                    label="Enter new password"
                    type="password"
                    value={newPassword}
                    onChange={(e) => handlePasswordChange(e.target.value)}
                    onKeyDown={(e) => {
                      if (e.key === "Enter") {
                        handlePasswordSubmit();
                      }
                    }}
                    margin="normal"
                />

                      {newPassword && (
                        <Typography
                          color={passwordStrength === "strong" ? "success.main" : "warning.main"}
                        >
                          Password is {passwordStrength}.
                        </Typography>
                      )}

                <TextField
                    fullWidth
                    label="Confirm password"
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    onKeyDown={(e) => {
                      if (e.key === "Enter") {
                        handlePasswordSubmit();
                      }
                    }}
                    margin="normal"
                />
                {errorMessage && <Typography color="error">{errorMessage}</Typography>}
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handlePasswordSubmit}
                    fullWidth
                >
                    Update Password
                </Button>
            </Box>
             )}



             {/* Success */}

             {step === 4 && (
                <Box textAlign="center">
                    <Typography variant="h5" gutterBottom>
                        Successfully
                    </Typography>
                    <Typography variant="body1" gutterBottom>
                        Your password has been reset successfully.
                    </Typography>
                    <Button variant="contained" fullWidth onClick={handleContinueToLogin}>
                        Continue to Login
                    </Button>
                </Box>
      )}





        </Box>
      
      
  
    );
}

export default ForgotPasswordForm;