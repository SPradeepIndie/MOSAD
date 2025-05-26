package org.rtss.mosad_backend.repository.stock_management_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.entity.stock_management_entity.StockIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockInRepo extends JpaRepository<StockIn,Long> {

    List<StockIn> findAllByItem(Optional<Item> item);

}
