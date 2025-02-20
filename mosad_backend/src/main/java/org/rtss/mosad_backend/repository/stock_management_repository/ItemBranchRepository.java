package org.rtss.mosad_backend.repository.stock_management_repository;


import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemBranchRepository extends JpaRepository<ItemBranch, Long> {

    @Query(nativeQuery = true, value="SELECT * FROM item_branch WHERE item_id=:itemId and branch_id=:branchId")
    ItemBranch findByItemIdAndBranchId(Long itemId, Long branchId);

    @Query(value = "SELECT branch_id,branch_name FROM branch", nativeQuery = true)
    List<Branch> getAllBranches();


//    @Query(nativeQuery = true, value="UPDATE item_branch SET available_quantity=:quantity WHERE item_id=:itemId and branch_id=:branchId")
//    void updateItemQuantity(Long itemId, Long branchId, Integer quantity);

    @Modifying
    @Transactional
    @Query("UPDATE ItemBranch ib SET ib.availableQuantity = :quantity WHERE ib.item.id = :itemId AND ib.branch.id = :branchId")
    int updateItemQuantity(Long itemId, Long branchId, Integer quantity);
}
