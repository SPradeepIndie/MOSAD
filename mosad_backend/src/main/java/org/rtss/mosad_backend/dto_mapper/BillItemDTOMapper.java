package org.rtss.mosad_backend.dto_mapper;

import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.springframework.stereotype.Component;

@Component
public class BillItemDTOMapper {

    public BillItemDTO toDTO(BillItem item) {
        BillItemDTO dto = new BillItemDTO();
        dto.setBillItemId(dto.getBillItemId());  // Fixed incorrect method name (getid -> getId)
        dto.setItemId(item.getItem().getItemId());
        dto.setDescription(item.getDescription());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }

    public BillItem toEntity(BillItemDTO dto) {
        BillItem billItem = new BillItem();
        billItem.setBillItemId(dto.getBillItemId()); // Fixed method name

        // Assuming you have an Item entity and need to set it using itemId
        Item item = new Item();
        item.setItemId(dto.getItemId());
        billItem.setItem(item);

        billItem.setDescription(dto.getDescription());
        billItem.setQuantity(dto.getQuantity());
        billItem.setUnitPrice(dto.getUnitPrice());

        return billItem;
    }

}
