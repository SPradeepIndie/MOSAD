package org.rtss.mosad_backend.dto.stock_management_dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemTyreRapidDTOTest {

    @Test
    void testItemTyreRapidDTO() {
        // Create a new instance of the DTO
        ItemTyreRapidDTO dto = new ItemTyreRapidDTO(1, "205/55R16", 50, 120.75, "Car");

        // Test getters
        Assertions.assertEquals(1, dto.getItemID());
        Assertions.assertEquals("205/55R16", dto.getTyreSize());
        Assertions.assertEquals(50, dto.getTyreCount());
        Assertions.assertEquals(120.75, dto.getOfficialSellingPrice());
        Assertions.assertEquals("Car", dto.getVehicleType());

        // Test setters
        dto.setItemID(2);
        dto.setTyreSize("225/45R17");
        dto.setTyreCount(30);
        dto.setOfficialSellingPrice(140.50);
        dto.setVehicleType("Truck");

        Assertions.assertEquals(2, dto.getItemID());
        Assertions.assertEquals("225/45R17", dto.getTyreSize());
        Assertions.assertEquals(30, dto.getTyreCount());
        Assertions.assertEquals(140.50, dto.getOfficialSellingPrice());
        Assertions.assertEquals("Truck", dto.getVehicleType());

        // Test toString
        String expectedString = "ItemTyreRapidDTO{itemID=2, tyreSize='225/45R17', tyreCount='30', officialSellingPrice=140.5, vehicleType='Truck'}";
        Assertions.assertEquals(expectedString, dto.toString());
    }
}
