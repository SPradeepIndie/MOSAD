package org.rtss.mosad_backend.dto_mapper.bill_dto_mapper;

import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.springframework.stereotype.Component;

@Component
public class BillItemDTOMapper {

    public BillItemDTO toBillItemDTO(BillItem billItem) {
        BillItemDTO dto = new BillItemDTO();
        dto.setBillItemId(billItem.getBillItemId());
        //dto.setItemId(item.getItem().getItemId());
        dto.setDescription(billItem.getDescription());
        dto.setQuantity(billItem.getQuantity());
        dto.setUnitPrice(billItem.getUnitPrice());
        return dto;
    }

    public BillItem toBillItemEntity(BillItemDTO dto) {
        BillItem billItem = new BillItem();
        billItem.setBillItemId(dto.getBillItemId()); // Fixed method name

        // Assuming you have an Item entity and need to set it using itemId
        Item item = new Item();
        item.setItemId(dto.getItemId());
        //billItem.setItem(item);

        billItem.setDescription(dto.getDescription());
        billItem.setQuantity(dto.getQuantity());
        billItem.setUnitPrice(dto.getUnitPrice());

        return billItem;
    }

}
