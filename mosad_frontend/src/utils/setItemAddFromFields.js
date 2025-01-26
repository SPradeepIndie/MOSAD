//set Add Item form fields
const setItemAddFromFields = (selectedCategory,selectedBrand) => {
    
  let defaultTyreFields = {
    itemId: "",
    itemName:"",
    itemDescription:"",
    companyPrice:null,
    retailPrice:null,
    discount:null,
    tyreSize: "",
    pattern: "",
    availableQuantity: null,
    vehicleType: ""
  };
    
    if(selectedCategory === "Tyre" && selectedBrand !==null){
      return defaultTyreFields;
    }
  };

export default setItemAddFromFields;