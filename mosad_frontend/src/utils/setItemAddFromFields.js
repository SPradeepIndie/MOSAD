//set Add Item form fields
const setItemAddFromFields = (selectedCategory,selectedBrand) => {

  let defaultItemFields = {itemId: "",itemName:"",itemDescription:"",companyPrice:null,retailPrice:null,discount:null,availableQuantity: null};  
  let defaultTyreFields = {itemId: "",itemName:"",itemDescription:"",companyPrice:null,retailPrice:null,discount:null,tyreSize: "",pattern: "",availableQuantity: null,vehicleType: ""};
    
    if(selectedCategory === "Tyre" && selectedBrand !==null){
      return defaultTyreFields;
    }
    return defaultItemFields;
  };

export default setItemAddFromFields;