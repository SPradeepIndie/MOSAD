package org.rtss.mosad_backend.dto_mapper;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;


public class BillDTOMapper {

    public static BillDTO toDTO(Bill bill) {
        BillDTO dto = new BillDTO();
        dto.setBillId(bill.getBillId());
        dto.setTotalAmount(bill.getTotalAmount());
        dto.setAdvance(bill.getAdvance());
        dto.setBalance(bill.getBalance());
        dto.setDate(bill.getDate());
        return dto;
    }

    public static Bill toEntity(BillDTO dto) {
        Bill bill = new Bill();
        bill.setBillId(dto.getBillId());
        bill.setTotalAmount(dto.getTotalAmount());
        bill.setAdvance(dto.getAdvance());
        bill.setBalance(dto.getBalance());
        bill.setDate(dto.getDate());
        return bill;
    }
}
