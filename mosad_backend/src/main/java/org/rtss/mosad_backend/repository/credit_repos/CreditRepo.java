package org.rtss.mosad_backend.repository.credit_repos;

import org.rtss.mosad_backend.entity.credit.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepo extends JpaRepository<Credit,Long> {
}
