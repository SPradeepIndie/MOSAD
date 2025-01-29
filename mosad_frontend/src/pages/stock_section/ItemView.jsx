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
import { addItem, fetchItems, deleteItem, updateItem } from "../../services/apiStockService";
import { useLocation } from "react-router-dom";
import PopUp from "../../component/PopUp";
import PriceDetailsSection from "../../component/PriceDetailsSection";
import useAuth  from "../../hooks/useAuth";

const ItemView = () => {
  const {auth}=useAuth();
  //console.log(auth);
  //Store passed Category and Brand using Link state & useLocation
  const location = useLocation();
  const states = location.state; //ex: states={category: 'Tyre', brand: 'RAPID'} can use for selectedCategory, selectedBrand props
  let selectedCategory = states.category;
  let selectedBrand = states.brand;
  let selectedBranch = 1; //Adjust based on your branch ID

  const [rows, setRows] = useState([]);
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [bannerImage, setBannerImage] = useState("");
  
  const [currentItem, setCurrentItem] = useState(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [formData, setFormData] = useState(setItemAddFromFields(selectedCategory,selectedBrand));
  const [message, setMessage] = useState(null);
  const [inputFieldErrors, setinputFieldErrors] = useState({});
  const [isPriceDetailsPopupOpen, setIsPriceDetailsPopupOpen] = useState(false);
  const [selectedItemPriceDetails, setSelectedItemPriceDetails] = useState(null);

  const openDialog = (item) => {
    if (item) {
      //console.log("Item to edit:", item);
      const formattedItem = {
        itemId: item.itemDTO.itemId,
        itemName: item.itemDTO.itemName,
        itemDescription: item.itemDTO.itemDescription,
        companyPrice: item.itemDTO.companyPrice,
        retailPrice: item.itemDTO.retailPrice,
        discount: item.itemDTO.discount,
        availableQuantity: item.itemBranchDTO.availableQuantity,
        ...(selectedCategory === "Tyre" && {
          tyreSize: item.itemTyreDTO.tyreSize,
          pattern: item.itemTyreDTO.pattern,
          vehicleType: item.itemTyreDTO.vehicleType
        })
        
      };
      //console.log("Formatted Item to update:", formattedItem);

      setCurrentItem(formattedItem);
      setFormData(formattedItem);
    } else {
      setCurrentItem(null);
      setFormData(setItemAddFromFields(selectedCategory,selectedBrand));
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
    } else if ((key === "availableQuantity" ) && !Number.isInteger(Number(value))) {
      fieldError = "Quantity must be a valid integer.";
    } else if ((key === "companyPrice" || key==="retailPrice" || key==="discount") && !/^(-?\d+(\.\d+)?)$/.test(value)) {
      fieldError = "Price must be a valid number.";
    }
    else if( key === "discount" && (parseFloat(value) < 0 || parseFloat(value) > 100)){
      fieldError = "Discount must be between 0 and 100.";
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

 

  const fetchandSetItems = async () => {
    if (selectedCategory && selectedBrand) {
      fetchItems({ params: { category: selectedCategory, brand: selectedBrand, branchId: selectedBranch } })
        .then((response) => setRows(response.data))
        .catch((error) => console.error("Error fetching data:", error));
    }
  }

  const handleDelete = () => {
    if (selectedRowId !== null) {
      const selectedItem = rows.find((row) => row.itemDTO.itemId === selectedRowId);
      deleteItem(selectedItem.itemDTO.itemId)
        .then(() => {
          setMessage({ type: "success", text: "Item deleted successfully!" });
          setRows(rows.filter((row) => row.itemDTO.itemId !== selectedRowId));
          setSelectedRowId(null);
          setTimeout(() => setMessage(null), 3000);
        })
        .catch((error) => {
          console.error("Error deleting item:", error);
          setMessage({ type: "error", text: "Failed to delete item!" });
          setTimeout(() => setMessage(null), 3000);
        });
    } else {
      setMessage({ type: "error", text: "Please select an item to delete." });
      setTimeout(() => setMessage(null), 3000);
    }
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





    fetchandSetItems();

    const handleOutsideClick = (event) => {
      if (!event.target.closest(".item-table")) {
        setSelectedRowId(null); // Deselect row when clicking outside the table
      }
    };

    document.addEventListener("click", handleOutsideClick);

    return () => {
      document.removeEventListener("click", handleOutsideClick);
    };


  }, [selectedCategory, selectedBrand]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (Object.keys(inputFieldErrors).length > 0) {
      setMessage({ type: "error", text: "Please re-check all red fields!" });
      setTimeout(() => setMessage(null), 2000);
      return;
    }

    //console.log("Form Data:", formData);
    const formatedData = {
      "itemDTO": {
        "itemId": currentItem ? currentItem.itemId : null,
        "itemName": formData.itemName,
        "itemDescription": formData.itemDescription,
        "companyPrice": parseFloat(formData.companyPrice),
        "retailPrice": parseFloat(formData.retailPrice),
        "discount": parseFloat(formData.discount),
        "category": selectedCategory,
        "brand": selectedBrand
      },
      "itemTyreDTO": {
        "tyreSize": formData.tyreSize,
        "pattern": formData.pattern,
        "vehicleType": formData.vehicleType
      },
      "itemBranchDTO": {
        "branchId": 1, // Adjust based on your branch ID
        "availableQuantity": parseInt(formData.availableQuantity)
      }
    };

    //console.log(formatedData);
    const request = currentItem
      ? updateItem(formatedData)
      : addItem(formatedData);

    request
      .then((response) => {
        console.log(currentItem ? "Item updated successfully!" :"Backend - "+ response.data.message);
        closeDialog();
        fetchandSetItems();
        setMessage(
          currentItem
            ? { type: "success", text: "Item updated successfully!" }
            : { type: response.data.success ? "success": "error", text:  response.data.message }
        );
        setTimeout(() => setMessage(null), 3000);
      })
      .catch((error) => {
        console.error(currentItem ? "Error updating item:" : "Error adding item:", error);
        setMessage(
          currentItem
            ? { type: "error", text: "Failed to update item!" }
            : { type: "error", text: "Failed to add item!" }
        );
        setTimeout(() => setMessage(null), 3000);
      });
  };




  const handleRowClick = (id) => {
    setSelectedRowId((prevId) => (prevId === id ? null : id)); // Toggle selection
    console.log("Selected Row ID:", selectedRowId);
  };

  return (
    <>
      {message && <GeneralMessage message={message} />}
      <div className="item-view-container">
        <section className="banner">
          <img src={bannerImage} alt="Brand Banner" className="brand-banner" />
        </section>
        <table className="item-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Description</th>
              <th>Company Price</th>
              <th>Retail Price</th>
              <th>Discount</th>
              <th>Available Quantity</th>
              { selectedCategory === "Tyre" &&
              <>
              <th>Pattern</th>
              <th>Tyre Size</th>
              <th>Vehicle Type</th>
              </>
              }
              
            </tr>
          </thead>
          <tbody>
            {rows.map((row) => (
              <tr
                key={row.itemDTO.itemId}
                className={selectedRowId === row.itemDTO.itemId ? "selected-row" : ""}
                onClick={() => {
                  console.log("Row clicked:", row.itemDTO.itemId);
                  
                  handleRowClick(row.itemDTO.itemId);

                }}
              >
                <td>{row.itemDTO.itemName}</td>
                <td>{row.itemDTO.itemDescription}</td>
                <td>{row.itemDTO.companyPrice}</td>
                <td>{row.itemDTO.retailPrice}</td>
                <td>{row.itemDTO.discount}</td>
                <td>{row.itemBranchDTO.availableQuantity}</td>
                {selectedCategory === "Tyre" &&
                  <>
                    <td>{row.itemTyreDTO.pattern}</td>
                    <td>{row.itemTyreDTO.tyreSize}</td>
                    <td>{row.itemTyreDTO.vehicleType}</td>
                  </>
                }
              </tr>
            ))}
          </tbody>
        </table>
        <div className="button-group">
          <button className="btn delete" onClick={handleDelete}>Delete</button>
          <button className="btn update" onClick={() => {
            if (selectedRowId) {
              console.log("On Update Selected Row ID:", selectedRowId);
              const selectedItem = rows.find(row =>row.itemDTO.itemId === selectedRowId);
              openDialog(selectedItem);

            } else {
              setMessage({ type: "error", text: "Please select an item to update!" });
              setTimeout(() => setMessage(null), 2000);
            }
          }}>Update</button>
          <button className="btn add" onClick={() => openDialog(null)}>Add Item</button>
          <button className="btn info"onClick={() => {
            if (selectedRowId) {
              const selectedItem = rows.find((row) => row.itemDTO.itemId === selectedRowId);
              setSelectedItemPriceDetails({
                officialSellingPrice: selectedItem.itemDTO.companyPrice || 0,
                discount: selectedItem.itemDTO.discount || 0,
              });
              setIsPriceDetailsPopupOpen(true);
            }else {
              setMessage({ type: "error", text: "Please select an item to view price details!" });
              setTimeout(() => setMessage(null), 2000);
            }
          }}>More Info</button>
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
      <PopUp
        popUpTitle="Price Details"
        openPopup={isPriceDetailsPopupOpen}
        setOpenPopup={setIsPriceDetailsPopupOpen}
        onSubmit={() => setIsPriceDetailsPopupOpen(false)} 
        setCancelButtonAction={() => setIsPriceDetailsPopupOpen(false)} 
        isDefaultButtonsDisplay={false} 
      >
        <PriceDetailsSection
        officialSellingPrice={selectedItemPriceDetails?.officialSellingPrice || ""}
        discount={selectedItemPriceDetails?.discount || ""}
      />
      </PopUp>
    </>
  );
};
export default ItemView;

