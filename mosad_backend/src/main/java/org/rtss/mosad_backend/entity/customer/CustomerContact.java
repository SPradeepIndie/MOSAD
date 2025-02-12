package org.rtss.mosad_backend.entity.customer;

import jakarta.persistence.*;

@Entity
public class CustomerContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerContactId;

    private String contactNumber;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public CustomerContact(Long customerContactId, Customer customer, String contactNumber) {
        this.customerContactId = customerContactId;
        this.customer = customer;
        this.contactNumber = contactNumber;
    }
    public CustomerContact(String contactNumber) {
        this.contactNumber = contactNumber;
    }



    public CustomerContact() {

    }

    public Long getCustomerContactId() {
        return customerContactId;
    }

    public void setCustomerContactId(Long customerContactId) {
        this.customerContactId = customerContactId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
