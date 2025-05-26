package org.rtss.mosad_backend.repository.credit_repository;

import org.rtss.mosad_backend.entity.credit.Credit;
import org.rtss.mosad_backend.entity.credit.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RepaymentRepository extends JpaRepository<Repayment,Long> {
    Set<Repayment> findRepaymentsByCredit(Credit credit);

}
