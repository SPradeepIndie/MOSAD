//set form fields
const setItemAddFromFields = (title) => {
    console.log(title);
    switch (title) {
      case "CEAT":
       
        return {
          itemID: "",
          size: "",
          pattern: "",
          pr: 0,
          availableQty: 0,
          osp: 0,
          cp:0
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