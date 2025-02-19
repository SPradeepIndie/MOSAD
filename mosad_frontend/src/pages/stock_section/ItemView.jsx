import React, { useState, useEffect,useRef } from "react";
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
import ConfirmationDialog from "../../component/ConfirmationDialog";
import SearchComponent from "../../component/SearchComponent";

const ItemView = () => {
 
  //Store passed Category and Brand using Link state & useLocation
  const [selectedCategory, setSelectedCategory] = useState("Tyre");
  const [selectedBrand, setSelectedBrand] = useState("");
  const [selectedBranch, setSelectedBranch] = useState(1); //Adjust based on your branch ID




  const [rows, setRows] = useState([]);

  const [filter,setFilters] =useState({tyreSize:"",itemName:"",vehicleType:""});


  const handleFilterChange = (tireSize,itemName,vehicleType) => {
    
    setFilters({tyreSize:tireSize,itemName:itemName,vehicleType:vehicleType});
    setTimeout(() => {
      console.log(filter);
      console.log(selectedCategory,selectedBrand,selectedBranch);
    }, 1000);
  };

  const filteredRows = rows.filter((row) => {
    const itemNameMatch = filter.itemName?.trim() ? row.itemDTO?.itemName?.toLowerCase().includes(filter.itemName.trim().toLowerCase()) : true;
    const vehicleTypeMatch = filter.vehicleType?.trim() ? row.itemTyreDTO?.vehicleType?.toLowerCase().includes(filter.vehicleType.trim().toLowerCase()) : true;
    const tyreSizeMatch = filter.tyreSize?.trim() ? row.itemTyreDTO?.tyreSize?.toLowerCase().includes(filter.tyreSize.trim().toLowerCase()) : true;
    return itemNameMatch && vehicleTypeMatch && tyreSizeMatch;
  });
  
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [bannerImage, setBannerImage] = useState("");
  
  const [currentItem, setCurrentItem] = useState(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [formData, setFormData] = useState(setItemAddFromFields(selectedCategory,selectedBrand));
  const [message, setMessage] = useState(null);
  const [inputFieldErrors, setinputFieldErrors] = useState({});
  const [isPriceDetailsPopupOpen, setIsPriceDetailsPopupOpen] = useState(false);
  const [selectedItemPriceDetails, setSelectedItemPriceDetails] = useState(null);


  const [confirmationDialog, setConfirmationDialog] = useState(false);
  const dialogOpenRef = useRef(false); // ✅ Track whether dialog is open

  const openDialog = (item) => {
    if (item) {
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
    console.log("Fetching items for Category:", selectedCategory, "Brand:", selectedBrand, "Branch:", selectedBranch);
    if (selectedCategory && selectedBrand && selectedBranch) {
      
      fetchItems({ params: { category: selectedCategory, brand: selectedBrand, branchId: selectedBranch , } })
        .then((response) => setRows(response.data))
        .catch((error) => console.error("Error fetching data:", error));
      
    }
    else{
      console.error("Error fetching data: Category, Brand and Branch are required");
    }
    console.log(rows);  
  }

  
  const handleRowClick = (id) => {
    setSelectedRowId((prevId) => (prevId === id ? null : id)); // Toggle selection
    
  };

  const openConfirmationDialog = () => {
    if(selectedRowId !== null){
    dialogOpenRef.current = true; // ✅ Mark dialog as open
    setConfirmationDialog(true);
    }else{
      setMessage({ type: "error", text: "Please select an item to delete." });
      setTimeout(() => setMessage(null), 2000);
    }
  };
  
  const closeConfirmationDialog = () => {
    dialogOpenRef.current = false; // ✅ Mark dialog as closed
    setConfirmationDialog(false);
  };
  
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
    console.log("Fetch item called by useEffect!")


  }, [selectedCategory, selectedBrand,selectedBranch]);

  useEffect(() => {
    const handleOutsideClick = (event) => {
      if (
        dialogOpenRef.current || // ✅ Prevent deselection when dialog is open
        event.target.closest(".item-table") || 
        event.target.closest(".confirmation-dialog") || 
        event.target.closest(".confirmation-dialog-overlay")
      ) {
        return;
      }
  
      console.log("Clicked outside, deselecting row");
      setSelectedRowId(null);
    };
  
    document.addEventListener("click", handleOutsideClick);
  
    return () => {
      document.removeEventListener("click", handleOutsideClick);
    };
  }, [selectedRowId]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (Object.keys(inputFieldErrors).length > 0) {
      setMessage({ type: "error", text: "Please re-check all red fields!" });
      setTimeout(() => setMessage(null), 2000);
      return;
    }

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


  return (
    <>
      {message && <GeneralMessage message={message} />}
      {confirmationDialog && (
        <ConfirmationDialog
        message={"Are you sure you want to delete this item ID="+selectedRowId+" ? "}
        onCancel={closeConfirmationDialog}
        onConfirm={() => {
          closeConfirmationDialog();
          handleDelete();
        }}
        isOpen={confirmationDialog}
      />
      )}
      
      <SearchComponent 
        setSelectedBranch={setSelectedBranch} 
        setSelectedCategory={setSelectedCategory} 
        setSelectedBrand={setSelectedBrand}
        selectedCategory={selectedCategory}
        selectedBrand={selectedBrand}
        handleFilterChange={handleFilterChange}
        fetchandSetItems={fetchandSetItems}


        
        
      />
      <div className="item-view-container">
      
        <section className="banner">
          <img src={bannerImage} alt="Brand Banner" className="brand-banner" />
          
        </section>
        <table className="item-table">
          <thead>
          <h3>{selectedCategory} {selectedBrand}</h3>
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
            {filteredRows.map((row) => (
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
          <button className="btn delete" onClick={()=>openConfirmationDialog()}>Delete</button>
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

