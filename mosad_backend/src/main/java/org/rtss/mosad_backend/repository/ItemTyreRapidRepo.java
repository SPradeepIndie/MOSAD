package org.rtss.mosad_backend.repository;

import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreRapid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTyreRapidRepo extends JpaRepository<ItemTyreRapid,Integer> {
}
