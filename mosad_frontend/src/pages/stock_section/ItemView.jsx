import React, { useState, useEffect } from "react";
import "./css/ItemView.css";
import GeneralMessage from "../../component/GeneralMessage";
import ItemDetailsForm from "../../forms/ItemDetailsForm";
import setItemAddFromFields from "../../utils/setItemAddFromFields";
import atlander_baner from "../../assets/atlander.png";
import presa_baner from "../../assets/presa.png";
import default_baner from "../../assets/default.png"
import dsi_baner from "../../assets/dsi.png"
import rapid_baner from "../../assets/rapid.jpg"
import linglong_baner from "../../assets/linglong.png"
import {fetchItems, addItem,updateItem } from "../../services/apiStockService";
import { useLocation } from "react-router-dom";
import PopUp from "../../component/PopUp";


const ItemView = () => {

  //Store passed Category and Brand using Link state & useLocation
  const location=useLocation();
  const states=location.state; //ex: states={category: 'Tyre', brand: 'RAPID'} can use for selectedCategory, selectedBrand props
 
  let selectedCategory=states.category;
  let selectedBrand=states.brand;

  const [rows, setRows] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [bannerImage, setBannerImage] = useState("");


  const cat_and_brand=(states.category+"_"+states.brand).toLowerCase();
  const [currentItem, setCurrentItem] = useState(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [formData, setFormData] = useState(setItemAddFromFields(cat_and_brand));
  const [message, setMessage] = useState(null);
  const [inputFieldErrors, setinputFieldErrors] = useState({});

  const openDialog = (item) => {
    if (item) {
      setCurrentItem(item);
      setFormData(item);
    } else {
      setCurrentItem(null);
      setFormData(setItemAddFromFields(cat_and_brand));
    }
    setIsDialogOpen(true);
  };

  const closeDialog = () => {
    setinputFieldErrors({});
    setIsDialogOpen(false);
  };

  const validateAddForm = (key, value) => {
    setFormData((prevData) => ({ ...prevData, [key]: value }));

    let fieldError = "";

    if (!value) {
      fieldError = `${key.replace(/([A-Z])/g, " $1").trim()} is required.`;
    } else if ((key === "tyreCount" || key==="ply") && !Number.isInteger(Number(value))) {
      fieldError = "Tyre Count must be a valid integer.";
    } else if (key === "officialSellingPrice" && !/^(-?\d+(\.\d+)?)$/.test(value)) {
      fieldError = "Official Selling Price must be a valid number.";
    }

    setinputFieldErrors((prevErrors) => {
      const updatedErrors = { ...prevErrors };
      if (fieldError) {
        updatedErrors[key] = fieldError;
      } else {
        delete updatedErrors[key];
      }
      return updatedErrors;
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (Object.keys(inputFieldErrors).length > 0) {
      setMessage({ type: "error", text: "Please re-check all red fields!" });
      setTimeout(() => setMessage(null), 2000);
      return;
    }

    const request = currentItem
      ? addItem(cat_and_brand,formData)
      : updateItem(cat_and_brand,formData);

    request
      .then(() => {
        console.log("Item Added successfully!");
        //fetchItems(); // Implement and call this function to fetch items after adding/updating
        closeDialog();
        console.log("Item added/updated successfully!");
        setMessage(currentItem ? { type: "success", text: "Item updated successfully!" } : { type: "success", text: "Item added successfully!" });
        setTimeout(() => setMessage(null), 3000);
      })
      .catch((error) => {
        console.log("Error adding/updating item:", error);
        setMessage(currentItem ? { type: "error", text: "Failed to update item!" } : { type: "error", text: "Failed to add item!" });
        setTimeout(() => setMessage(null), 3000);
      });
  };

  useEffect(() => {
    const brandImages = {
      atlander: atlander_baner,
      presa: presa_baner,
      dsi: dsi_baner,
      linglong: linglong_baner,
      rapid: rapid_baner
    };

    setBannerImage(brandImages[selectedBrand.toLowerCase()] || default_baner);

    if (selectedCategory && selectedBrand) {
      fetchItems({params:{category:selectedCategory,brand:selectedBrand}})
        .then((response) => setRows(response.data))
        .catch((error) => console.error("Error fetching data:", error));
    }
  }, [selectedCategory, selectedBrand]);


  const handleRowClick = (id) => setSelectedRowId(id);

  return (
    <>
    {message && <GeneralMessage message={message}/>}
      <div className="item-view-container">
        <section className="banner">
          <img src={bannerImage} alt="Brand Banner" className="brand-banner" />
        </section>
       

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
            {rows.map((row) => (
              <tr
                key={row.itemID}
                className={selectedRowId === row.id ? "selected-row" : ""}
                onClick={() => handleRowClick(row.id)}
              >
                <td>{row.pattern}</td>
                <td>{row.tyreSize}</td>
                <td>{row.ply}</td>
                <td>{row.officialSellingPrice}</td>
                <td>{row.tyreCount}</td>
              </tr>
            ))}
          </tbody>
        </table>

        <div className="button-group">
          <button className="btn delete">Delete</button>
          <button className="btn update">Update</button>
          <button className="btn add" onClick={() => openDialog(null)}>Add Item</button>
          <button className="btn info">More Info</button>
        </div>
      </div>

      <PopUp popUpTitle={currentItem ? "Edit Item" : "Add New Item"} openPopup={isDialogOpen} setOpenPopup={setIsDialogOpen} onSubmit={handleSubmit} setCancelButtonAction={closeDialog} isDefaultButtonsDisplay={false}>
        <ItemDetailsForm
          formData={formData}
          setFormData={setFormData}
          errors={inputFieldErrors}
          handleChange={validateAddForm}
          onSubmit={handleSubmit}
          closeDialog={closeDialog}
        />
      </PopUp>
    </>
  );
};

export default ItemView;
