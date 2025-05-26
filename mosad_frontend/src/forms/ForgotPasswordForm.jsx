import { Typography, Button, TextField, Box,IconButton, InputAdornment } from "@mui/material";
import React, { useState, useEffect } from "react";
import { useFgtPwdMailCheckAndOtpSend,useVerifyOtp,useResendOtp,useChangePassword } from "../hooks/servicesHook/useApiUserService";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';

const ForgotPasswordForm = () =>{
    const fgtPwdMailCheckAndOtpSend=useFgtPwdMailCheckAndOtpSend();
    const verifyOtp = useVerifyOtp();
    const resendOtp = useResendOtp();
    const changePwd=useChangePassword();
    const [step, setStep] = useState(1); // Step management: 1 - Email, 2 - OTP, 3 - New Password, 4 - Success
    const [email, setEmail] = useState("");
    const [otp, setOtp] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const [timer, setTimer] = useState(30); // Countdown timer for OTP
    const [isResendDisabled, setIsResendDisabled] = useState(true);
    const [passwordStrength, setPasswordStrength] = useState("");
    const [isLoading,setIsLoading] =useState(false);

    //Password hide/show
    const [type, setType] = useState('password');
    const [icon, setIcon] = useState(<VisibilityOffIcon />);

    const handlePasswordToggle = () => {
      if (type === 'password') {
          setIcon(<VisibilityIcon />);
          setType('text')
      } else {
          setIcon(<VisibilityOffIcon />)
          setType('password')
      }
    }

    //Email submititon to backend
    const handleEmailSubmit = async () => {
        //validate email
        if (email.includes(' ')) {
          setErrorMessage('Email cannot contain spaces.');
          return false;
        }
        else if (!email) {
          setErrorMessage("Email cannot be empty.");
          return false;
        }
        else if (!email.includes("@")) {
          setErrorMessage("Enter a valid email.");
          return false;
        }               
        try {
          setIsLoading(true); // Set loading state to true
          const response = await fgtPwdMailCheckAndOtpSend(email);
          if (response.data.success) {
            setStep(2);
            setErrorMessage("");
            startTimer();
          } 
        } catch (error) {
          setErrorMessage("Failed to send email. Please try again later.");
        } finally {
          setIsLoading(false); // Set loading state to false after the request completes
        }
    };

    //Otp submisson to backend
    const handleOtpSubmit = async () => {
      if (!otp) {
        setErrorMessage("OTP cannot be empty.");
        return false;
      }
      if (!/^\d+$/.test(otp)) {
        setErrorMessage("OTP must contain only numbers.");
        return false;
      }
      if (!/^\d{6}$/.test(otp)) {
        setErrorMessage("OTP must have only 6 digit.");
        return false;
      }
      try {
        setIsLoading(true); // Set loading state to true
        const response = await verifyOtp(otp,email);
        if (response.data.success) {
          setStep(3);
          setErrorMessage("");
        }
      }catch (error) {
        setErrorMessage("Invalid OTP. Please try again.");
      } finally {
        setIsLoading(false); // Set loading state to false after the request completes
      }
    };

    const startTimer = () => {
      let countdown = 60;
      setTimer(countdown);
      const interval = setInterval(() => {
        countdown -= 1;
        setTimer(countdown);
        if (countdown <= 0) clearInterval(interval);
      }, 1000);
    };

    //Otp resending....
    const handleResendOtp = () => {
      if (!isResendDisabled) {
        setOtp("");
        setTimer(30);
        setIsResendDisabled(true);
        startResendTimer();
        resendOtp(email).then((response)=>{
          setConfirmPassword(response.data.message);
        })
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

    //New password changing
    const handlePasswordSubmit =async () => {
      if (newPassword.length < 6) {
        setErrorMessage("Password must be at least 8 characters long.");
        return;
      }
      if (newPassword.includes(' ')) {
        setErrorMessage('Password cannot contain spaces.');
        return;
      }
      if (!/\d/.test(newPassword)) {
        setErrorMessage("Password must contain at least one number.");
        return;
      }
      if (newPassword !== confirmPassword) {
        setErrorMessage("Passwords do not match.");
      } 
      try {
        setIsLoading(true); // Set loading state to true
        const response = await changePwd(email,{password:newPassword});
        if (response.data.success) {
          setStep(4);
          setErrorMessage("");
        }
      }catch (error) {
        setErrorMessage("Internal Error. Please try again.");
      } finally {
        setIsLoading(false); // Set loading state to false after the request completes
      }
    };
    const handlePasswordChange = (password) => {
      setNewPassword(password);
      if (password === "") {
        setPasswordStrength("");
        return;
      }
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
      <Box sx={{ p: 3, maxWidth: '400px', mx: 'auto' }}>
        {/* Email Entering section */}{step === 1 && (     
          <Box>
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
                sx={{ mt: 2 }}
                variant="contained"
                color="primary"
                onClick={handleEmailSubmit}
                fullWidth
                loading={isLoading}
                loadingPosition="end"
              >
                Continue
              </Button>
          </Box>)}
        {/* OTP Entering Section */}
        <Box textAlign="center" mb={3}>
          <Typography variant="h4" gutterBottom>
            {step === 2 && "Verification"}
            {step === 3 && "New Password"}
            {step === 4 && "Successfully"}
          </Typography>
        </Box>
        {step === 2 && (
          <Box>
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
                  loading={isLoading}
                  loadingPosition="end"
                  sx={{ mt: 2 }}
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
                      sx={{ mt: 2 }}
                    >
                  Resend Code
                  </Button>
              </Typography>
          </Box>)}
        {/* New Password Create */}{step === 3 && (
          <Box>
              <Typography variant="body1" gutterBottom>
                  Set the new password for your account so you can log in and access all features.
              </Typography>
              <TextField
                  fullWidth
                  label="Enter new password"
                  type={type}
                  value={newPassword}
                  onChange={(e) => handlePasswordChange(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      handlePasswordSubmit();
                    }
                  }}
                  margin="normal"
                  slotProps={{
                    input: {
                        endAdornment:
                            <InputAdornment position="end">
                                <IconButton
                                    aria-label="Toggle the password"
                                    onClick={handlePasswordToggle}
                                >
                                    {icon}
                                </IconButton>
                            </InputAdornment>
                    },
                }}
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
                  type={type}
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      handlePasswordSubmit();
                    }
                  }}
                  margin="normal"
                  sx={{ mb: 2 }}
                  slotProps={{
                      input: {
                          endAdornment:
                              <InputAdornment position="end">
                                  <IconButton
                                      aria-label="Toggle the password"
                                      onClick={handlePasswordToggle}
                                  >
                                      {icon}
                                  </IconButton>
                              </InputAdornment>
                      },
                  }}
              />
              {errorMessage && <Typography color="error">{errorMessage}</Typography>}
              <Button
                  variant="contained"
                  color="primary"
                  onClick={handlePasswordSubmit}
                  fullWidth
                  loading={isLoading}
                  loadingPosition="end"
                  sx={{ mt: 2 }}
              >
                  Update Password
              </Button>
          </Box>)}
        {/* Success */}{step === 4 && (
          <Box textAlign="center">
              <Typography variant="h5" gutterBottom>
                  Successfully
              </Typography>
              <Typography variant="body1" gutterBottom>
                  Your password has been reset successfully.
              </Typography>
              <Button variant="contained" fullWidth onClick={handleContinueToLogin} sx={{ mt: 2 }}>
                  Continue to Login
              </Button>
          </Box>)}
      </Box>
   );

}

export default ForgotPasswordForm;