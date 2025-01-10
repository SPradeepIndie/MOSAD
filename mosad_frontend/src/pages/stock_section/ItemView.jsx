import React, { useState, useEffect } from "react";
import "./css/ItemView.css";
import GeneralMessage from "../../component/GeneralMessage";
import ItemDetailsSection from "../../component/ItemDetailsSection";
import PriceDetailsSection from "../../component/PriceDetailsSection";
import setItemAddFromFields from "../../utils/setItemAddFromFields";
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from "@mui/material";
import useGlobalAccess from "../../hooks/useGlobalAccess";
import { fetchItems } from "../../services/apiStockService";
import atlander_baner from "../../assets/atlander.png";
import presa_baner from "../../assets/presa.png";
import default_baner from "../../assets/default.png"
import dsi_baner from "../../assets/dsi.png"
import rapid_baner from "../../assets/rapid.jpg"
import linglong_baner from "../../assets/linglong.png"

const ItemView = () => {
  const { selectedCategory, selectedBrand } = useGlobalAccess();
  const [rows, setRows] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [bannerImage, setBannerImage] = useState("");


  //const title="tyre_linglong";
  const [currentItem, setCurrentItem] = useState(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [formData, setFormData] = useState(setItemAddFromFields());
  const [message, setMessage] = useState(null);
  const [inputFieldErrors, setinputFieldErrors] = useState({});

  const openDialog = (item) => {
    if (item) {
      setCurrentItem(item);
      setFormData(item);
    } else {
      setCurrentItem(null);
      setFormData(setItemAddFromFields(title));
    }
    setIsDialogOpen(true);
  };

  const closeDialog = () => {
    setIsDialogOpen(false);
  };

  const validateAddForm = (key, value) => {
    setFormData((prevData) => ({ ...prevData, [key]: value }));

    let fieldError = "";

    if (!value) {
      fieldError = `${key.replace(/([A-Z])/g, " $1").trim()} is required.`;
    } else if (key === "tyreCount" && !Number.isInteger(Number(value))) {
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

    // const request = currentItem
    //   ? axios.put(`http://localhost:8080/api/v1/stock/${title}/${formData.itemID}`, formData)
    //   : axios.post(`http://localhost:8080/api/v1/stock/${title}`, formData);

    // request
    //   .then(() => {
    //     fetchItems();
    //     closeDialog();
    //     setMessage(currentItem ? { type: "success", text: "Item updated successfully!" } : { type: "success", text: "Item added successfully!" });
    //     setTimeout(() => setMessage(null), 3000);
    //   })
    //   .catch((error) => {
    //     setMessage(currentItem ? { type: "error", text: "Failed to update item!" } : { type: "error", text: "Failed to add item!" });
    //     setTimeout(() => setMessage(null), 3000);
    //   });
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

      <Dialog open={isDialogOpen} onClose={closeDialog} fullWidth maxWidth="md">
        <DialogTitle>{currentItem ? "Edit Item" : "Add New Item"}</DialogTitle>
        <DialogContent>
        {message && <GeneralMessage message={message} />}
          <form onSubmit={handleSubmit}>
            <ItemDetailsSection
              formData={formData}
              setFormData={setFormData}
              errors={inputFieldErrors}
              handleChange={validateAddForm}
            />
            <PriceDetailsSection />
          </form>
        </DialogContent>
        <DialogActions>
          <Button onClick={closeDialog} color="secondary">Cancel</Button>
          <Button onClick={handleSubmit} color="primary">Submit</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default ItemView;
