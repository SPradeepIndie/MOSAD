import React from "react";
import PropTypes from "prop-types";
import "./ConfirmationDialog.css"; 

const ConfirmationDialog = ({
  message,
  onCancel,
  onConfirm,
  isOpen,
}) => {
  if (!isOpen) return null;

  return (
    <div className="confirmation-dialog-overlay">
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
          <button className="confirm-button" onClick={onConfirm}>
            Confirm
          </button>
        </div>
      </div>
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
