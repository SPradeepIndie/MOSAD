package org.rtss.mosad_backend.repository.credit_repository;

import org.rtss.mosad_backend.entity.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {
    @Query("SELECT c.creditId, c.balance, c.dueDate, cu.name, cc.contactNumber, r.repaymentId, r.date, r.amount " +
            "FROM Credit c " +
            "JOIN c.customer cu " +
            "JOIN cu.contacts cc " +
            "LEFT JOIN c.repayments r")
    List<Object[]> findAllCreditDetails();

}
