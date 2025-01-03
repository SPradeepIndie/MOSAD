package org.rtss.mosad_backend.entity.customer;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.credit.Credit;

import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerContact> contacts;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Credit> credits;

    public Customer() {
    }

    public Customer(Long id, String name, List<CustomerContact> contacts, List<Credit> credits) {
        this.id = id;
        this.name = name;
        this.contacts = contacts;
        this.credits = credits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CustomerContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<CustomerContact> contacts) {
        this.contacts = contacts;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                ", credits=" + credits +
                '}';
    }
}
