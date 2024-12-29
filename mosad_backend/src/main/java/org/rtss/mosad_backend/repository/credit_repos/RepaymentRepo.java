package org.rtss.mosad_backend.repository.credit_repos;

import org.rtss.mosad_backend.entity.credit.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepaymentRepo extends JpaRepository<Repayment,Long> {
}
