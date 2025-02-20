package org.rtss.mosad_backend.entity.user_management;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UsersOTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;

    @Column(nullable = false)
    private String otpToken;

    @Column(nullable = false)
    private Date otpExpiryDate;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    public UsersOTP(Long otpId, String otpToken, Date otpExpiryDate, Users user) {
        this.otpId = otpId;
        this.otpToken = otpToken;
        this.otpExpiryDate = otpExpiryDate;
        this.user = user;
    }

    public UsersOTP() {
    }

    public Long getOtpId() {
        return otpId;
    }

    public void setOtpId(Long otpId) {
        this.otpId = otpId;
    }

    public String getOtpToken() {
        return otpToken;
    }

    public void setOtpToken(String otpToken) {
        this.otpToken = otpToken;
    }

    public Date getOtpExpiryDate() {
        return otpExpiryDate;
    }

    public void setOtpExpiryDate(Date otpExpiryDate) {
        this.otpExpiryDate = otpExpiryDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
