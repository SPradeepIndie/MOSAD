package org.rtss.mosad_backend.service.bill_management;

import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.repository.bill_repository.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }


    // Additional methods like getBillById, deleteBill, etc.
}

