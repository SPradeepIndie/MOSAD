package org.rtss.mosad_backend.dto_mapper.bill_dto_mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillItemDTOMapperTest {

    private BillItemDTOMapper billItemDTOMapper;

    @BeforeEach
    public void setUp() {
        billItemDTOMapper = new BillItemDTOMapper();
    }

    @Test
    public void testToBillItemDTO() {
        // Create a BillItem object with sample data
        BillItem billItem = new BillItem();
        billItem.setBillItemId(1L);
        billItem.setDescription("Test Item");
        billItem.setQuantity(10);
        billItem.setUnitPrice(100.0);

        // Call the method under test
        BillItemDTO dto = billItemDTOMapper.toBillItemDTO(billItem);

        // Assert the result
        assertEquals(billItem.getBillItemId(), dto.getBillItemId());
        assertEquals(billItem.getDescription(), dto.getDescription());
        assertEquals(billItem.getQuantity(), dto.getQuantity());
        assertEquals(billItem.getUnitPrice(), dto.getUnitPrice());
    }

    @Test
    public void testToBillItemEntity() {
        // Create a BillItemDTO object with sample data
        BillItemDTO dto = new BillItemDTO();
        dto.setBillItemId(1L);
        dto.setDescription("Test Item");
        dto.setQuantity(10);
        dto.setUnitPrice(100.0);
        dto.setItemId(2L); // Assuming itemId is part of the DTO

        // Call the method under test
        BillItem billItem = billItemDTOMapper.toBillItemEntity(dto);

        // Assert the result
        assertEquals(dto.getBillItemId(), billItem.getBillItemId());
        assertEquals(dto.getDescription(), billItem.getDescription());
        assertEquals(dto.getQuantity(), billItem.getQuantity());
        assertEquals(dto.getUnitPrice(), billItem.getUnitPrice());

        // Verify the Item entity is set correctly
        assertEquals(dto.getItemId(), billItem.getBill().getBillItems());
    }
}