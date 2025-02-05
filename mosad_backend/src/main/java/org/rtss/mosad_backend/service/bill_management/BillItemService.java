package org.rtss.mosad_backend.service.bill_management;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto.bill_dtos.BillItemDTO;
import org.rtss.mosad_backend.dto_mapper.bill_dto_mapper.BillItemDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.repository.bill_repository.BillItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillItemService {

    private final BillItemRepository billItemRepository;
    private final BillItemDTOMapper billItemDTOMapper;

    @Autowired
    public BillItemService(BillItemRepository billItemRepository, BillItemDTOMapper billItemDTOMapper) {
        this.billItemRepository = billItemRepository;
        this.billItemDTOMapper = billItemDTOMapper;
    }

    @Transactional
    public BillItemDTO addBillItem(BillItem billItem) {
        BillItem savedBillItem =  billItemRepository.save(billItem);
        return billItemDTOMapper.toBillItemDTO(savedBillItem);
    }


    public List<BillItemDTO> getAllBillItems() {
        return billItemRepository.findAll().stream()
                .map(billItemDTOMapper::toBillItemDTO)
                .collect(Collectors.toList());
    }

}
