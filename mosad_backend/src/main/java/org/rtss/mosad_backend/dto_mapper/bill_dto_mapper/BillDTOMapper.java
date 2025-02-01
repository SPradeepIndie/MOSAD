package org.rtss.mosad_backend.dto_mapper.bill_dto_mapper;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.bill_management.BillItem;

import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.stream.Collectors;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;

@Component
public class BillDTOMapper {

    private final BillItemDTOMapper billItemDTOMapper; // Inject dependency


    public BillDTOMapper(BillItemDTOMapper billItemDTOMapper) {
        this.billItemDTOMapper = billItemDTOMapper;
    }

    public BillDTO toDTO(Bill bill) {
        BillDTO billdto = new BillDTO();
        billdto.setBillId(bill.getBillId());
        billdto.setTotalAmount(bill.getTotalAmount());
        billdto.setAdvance(bill.getAdvance());
        billdto.setBalance(bill.getBalance());
        billdto.setDate(bill.getDate());
        billdto.setCustomerDTO(bill.getCustomer() != null
                ? new CustomerDTO(bill.getCustomer()) : null);
        billdto.setBillItemDTO(bill.getBillItems() != null
                ? bill.getBillItems().stream().map(billItemDTOMapper :: toBillItemDTO).collect(Collectors.toList())
                : null);

        return billdto;
    }


    public Bill toEntity(BillDTO dto) {
        Bill bill = new Bill();
        bill.setBillId(dto.getBillId());
        bill.setTotalAmount(dto.getTotalAmount());
        bill.setAdvance(dto.getAdvance());
        bill.setBalance(dto.getBalance());
        bill.setDate(dto.getDate());
        return bill;
    }
}
