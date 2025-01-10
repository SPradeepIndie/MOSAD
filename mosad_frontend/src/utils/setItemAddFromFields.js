//set Add Item form fields
const setItemAddFromFields = (cat_and_brand) => {

  let defaultItemFields = {itemID: "",availableQty:0,officialSellingPrice: 0,companyPrice:0,discountPrice:0};
    
  let defaultTyreFields = {itemID: "",size: "",pattern: "",tyreCount: 0,officialSellingPrice: 0,vehicleType: ""};
    
    switch (cat_and_brand) {
      case "tyre_ceat":
       
        return {...defaultTyreFields,pr:0};
        
      case "tyre_presa":
        return {...defaultTyreFields,ply:0};
      case "tyre_rapid":
        return defaultTyreFields;
      case "tyre_linglong":
        return defaultTyreFields;
      case "tyre_atlander":
        return defaultTyreFields;
        
      default:
        return defaultItemFields;
    }
  };

  export default setItemAddFromFields;