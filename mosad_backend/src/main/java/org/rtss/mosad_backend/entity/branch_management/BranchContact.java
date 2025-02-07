package org.rtss.mosad_backend.entity.branch_management;

import jakarta.persistence.*;

@Entity
@Table(name = "branchContact")
public class BranchContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactId", unique = true, nullable = false)
    private Long contactId;
    @Column(name = "contactNumber",nullable = false,unique = false)
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "branchId",nullable = false)
    private Branch branch;

    public BranchContact() {
    }

    public BranchContact(Long contactId, String contactNumber, Branch branch) {
        this.contactId = contactId;
        this.contactNumber = contactNumber;
        this.branch = branch;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
