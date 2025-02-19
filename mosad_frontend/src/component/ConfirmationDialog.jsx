import React from "react";
import PropTypes from "prop-types";
import "./css/ConfirmationDialog.css"; 
const ConfirmationDialog = ({
  title,
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
        <h3>{title || "Confirmation"}</h3>
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
  title:PropTypes.string,
  message: PropTypes.string.isRequired,
  onCancel: PropTypes.func.isRequired,
  onConfirm: PropTypes.func.isRequired,
  isOpen: PropTypes.bool.isRequired,
};

export default ConfirmationDialog;
