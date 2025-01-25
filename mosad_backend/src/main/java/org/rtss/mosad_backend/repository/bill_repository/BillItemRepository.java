package org.rtss.mosad_backend.repository.bill_repository;

import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {}
