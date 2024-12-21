package org.rtss.mosad_backend.repository.stock_management_repository;

import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreAtlander;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemTyreLinglong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemTyreLinglongRepo extends JpaRepository<ItemTyreLinglong,Integer> {
    List<ItemTyreLinglong> findBytyreSize(String tyreSize);

    List<ItemTyreLinglong> tyreSize(String tyreSize);
}
