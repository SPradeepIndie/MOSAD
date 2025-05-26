const initialErrors = {
    firstNameError: '',
    lastNameError: '',
    usernameError: '',
    emailError: '',
    roleNameError: '',
    pwd_1Error: '',
    pwd_2Error: '',
    branchNameError: '',
};

// Email validation
const isValidEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
};

// Password strength validation
const isValidPassword = (password) => {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@#$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
    return passwordRegex.test(password);
};

const validateGeneralForm = (userRegData) => {
    let isValid = true;
    let newErrors = { ...initialErrors };

    // Check empty fields
    if (userRegData.userDto.firstName === "") {
        isValid = false;
        newErrors.firstNameError = "First Name cannot be empty";
    } else if (typeof userRegData.userDto.firstName !== 'string') {
        isValid = false;
        newErrors.firstNameError = "First Name must be a string";
    }
    if (userRegData.userDto.username === "") {
        isValid = false;
        newErrors.usernameError = "Username cannot be empty";
    }
    if (userRegData.userRoleDto.roleName === "") {
        isValid = false;
        newErrors.roleNameError = "Please select a role";
    }
    if (userRegData.branchName === "") {
        isValid = false;
        newErrors.branchNameError = "Please select branch";
    }

    if (userRegData.userDto.email === "") {
        isValid = false;
        newErrors.emailError = "email cannot be empty";
    }
    else if(!isValidEmail(userRegData.userDto.email)) {
            isValid = false;
            newErrors.emailError = "Invalid email address";
    }

    if (userRegData.userDto.lastName === "") {
        isValid = false;
        newErrors.lastNameError = "last name cannot be empty";
    }else if (typeof userRegData.userDto.lastName !== 'string') {
        isValid = false;
        newErrors.lastNameError = "Last Name must be a string";
    }
    
    return { isValid, newErrors };
};

const validatePasswords = (pwds) => {
    let isValid = true;
    let newErrors = {
        pwd_1Error: '',
        pwd_2Error: '',
    };
    if (pwds.pwd_1 === "") {
        isValid = false;
        newErrors.pwd_1Error = "Password cannot be empty";
    } else if (!isValidPassword(pwds.pwd_1)) {
        isValid = false;
        newErrors.pwd_1Error = "Password must be at least 6 characters long, include uppercase and lowercase letters, a number, and a special character.";
    }

    if (pwds.pwd_2 === "") {
        isValid = false;
        newErrors.pwd_2Error = "Confirm Password cannot be empty";
    }
    
    if (pwds.pwd_1 !== pwds.pwd_2) {
        isValid = false;
        alert("Passwords do not match");
    }

    return { isValid, newErrors };
};

const validateFullForm = (userRegData, pwds) => {
    const { isValid: isValidGeneral, newErrors: generalErrors } = validateGeneralForm(userRegData);
    const { isValid: isValidPasswords, newErrors: passwordErrors } = validatePasswords(pwds);

    let isValid = isValidGeneral && isValidPasswords;
    let newErrors = { ...generalErrors, ...passwordErrors };

    return { isValid, newErrors };
};

export { 
    validateGeneralForm, 
    validateFullForm, 
    initialErrors 
};
