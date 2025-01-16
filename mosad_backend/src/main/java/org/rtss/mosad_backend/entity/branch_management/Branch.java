package org.rtss.mosad_backend.entity.branch_management;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.stock_management_entity.Item;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;

import java.util.Set;

@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branchId", unique = true, nullable = false)
    private Long branchId;
    @Column(name = "branchName", length = 30)
    private String branchName;
    @Column(name = "buildingNumber", length = 30, nullable = false)
    private String buildingNumber;
    @Column(name = "streetName", length = 30, nullable = false)
    private String streetName;
    @Column(name = "city",length = 30,nullable = false)
    private String city;

    @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BranchContact> branchContacts;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<ItemBranch> itemBranches;




    public Branch() {
    }

    public Branch(Long branchId, String branchName, String buildingNumber, String streetName, String city, Set<BranchContact> branchContacts,Set<ItemBranch> itemBranches) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.buildingNumber = buildingNumber;
        this.streetName = streetName;
        this.city = city;
        this.branchContacts = branchContacts;
        this.itemBranches = itemBranches;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<BranchContact> getBranchContacts() {
        return branchContacts;
    }

    public void setBranchContacts(Set<BranchContact> branchContacts) {
        this.branchContacts = branchContacts;
    }

    public Set<ItemBranch> getItemBranches() {
        return itemBranches;
    }

    public void setItemBranches(Set<ItemBranch> itemBranches) {
        this.itemBranches = itemBranches;
    }
}
