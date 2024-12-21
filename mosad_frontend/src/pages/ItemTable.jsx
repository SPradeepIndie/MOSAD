import React, { useState, useEffect } from "react";
import axios from "axios";
import Modal from "react-modal";
import { useLocation } from "react-router-dom";
import "./Itemtable.css";
import HeaderBar from "../component/Header";
import ConfirmationDialog from "../component/ConfirmationDialog";
import GeneralMessage from "../component/GeneralMessage";
import ItemDetailsSection from "../component/ItemDetailsSection";


// React Modal setup
Modal.setAppElement("#root");

const ItemTable = () => {
  const location = useLocation();
  const { title } = location.state || {};
  //console.log(title);

  const getInitialFormData = (title) => {
    switch (title) {
      case "CEAT":
       
        return {
          itemID: "",
          size: "",
          pattern: "",
          pr: 0,
          availableQty: 0,
          osp: 0,
          cp:0
        };
      case "tyre_presa":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          ply: 0,
          tyreCount: 0,
          officialSellingPrice: 0,
          vehicleType: "",
        };
      case "tyre_rapid":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          tyreCount: 0,
          officialSellingPrice: 0,
          vehicleType: "",
        };
      case "tyre_linglong":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          tyreCount: null,
          officialSellingPrice: null,
          vehicleType: "",
        };
      case "tyre_atlander":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          tyreCount: 0,
          officialSellingPrice: 0,
          vehicleType: "",
        };
        
      default:
        return {
          itemID: "",
          field1: "",
          field2: "",
        };
    }
  };

  const [items, setItems] = useState([]);
  const [currentItem, setCurrentItem] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isDialogOpen, setIsDialogOpen] = useState(false); // Dialog visibility state
  const [itemIdToDelete, setItemIdToDelete] = useState(null); // Store the item ID to delete
  const [formData, setFormData] = useState(getInitialFormData(title));
  const [message, setMessage] = useState(null); // Message for GeneralMessage component
  const [inputFieldErrors, setinputFieldErrors] = useState({});


  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = () => {
    axios
      .get(`http://localhost:8080/api/v1/stock/${title}`)
      .then((response) => {
        //console.log("Fetched items:", response.data);
        setItems(response.data);
      })
      .catch((error) => console.error("Error fetching data:", error));
  };

  const deleteItem = () => {
    if (itemIdToDelete) {
      axios
        //.delete(`http://localhost:8080/api/v1/stock/${title}/${itemIdToDelete}`)
        .delete(`http://localhost:8080/api/v1/stock/${title}`,itemIdToDelete)
        .then(() => {
          fetchItems();
          setIsDialogOpen(false);
          setItemIdToDelete(null);
          setMessage({ type: "error", text: "Item deleted successfully!" });
          setTimeout(() => setMessage(null), 3000); // Clear message after 3 seconds
        })
        .catch((error) => console.error("Error deleting item:", error));
    }
  };

  const openModal = (item) => {
    if (item) {
      setCurrentItem(item);
      setFormData(item);
    } else {
      setCurrentItem(null);
      setFormData(getInitialFormData(title));
    }
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  // const validateForm = () => {
  //   const newErrors = {};
  
  //   // Iterate over form fields to validate
  //   Object.keys(formData).forEach((key) => {
  //     if (!formData[key] && key !== "itemID") {
  //       newErrors[key] = `${key.replace(/([A-Z])/g, " $1").trim()} is required.`;
  //     }
  //     // Example for validating numeric fields
  //     if (key === "tyreCount" && isNaN(formData[key])) {
  //       newErrors[key] = "tyreCount must be a valid number.";
  //     }
  //   });
  
  //   setErrors(newErrors);
  //   return Object.keys(newErrors).length === 0; // Returns true if no errors
  // };


  const handleChange = (key, value) => {
    // Update the form data
    setFormData((prevData) => ({ ...prevData, [key]: value }));
  
    // Validate the field
    let fieldError = "";
  
    if (!value) {
      fieldError = `${key.replace(/([A-Z])/g, " $1").trim()} is required.`;
    } else if (key === "tyreCount" && !Number.isInteger(Number(value))) {
      fieldError = "Tyre Count must be a valid integer.";
    } else if (key === "officialSellingPrice" && !/^(-?\d+(\.\d+)?)$/.test(value)) {
      fieldError = "Official Selling Price must be a valid number.";
    }
  
    // Update or clear the specific field's error
    setinputFieldErrors((prevErrors) => {
      const updatedErrors = { ...prevErrors };
      if (fieldError) {
        updatedErrors[key] = fieldError; // Add error if validation fails
      } else {
        delete updatedErrors[key]; // Remove error if validation passes
      }
      return updatedErrors;
    });
  };
  
  

  const handleSubmit = (e) => {
    e.preventDefault();
    if (Object.keys(inputFieldErrors).length > 0) {
      console.log("Errors found in the form:", inputFieldErrors);
      setMessage({ type: "error", text: "Please re-check all red fields!" });
      setTimeout(() => setMessage(null), 2000); // Clear message after 3 seconds
      return; // Stop submission if validation fails
    }
    console.log("Form data:", formData);
    const request = currentItem
      ? axios.put(`http://localhost:8080/api/v1/stock/${title}/${formData.itemID}`, formData)
      : axios.post(`http://localhost:8080/api/v1/stock/${title}`, formData);

    request
      .then(() => {
        fetchItems();
        closeModal();
        setMessage(currentItem ? { type: "success", text:"Item updated successfully!"} : { type: "success", text:"Item added successfully!"});
        setTimeout(() => setMessage(null), 3000); // Clear message after 3 seconds
      })
      .catch((error) => {
        console.error("Error saving item:", error.response?.data || error.message);
        setMessage(currentItem ? { type: "error", text:"Failed to update item !"} : { type: "error", text:"Failed to add item!"});
        setTimeout(() => setMessage(null), 3000); // Clear message after 3 seconds
      });
  };

  return (
    <>
      {/* <HeaderBar /> */}
      <div>
        <h1>{title} Items</h1>
        {message && <GeneralMessage message={message} />}
        <button
          onClick={() => openModal(null)}
          style={{
            backgroundColor: "#03DAC6",
            color: "#121212",
            padding: "10px 20px",
            border: "none",
            borderRadius: "5px",
            marginBottom: "20px",
          }}
        >
          Add New Item
        </button>
        <table>
          <thead>
            <tr>
              {Object.keys(formData).map((key) => (
                <th key={key} hidden={key === "itemID"}>
                  {/* {key.replace(/([A-Z])/g, " $1").trim()} */}
                  {key}
                </th>
              ))}
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {items.map((item) => (
              <tr key={item.itemID}>
                {Object.keys(formData).map((key) => (
                  <td key={key} hidden={key === "itemID"}>
                    {item[key]}
                  </td>
                ))}
                <td>
                  <button
                    onClick={() => openModal(item)}
                    style={{
                      marginRight: "10px",
                      backgroundColor: "#BB86FC",
                      color: "#FFFFFF",
                      border: "none",
                      padding: "5px 10px",
                      borderRadius: "3px",
                    }}
                  >
                    Update
                  </button>
                  <button
                    onClick={() => {
                      setItemIdToDelete(item);
                      setIsDialogOpen(true);
                      console.log("Item ID to delete:", item);
                    }}
                    style={{
                      backgroundColor: "#CF6679",
                      color: "#FFFFFF",
                      border: "none",
                      padding: "5px 10px",
                      borderRadius: "3px",
                    }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <Modal
          isOpen={isModalOpen}
          onRequestClose={closeModal}
          style={{
            content: {
              backgroundColor: "#1E1E1E",
              color: "#FFFFFF",
              borderRadius: "18px",
              padding: "40px",
              maxWidth: "1000px",
              maxHeight: "50vh",
              margin: "auto",
            },
            overlay: {
              backgroundColor: "rgba(0, 0, 0, 0.8)",
            },
          }}
        >
          <h2>{currentItem ? "Edit Item" : "Add New Item"}</h2>
          <form onSubmit={handleSubmit}>
            {/* <section
              id="itemdetails"
              style={{
                marginBottom: "20px",
                padding: "15px",
                border: "1px solid #ccc",
                borderRadius: "8px",
              }}
            >
              <h3 style={{ color: "green", marginBottom: "10px" }}>Item Details</h3>
              <div
                style={{
                  display: "grid",
                  gridTemplateColumns: "1fr 2fr",
                  gap: "15px",
                }}
              >
                {Object.keys(formData).map((key) => (
                  <React.Fragment key={key}>
                    <label
                      disabled={key === "itemID"}
                      style={{
                        alignSelf: "center",
                        color: "#333",
                        fontWeight: "bold",
                      }}
                    >
                      {key.replace(/([A-Z])/g, " $1").trim()}:
                    </label>
                    <input
                      type="text"
                      value={formData[key]}
                      onChange={(e) =>
                        setFormData({ ...formData, [key]: e.target.value })
                      }
                      required={key !== "itemID"}
                      disabled={key === "itemID"}
                      style={{
                        backgroundColor: "#F5F5F5",
                        color: "#333",
                        border: "1px solid #ccc",
                        borderRadius: "4px",
                        padding: "8px",
                        width: "100%",
                        maxWidth: "300px",
                      }}
                    />
                  </React.Fragment>
                ))}
              </div>
            </section> */}

<ItemDetailsSection
  formData={formData}
  setFormData={setFormData}
  errors={inputFieldErrors}
  handleChange={handleChange}
/>


             {true && (
              <section id="pricedetails" style={{ padding: "15px", border: "1px solid #ccc", borderRadius: "8px" }}>
                <h3 style={{ color: "green", marginBottom: "10px" }}>Price Details</h3>
                <div style={{ display: "grid", gridTemplateColumns: "1fr 2fr", gap: "15px" }}>
                  <div>
                    <label>Company Price (CP):</label>
                    <input
                      type="text"
                      style={{
                        backgroundColor: "#F5F5F5",
                        color: "#333",
                        border: "1px solid #ccc",
                        borderRadius: "4px",
                        padding: "8px",
                        width: "100%",
                        maxWidth: "300px",
                      }}
                    />
                  </div>
                  <div>
                    <label>Official Selling Price (OSP):</label>
                    <input
                      type="text"
                      style={{
                        backgroundColor: "#F5F5F5",
                        color: "#333",
                        border: "1px solid #ccc",
                        borderRadius: "4px",
                        padding: "8px",
                        width: "100%",
                      }}
                      value={formData.officialSellingPrice}
                    />
                  </div>
                </div>
                <h4 style={{ marginTop: "15px" }}>Price Calculation</h4>
                <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr 1fr", gap: "15px" }}>
                  <div>
                    <label>Discount:</label>
                    <input
                      type="text"
                      style={{
                        backgroundColor: "#F5F5F5",
                        color: "#333",
                        border: "1px solid #ccc",
                        borderRadius: "4px",
                        padding: "8px",
                        width: "100%",
                      }}
                    />
                    <small style={{ display: "block", color: "#666", marginTop: "5px" }}>
                      Enter discount percentage for calculation
                    </small>
                  </div>
                  <div>
                    <label>Discount Price (DP = CP * Discount):</label>
                    <input
                      type="text"
                      disabled
                      style={{
                        backgroundColor: "#EFEFEF",
                        color: "#999",
                        border: "1px solid #ccc",
                        borderRadius: "4px",
                        padding: "8px",
                        width: "100%",
                      }}
                    />
                  </div>
                  <div>
                    <label>Selling Price (SP = OSP - DP):</label>
                    <input
                      type="text"
                      disabled
                      style={{
                        backgroundColor: "#EFEFEF",
                        color: "#999",
                        border: "1px solid #ccc",
                        borderRadius: "4px",
                        padding: "8px",
                        width: "100%",
                      }}
                    />
                  </div>
                  <div>
                    <label>Profit (Profit = SP - CP):</label>
                    <input
                      type="text"
                      disabled
                      style={{
                        backgroundColor: "#EFEFEF",
                        color: "#999",
                        border: "1px solid #ccc",
                        borderRadius: "4px",
                        padding: "8px",
                        width: "100%",
                      }}
                    />
                  </div>
                </div>
              </section>
            )}    

            <button
              type="submit"
              style={{
                backgroundColor: "#03DAC6",
                color: "#121212",
                padding: "10px 20px",
                border: "none",
                marginRight: "10px",
              }}
            >
              Submit
            </button>
            <button
              type="button"
              onClick={closeModal}
              style={{
                backgroundColor: "#CF6679",
                color: "#FFFFFF",
                padding: "10px 20px",
                border: "none",
              }}
            >
              Cancel
            </button>
          </form>
        </Modal>

        {/* Confirmation Dialog */}
        <ConfirmationDialog
          message="Are you sure you want to delete this item?"
          isOpen={isDialogOpen}
          onCancel={() => setIsDialogOpen(false)}
          onConfirm={deleteItem}
        />
      </div>
    </>
  );
};

export default ItemTable;
