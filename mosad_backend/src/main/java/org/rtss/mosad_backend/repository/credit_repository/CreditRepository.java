package org.rtss.mosad_backend.repository.credit_repository;

import org.rtss.mosad_backend.dto.credit_dtos.CreditDTO;
import org.rtss.mosad_backend.entity.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditRepository extends JpaRepository<Credit,Long> {
    @Query("SELECT c.creditId, c.balance, c.dueDate, cu.name, cc.contactNumber, r.repaymentId, r.date, r.amount,b.id " +
            "FROM Credit c " +
            "JOIN c.customer cu " +
            "JOIN cu.contacts cc " +
            "JOIN c.bill b " +
            "LEFT JOIN c.repayments r")
    List<Object[]> findAllCreditDetails();

    @Query("SELECT c.creditId, c.balance, c.dueDate, cu.name, cc.contactNumber, r.repaymentId, r.date, r.amount " +
            "FROM Credit c " +
            "JOIN c.customer cu " +
            "JOIN cu.contacts cc " +
            "LEFT JOIN c.repayments r " +
            "WHERE c.customer.id = :customerId")
    List<Object[]> findAllCreditDetailsByCustomerId(Long customerId);

    @Query(value="SELECT * FROM credit WHERE due_date=?1 ", nativeQuery = true)
    List<Credit> findCreditByDueDate(Date date);







}
