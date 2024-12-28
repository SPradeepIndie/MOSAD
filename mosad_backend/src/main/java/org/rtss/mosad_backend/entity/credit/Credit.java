package org.rtss.mosad_backend.entity.credit;

import jakarta.persistence.*;
import org.rtss.mosad_backend.entity.customer.Customer;

import java.util.Date;

@Entity
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditId;

    private double balance;

    private double remainingBalance;

    private Date dueDate;

    @OneToOne
    private Customer customer;

}
