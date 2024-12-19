package org.rtss.mosad_backend.repository.stock_management_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.ItemBattery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemBatteryRepo extends JpaRepository<ItemBattery,Integer> {
}
