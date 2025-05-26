import React, { useState, useEffect, useRef } from "react";
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
import { useAddItem, useFetchItems, useDeleteItem, useUpdateItem, useFetchStockInHistory } from "../../hooks/servicesHook/useStockService";
import PopUp from "../../component/PopUp";
import ConfirmationDialog from "../../component/ConfirmationDialog";
import SearchComponent from "../../component/SearchComponent";
import Box from '@mui/material/Box';
import { useLocation } from "react-router-dom";
import useAuth from '../../hooks/useAuth';
import { DataGrid } from '@mui/x-data-grid';
import Paper from '@mui/material/Paper';
import StockInHistory from "./StockInHistory";
import { Button, Typography } from "@mui/material";
import { useAddRestockRequest } from "../../hooks/servicesHook/useNotificationService";
import GeneralSnackbarAlerts from "../../component/GeneralSnackbarAlerts";

const ItemView = () => {
  const addItem = useAddItem();
  const fetchItems = useFetchItems();
  const deleteItem = useDeleteItem();
  const updateItem = useUpdateItem();
  const fetchStockInHistory = useFetchStockInHistory();
  const sendRestockRequest = useAddRestockRequest();
  //Show alerts using snack bar
  const [showSnack, setShowSnack] = useState(false);
  const [alertType, setAlertType] = useState("warning");
  const [alertMsg, setAlertMsg] = useState("");
  const { auth } = useAuth();
  const passedStates = useLocation();
  const states = passedStates.state;

  //Store passed Category and Brand using Link state & useLocation
  const [selectedCategory, setSelectedCategory] = useState(states?.category);
  const [selectedBrand, setSelectedBrand] = useState(states?.brand);
  const [selectedBranch, setSelectedBranch] = useState(auth.branch); //Adjust based on your branch ID
  const [searchFilters, setSearchFilters] = useState({ itemName: "", tyreSize: "", vehicleType: "" });
  const [rows, setRows] = useState([]);
  const [stockInHistory, setStockInHistory] = useState([]);
  const [restockRequestDialog, setRestockRequestDialog] = useState(false);
  const [selectedRowId, setSelectedRowId] = useState(null);
  const [bannerImage, setBannerImage] = useState("");
  const [currentItem, setCurrentItem] = useState(null);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [formData, setFormData] = useState(setItemAddFromFields(selectedCategory, selectedBrand));
  const [message, setMessage] = useState(null);
  const [inputFieldErrors, setInputFieldErrors] = useState({});
  const [stockIn, setStockIn] = useState({ stockIn: "", date: new Date().toISOString().split("T")[0] });
  const [confirmationDialog, setConfirmationDialog] = useState(false);
  const dialogOpenRef = useRef(false); // Track whether dialog is open
  const [openStockInHistory, setOpenStockInHistory] = useState(false);

  const handleRestockSubmit =async () => {
    try {
      const response = await sendRestockRequest({msg:selectedBranch+" is low on "+selectedCategory+selectedBrand,type:"Low Stock"});
      setAlertMsg(response.data .message|| 'Success.')
      setAlertType("success")
      setShowSnack(true)
      setRestockRequestDialog(false);
    } catch (error) {
      setAlertMsg(error.response?.data || error.message || 'Table data  fetching failed.')
      setAlertType("warning")
      setShowSnack(true)
      setRestockRequestDialog(false);
    } 
  }

  const closeRestockDialog = () => {
    setRestockRequestDialog(false)
  }

  const handleOpenStockInHistory = () => {
    setOpenStockInHistory(true);
    fetchStockInHistory(selectedRowId)
      .then((response) => {

        setStockInHistory(response.data);
      })
      .catch((error) => console.error("Error fetching stock in history:", error));
  };

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
      setFormData(setItemAddFromFields(selectedCategory, selectedBrand));
    }
    setIsDialogOpen(true);
  };

  const closeDialog = () => {
    setInputFieldErrors({});
    setIsDialogOpen(false);
  };

  const validateAddForm = (key, value) => {
    setFormData((prevData) => ({ ...prevData, [key]: value }));
    let fieldError = "";
    if (!value) {
      fieldError = `${key.replace(/([A-Z])/g, " $1").trim()} is required.`;
    } else if ((key === "availableQuantity") && !Number.isInteger(Number(value))) {
      fieldError = "Quantity must be a valid integer.";
    } else if ((key === "companyPrice" || key === "retailPrice" || key === "discount") && !/^(-?\d+(\.\d+)?)$/.test(value)) {
      fieldError = "Price must be a valid number.";
    }
    else if (key === "discount" && (parseFloat(value) < 0 || parseFloat(value) > 100)) {
      fieldError = "Discount must be between 0 and 100.";
    }

    setInputFieldErrors((prevErrors) => {
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

    if (selectedCategory && selectedBrand && selectedBranch) {

      fetchItems({ params: { category: selectedCategory, brand: selectedBrand, branchId: selectedBranch, } })
        .then((response) => setRows(response.data))
        .catch((error) => console.error("Error fetching data:", error));


    }
    else {
      console.error("Error fetching data: Category, Brand and Branch are required");
    }

  }


  const handleRowClick = (id) => {

    setSelectedRowId((prevId) => {
      const newId = prevId === id ? null : id;

      return newId;
    });
  };

  useEffect(() => {

  }, [selectedRowId]);




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
    fetchandSetItems();



  }, [selectedBranch, selectedBrand]);

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
        "branchId": selectedBranch, // Adjust based on your branch ID
        "availableQuantity": parseInt(formData.availableQuantity)
      },
      "stockInDTO": {
        "quantity": parseInt(stockIn.stockIn),
        "date": stockIn.date
      }
    };


    const request = currentItem
      ? updateItem(formatedData)
      : addItem(formatedData);

    request
      .then((response) => {

        closeDialog();
        fetchandSetItems();
        setStockIn({ stockIn: "", date: new Date().toISOString().split("T")[0] });  // Reset stock in fields
        setMessage(
          currentItem
            ? { type: "success", text: "Item updated successfully!" }
            : { type: response.data.success ? "success" : "error", text: response.data.message }
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

  const handleSearchChange = (e) => {
    const { name, value } = e.target;
    setSearchFilters({ ...searchFilters, [name]: value });

  };

  const filteredRows = rows.filter((row) =>
    row.itemDTO.itemName.toLowerCase().includes(searchFilters.itemName.toLowerCase()) &&
    (row.itemTyreDTO?.tyreSize || "").toLowerCase().includes(searchFilters.tyreSize.toLowerCase()) &&
    (row.itemTyreDTO?.vehicleType || "").toLowerCase().includes(searchFilters.vehicleType.toLowerCase())
  );

  useEffect(
    () => {

      setRows([]);
    }, [selectedCategory]
  )


  //Table component

  const tableColumns = [
    { field: 'id', headerName: 'Item ID', width: 130 },
    { field: 'itemName', headerName: 'Name', width: 200 },
    { field: 'itemDescription', headerName: 'Description', width: 250 },
    { field: 'companyPrice', headerName: 'Official Selling Price', width: 150 },

    { field: 'availableQuantity', headerName: 'Available Quantity', width: 180 },
    ...(selectedCategory === 'Tyre' ? [
      { field: 'pattern', headerName: 'Pattern', width: 150 },
      { field: 'tyreSize', headerName: 'Tyre Size', width: 150 },
      { field: 'vehicleType', headerName: 'Vehicle Type', width: 180 },
    ] : []), // Add tyre-specific columns only if selectedCategory is 'Tyre'
  ];

  const tableRows = filteredRows.map((row) => ({
    id: row.itemDTO.itemId,
    itemName: row.itemDTO.itemName,
    itemDescription: row.itemDTO.itemDescription,
    companyPrice: row.itemDTO.companyPrice,

    availableQuantity: row.itemBranchDTO.availableQuantity,
    pattern: row.itemTyreDTO?.pattern || '',
    tyreSize: row.itemTyreDTO?.tyreSize || '',
    vehicleType: row.itemTyreDTO?.vehicleType || '',
  }));








  return (
    <>
   <GeneralSnackbarAlerts open={showSnack} type={alertType} msg={alertMsg} setOpen={setShowSnack}/>
      {message && <GeneralMessage message={message} />}
      {confirmationDialog && (
        <ConfirmationDialog
          message={"Are you sure you want to delete this item ID=" + selectedRowId + " ? "}
          onCancel={closeConfirmationDialog}
          onConfirm={() => {
            closeConfirmationDialog();
            handleDelete();
          }}
          isOpen={confirmationDialog}
        />
      )}
      <section className="banner">
        <img src={bannerImage} alt="Brand Banner" className="brand-banner" />

      </section>

      <Box sx={{ width: "95%", margin: "auto", padding: "20px", borderRadius: "8px" }}>
        <SearchComponent
          selectedCategory={selectedCategory}
          setSelectedCategory={setSelectedCategory}
          selectedBrand={selectedBrand}
          setSelectedBrand={setSelectedBrand}
          selectedBranch={selectedBranch}
          setSelectedBranch={setSelectedBranch}
          fetchandSetItems={fetchandSetItems}
          handleSearchChange={handleSearchChange}
          onItemView={true}
          auth={auth}
          states={states}

        />

      </Box>
      <div className="item-view-container">
        <Paper sx={{ height: 500, width: '95%', margin: "auto", padding: "20px", }}>
          <DataGrid
            rows={tableRows}
            columns={tableColumns}
            pageSize={5}
            rowsPerPageOptions={[5, 10]}
            onRowClick={(e) => { handleRowClick(e.row.id); }}
            disableColumnResize

            sx={{
              '& .MuiDataGrid-row.Mui-selected': {
                backgroundColor: '#a0d8a0', // Selected row color
                '&:hover': {
                  backgroundColor: '#a0d8af', // A different hover color for better visibility
                },
              },
              border: 0,
              '& .MuiDataGrid-root': {
                marginTop: '50px', // Adjust the table position to make space for the filter panel
                backgroundImage: `url(${bannerImage})`, // URL of the background image
                backgroundSize: 'cover', // Ensures the image covers the entire background
                backgroundPosition: 'center', // Center the background image
                backgroundRepeat: 'no-repeat', // Prevents the background image from repeating
              },
            }}

          />
        </Paper>



        <div className="button-group">

          <button className="btn update" onClick={() => {
            if (selectedRowId) {

              const selectedItem = rows.find(row => row.itemDTO.itemId === selectedRowId);
              openDialog(selectedItem);

            } else {
              setMessage({ type: "error", text: "Please select an item to update!" });
              setTimeout(() => setMessage(null), 2000);
            }
          }}>Update Stock</button>
          <button className="btn add" onClick={() => openDialog(null)}>Add Item</button>
          <button className="btn info" onClick={() => {
            if (selectedRowId) {

              handleOpenStockInHistory();


            } else {
              setMessage({ type: "error", text: "Please select an item to stock in!" });
              setTimeout(() => setMessage(null), 2000);
            }
          }}>StockIn History</button>
          {auth.roles[0] == "BRANCH_MANAGER" &&
            <Button variant="contained" onClick={() => setRestockRequestDialog(true)}>Send Restock request</Button>}

        </div>
      </div>
      <PopUp popUpTitle={currentItem ? "Update stock" : "Add New Item"}
        openPopup={isDialogOpen}
        setOpenPopup={setIsDialogOpen}
        onSubmit={handleSubmit}
        setCancelButtonAction={closeDialog}
        isDefaultButtonsDisplay={false}
      >

        <ItemDetailsForm
          formData={formData}
          setFormData={setFormData}
          setStockIn={setStockIn}
          stockIn={stockIn}
          errors={inputFieldErrors}
          handleChange={validateAddForm}
          onSubmit={handleSubmit}
          closeDialog={closeDialog}
          operationType={currentItem ? "Edit" : "Add"}
        />

      </PopUp>
      <PopUp popUpTitle={"Send Restock Message"}
        openPopup={restockRequestDialog}
        setOpenPopup={setRestockRequestDialog}
        onSubmit={handleRestockSubmit}
        setCancelButtonAction={closeRestockDialog}
        setOkButtonAction={handleRestockSubmit}
        isDefaultButtonsDisplay={true}
      >
        <Typography variant="subtitle1">
          Do you need to send a restock message for:
        </Typography>
        <Typography variant="body1">
          Category: {selectedCategory}
        </Typography>
        <Typography variant="body1">
          Brand: {selectedBrand}
        </Typography>

      </PopUp>


      <StockInHistory open={openStockInHistory} onClose={() => { setOpenStockInHistory(false); }} rows={stockInHistory} />







    </>
  );
};
export default ItemView;

