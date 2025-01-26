package org.rtss.mosad_backend.service.bill_management;

import org.rtss.mosad_backend.dto.bill_dtos.BillDTO;
import org.rtss.mosad_backend.dto_mapper.BillDTOMapper;
import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDTOMapper billDTOMapper;

    public BillDTO createBill(BillDTO billDTO) {
        Bill bill = billDTOMapper.toEntity(billDTO);
        Bill savedBill = billRepository.save(bill);
        return billDTOMapper.toDTO(savedBill);
    }

    public List<BillDTO> getAllBills() {
        return billRepository.findAll().stream()
                .map(billDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BillDTO getBillById(Long id) {
        return billRepository.findById(id)
                .map(billDTOMapper::toDTO)
                .orElse(null);
    }
}
