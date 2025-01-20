package org.rtss.mosad_backend.repository.bill_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Add custom queries if necessary
}
