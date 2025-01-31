package org.rtss.mosad_backend.service.bill_management;

import jakarta.transaction.Transactional;
import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.rtss.mosad_backend.repository.bill_repository.BillItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillItemService {

    private final BillItemRepository billItemRepository;

    @Autowired
    public BillItemService(BillItemRepository billItemRepository) {
        this.billItemRepository = billItemRepository;
    }

    @Transactional
    public BillItem addBillItem(BillItem billItem) {
        return billItemRepository.save(billItem);
    }

}
