package org.rtss.mosad_backend.dto_mapper;

import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.springframework.stereotype.Component;

@Component
public class BillItemDTOMapper {

    public BillItemDTO toDTO(BillItem item) {
        BillItemDTO dto = new BillItemDTO();
        dto.setId(item.getId());
        dto.setItemId(item.getItem().getItemId());
        dto.setDescription(item.getDescription());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }
}
