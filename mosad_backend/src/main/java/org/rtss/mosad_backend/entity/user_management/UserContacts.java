package org.rtss.mosad_backend.entity.user_management;

import jakarta.persistence.*;

@Entity
@Table(name = "userContact")
public class UserContacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactId", unique = true, nullable = false)
    private Long id;

    @Column(name = "contactNum")
    private String contactNum;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;


    public UserContacts(Long id, String contactNum, Users user) {
        this.id = id;
        this.contactNum = contactNum;
        this.user = user;
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
