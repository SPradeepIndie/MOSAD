//set Add Item form fields
const setItemAddFromFields = (selectedCategory,selectedBrand) => {

  let defaultItemFields = {itemId: "",itemName:"",itemDescription:"",companyPrice:null,retailPrice:0,discount:0,availableQuantity: null};  
  let defaultTyreFields = {itemId: "",itemName:"",itemDescription:"",companyPrice:null,retailPrice:0,discount:0,tyreSize: "",pattern: "",vehicleType: "",availableQuantity: null};
    
    if(selectedCategory.toLowerCase() === "tyre" && selectedBrand !==null){
      return defaultTyreFields;
    }
    return defaultItemFields;
  };

export default setItemAddFromFields;