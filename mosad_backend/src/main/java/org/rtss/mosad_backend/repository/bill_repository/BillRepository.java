package org.rtss.mosad_backend.repository.bill_repository;

import org.rtss.mosad_backend.entity.bill_management.Bill;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findBillByUser(Users user);

    List<Bill> findByUser(Users user);
}
