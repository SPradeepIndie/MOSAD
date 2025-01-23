//set Add Item form fields
const setItemAddFromFields = (cat_and_brand) => {

  let defaultItemFields = {itemID: "",availableQty:null,officialSellingPrice: null,companyPrice:null,discountPrice:null};
    
  let defaultTyreFields = {itemID: "",itemName:"",itemDescription:"",companyPrice:null,retailPrice:null,discount:null,tyreSize: "",pattern: "",availableQuantity: null,officialSellingPrice: null,vehicleType: ""};
    
    switch (cat_and_brand) {
      case "tyre_ceat":
       
        return {...defaultTyreFields,pr:null};
        
      case "tyre_presa":
        return {...defaultTyreFields,ply:null};
      case "tyre_rapid":
        return defaultTyreFields;
      case "tyre_linglong":
        return defaultTyreFields;
      case "tyre_atlander":
        return defaultTyreFields;
        
      default:
        return defaultTyreFields;
    }
  };

  export default setItemAddFromFields;