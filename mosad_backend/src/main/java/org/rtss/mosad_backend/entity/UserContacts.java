package org.rtss.mosad_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "userContact")
public class UserContacts {

    @ManyToOne
    @JoinColumn(name = "useId", nullable = false)
    private Users user;

    @Id
    @Column(name = "contactNum")
    private String contactNum;

    public UserContacts(Users user, String contactNum) {
        this.user = user;
        this.contactNum = contactNum;
    }

    public UserContacts() {
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    @Override
    public String toString() {
        return "UserContacts{" +
                "user=" + user +
                ", contactNum='" + contactNum + '\'' +
                '}';
    }
}
