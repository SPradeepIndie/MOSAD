package org.rtss.mosad_backend.dto_mapper;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BillDTOMapper {

    public BillDTO toDTO(Bill bill) {
        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setAdvance(bill.getAdvance());
        dto.setBalance(bill.getBalance());
        dto.setDate(bill.getDate());
        dto.setCustomerId(bill.getCustomer() != null ? bill.getCustomer().getId() : null);
        dto.setItems(bill.getItems().stream().map(this::toItemDTO).collect(Collectors.toList()).reversed());
        return dto;
    }

    public Bill toEntity(BillDTO dto) {
        Bill bill = new Bill();
        bill.setId(dto.getId());
        bill.setTotalAmount(dto.getTotalAmount());
        bill.setAdvance(dto.getAdvance());
        bill.setBalance(dto.getBalance());
        bill.setDate(dto.getDate());
        return bill;
    }

    private BillItemDTO toItemDTO(BillItem item) {
        BillItemDTO dto = new BillItemDTO();
        dto.setId(item.getId());
        dto.setItemId(item.getItem().getItemId());
        dto.setDescription(item.getDescription());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }
}
