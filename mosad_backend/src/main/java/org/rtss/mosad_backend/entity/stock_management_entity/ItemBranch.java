package org.rtss.mosad_backend.entity.stock_management_entity;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.branch_management.Branch;

@Entity
@Table(name = "item_branch", uniqueConstraints = @UniqueConstraint(columnNames = {"itemId", "branchId"}))
public class ItemBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "branchId", nullable = false)
    private Branch branch;

    @Column(name = "availableQuantity", nullable = false)
    private Integer availableQuantity;

    public ItemBranch() {
    }

    public ItemBranch(Item item, Branch branch, Integer availableQuantity) {
        this.item = item;
        this.branch = branch;
        this.availableQuantity = availableQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
