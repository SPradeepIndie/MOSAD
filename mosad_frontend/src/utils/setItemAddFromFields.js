import { Category } from "@mui/icons-material";

//set Add Item form fields
const setItemAddFromFields = (cat_and_brand) => {
    
    //const cat_and_brand=(title.category+"_"+title.brand).toLowerCase();
    //console.log(cat_and_brand);
    switch (cat_and_brand) {
      case "tyre_ceat":
       
        return {
          itemID: "",
          size: "",
          pattern: "",
          pr: 0,
          tyreCount: 0,
          officialSellingPrice: 0,
          vehicleType: "",
        };
      case "tyre_presa":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          ply: 0,
          tyreCount: 0,
          officialSellingPrice: 0,
          vehicleType: "",
        };
      case "tyre_rapid":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          tyreCount: 0,
          officialSellingPrice: 0,
          vehicleType: "",
        };
      case "tyre_linglong":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          tyreCount: null,
          officialSellingPrice: null,
          vehicleType: "",
        };
      case "tyre_atlander":
        return {
          itemID: "",
          tyreSize: "",
          pattern: "",
          tyreCount: 0,
          officialSellingPrice: 0,
          vehicleType: "",
        };
        
      default:
        return {
          itemID: "",
          field1: "",
          field2: "",
        };
    }
  };

  export default setItemAddFromFields;