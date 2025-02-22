package org.rtss.mosad_backend.repository.stock_management_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ItemTyreRepo extends JpaRepository<ItemTyre,Long> {


    ItemTyre findByItem(Item item);

    void deleteItemTyreByItem(Item item);

    @Query
    List<ItemTyre> findByItem_Brand_BrandNameAndTyreSize (String brand, String size);

    List<ItemTyre> findItemTyreByVehicleType(String vehicleType);

    ItemTyre findByTyreSize(String tyreSize);
}
