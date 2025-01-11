package org.rtss.mosad_backend.repository.bill_repository;

import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {}
