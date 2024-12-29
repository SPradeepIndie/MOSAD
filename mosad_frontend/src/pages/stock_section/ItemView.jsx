import React, { useState, useEffect } from "react";
import "./css/ItemView.css";
import { useLocation } from "react-router-dom";
import axios from "axios";
import Modal from "react-modal";
import GeneralMessage from "../component/GeneralMessage";
import ItemDetailsSection from "../component/ItemDetailsSection";
import PriceDetailsSection from "../component/PriceDetailsSection";
import setItemAddFromFields from "../utils/setItemAddFromFields";

// React Modal setup
Modal.setAppElement("#root");

const ItemView = ({ selectedCategory="tyre", selectedBrand="presa" }) => {
  const [rows, setRows] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [bannerImage, setBannerImage] = useState("");


    // Add a new item
    const location = useLocation();
    const { title } = location.state || {};
  
  
    const [currentItem, setCurrentItem] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [formData, setFormData] = useState(setItemAddFromFields(title));
    const [message, setMessage] = useState(null); // Message for GeneralMessage component
    const [inputFieldErrors, setinputFieldErrors] = useState({});
  
    const openModal = (item) => {
      if (item) {
        setCurrentItem(item);
        setFormData(item);
      } else {
        setCurrentItem(null);
        setFormData(setItemAddFromFields(title));
      }
      setIsModalOpen(true);
    };
  
    const closeModal = () => {
      setIsModalOpen(false);
    };
  
    const handleChange = (key, value) => {
      console.log("Error Handling called");
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
  







  // Fetch table data from backend
  useEffect(() => {
    if (selectedCategory && selectedBrand) {
      fetch(
        `http://localhost:8080/api/v1/item-view?category=${selectedCategory}&brand=${selectedBrand}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
        .then((response) => response.json())
        .then((data) => setRows(data))
        
        .catch((error) => console.error("Error fetching data:", error));
    }
    
  }, [selectedCategory, selectedBrand]);

  // Update banner image based on brand
  useEffect(() => {
    const brandImages = {
      atlander: "/assets/atlander.png",
      presa: "/assets/presa.png",
      dsi: "/assets/dsi.png",
      linglong: "/assets/linglong.png",
      rapid: "/assets/rapid.png"
    };
    setBannerImage(brandImages[selectedBrand] || "/assets/default.png");
  }, [selectedBrand]);

  // Handle row selection
  const handleRowClick = (id) => setSelectedRowId(id);

  return (
    <>
    <div className="item-view-container">
      {/* Banner */}
      <section className="banner">
        <img src={bannerImage} alt="Brand Banner" className="brand-banner" />
      </section>
      {/* Alert  */}
      {message && <GeneralMessage message={message} />}

      {/* Table */}
      <table className="item-table">
        <thead>
          <tr>
            <th>Pattern</th>
            <th>Size</th>
            <th>PR</th>
            <th>Selling Price</th>
            <th>Stock</th>
          </tr>
        </thead>
        <tbody>
          {console.log(rows)}
          {rows.map((row) => (
            <tr
              key={row.itemID}
              className={selectedRowId === row.id ? "selected-row" : ""}
              onClick={() => handleRowClick(row.id)}
            >
              <td>{row.pattern}</td>
              <td>{row.tyreSize}</td>
              <td>{row.pr}</td>
              <td>{row.price}</td>
              <td>{row.stock}</td>
            </tr>
          ))}
        </tbody>
      </table>
      
      <div className="button-group">
        <button className="btn delete">Delete</button>
        <button className="btn update">Update</button>
        <button className="btn add" onClick={() => openModal(null)}>Add Item</button>
        <button className="btn info">More Info</button>
      </div>    

    </div>
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


          <ItemDetailsSection
            formData={formData}
            setFormData={setFormData}
            errors={inputFieldErrors}
            handleChange={handleChange}
          />


          <PriceDetailsSection/>


          <div className="formBtns">
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
          </div>  
        </form>
      </Modal>
    
    </>
  );
};

export default ItemView;
