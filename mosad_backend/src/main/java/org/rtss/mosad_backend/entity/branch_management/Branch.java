package org.rtss.mosad_backend.entity.branch_management;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.stock_management_entity.ItemBranch;
import org.rtss.mosad_backend.entity.user_management.Users;

import java.util.Set;

@Entity
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branchId", unique = true, nullable = false)
    private Long branchId;
    @Column(name = "branchName", length = 30,unique = true, nullable = false)
    private String branchName;
    @Column(name = "addressNumber", length = 10, nullable = false)
    private String addressNumber;
    @Column(name = "streetName", length = 30, nullable = false)
    private String streetName;
    @Column(name = "city",length = 30,nullable = false)
    private String city;

    @OneToMany(mappedBy = "branch",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BranchContact> branchContacts;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    private Set<ItemBranch> itemBranches;

    @OneToMany(mappedBy = "branch")
    private Set<Users> users;


    public Branch() {
    }

    public Branch(Long branchId, String branchName, String addressNumber, String streetName, String city, Set<BranchContact> branchContacts, Set<ItemBranch> itemBranches, Set<Users> users) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.addressNumber = addressNumber;
        this.streetName = streetName;
        this.city = city;
        this.branchContacts = branchContacts;
        this.itemBranches = itemBranches;
        this.users = users;
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

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
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

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}
