import React, { useState } from "react";
import PropTypes from "prop-types";
import "./css/ConfirmationDialog.css"; 
const ConfirmationDialog = ({
  message,
  onCancel,
  onConfirm,
  isOpen,
}) => {
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleConfirm = () => {
    onConfirm();
    setShowSuccessMessage(true);
    setTimeout(() => setShowSuccessMessage(false), 3000); // Show success message for 3 seconds
  };

  if (!isOpen && !showSuccessMessage) return null;

  return (
    <div className="confirmation-dialog-overlay">
      {showSuccessMessage ? (
        <div className="confirmation-success">
          <p>Item deleted successfully!</p>
        </div>
      ) : (
        <div className="confirmation-dialog">
          <button className="close-button" onClick={onCancel}>
            &times;
          </button>
          <h3>Confirmation</h3>
          <p>{message}</p>
          <div className="confirmation-dialog-actions">
            <button className="cancel-button" onClick={onCancel}>
              Cancel
            </button>
            <button className="confirm-button" onClick={handleConfirm}>
              Confirm
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

ConfirmationDialog.propTypes = {
  message: PropTypes.string.isRequired,
  onCancel: PropTypes.func.isRequired,
  onConfirm: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
};

export default ConfirmationDialog;