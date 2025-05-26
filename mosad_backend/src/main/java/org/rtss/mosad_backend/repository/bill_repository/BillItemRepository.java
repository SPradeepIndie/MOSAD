package org.rtss.mosad_backend.repository.bill_repository;

import org.rtss.mosad_backend.entity.bill_management.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BillItemRepository extends JpaRepository<BillItem, Long> {


}
