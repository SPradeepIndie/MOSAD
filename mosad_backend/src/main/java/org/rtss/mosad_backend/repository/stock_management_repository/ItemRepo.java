package org.rtss.mosad_backend.repository.stock_management_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.Brand;
import org.rtss.mosad_backend.entity.stock_management_entity.Category;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ItemRepo extends JpaRepository<Item, Long> {

    List<Item> findByCategoryAndBrand(Category category, Brand brand);

    List<Item> findByItemNameContainingIgnoreCase(String name);

    @Query(value = "SELECT * FROM Item WHERE item_name ILIKE CONCAT('%', :name, '%')", nativeQuery = true)
    List<Item> findItemsByItemNameContainsIgnoreCase(String name);

    @Query(value = "SELECT * FROM Item WHERE LOWER(item_name) = LOWER(:itemName) AND category_id = :category AND brand_id = :brand", nativeQuery = true)
    List<Item> findItemsByItemNameAndCategoryAndBrand(String itemName, Long category, Long brand);

}
