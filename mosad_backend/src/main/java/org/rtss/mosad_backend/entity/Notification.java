package org.rtss.mosad_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String msg;
    private String type;

    // Constructors
    public Notification() {}

    public Notification(String msg, String type) {
        this.msg = msg;
        this.type = type;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
