package org.rtss.mosad_backend.entity.rebuild_tyre;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class RebuildTyre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private Long customerId;
    private Integer tyreNumber;
    private String tyreSize;
    private String tyreBrand;
    private String customerName;
    private String contactNumber;
    private LocalDate dateReceived;
    private LocalDate dateSentToCompany;
    private String salesRepNumber;
    private String jobNumber;
    private LocalDate dateReceivedFromCompany;
    private LocalDate dateDeliveredToCustomer;
    private String billNumber;
    private Double price;

    @Enumerated(EnumType.STRING)
    private TyreStatus status;

    public enum TyreStatus {
        IN_HOLD, SENT_TO_REBUILD, DONE
    }

    // Constructors
    public RebuildTyre() {}

    public RebuildTyre(Long customerId, Integer tyreNumber, String tyreSize, String tyreBrand, 
                       String customerName, String contactNumber, LocalDate dateReceived, 
                       LocalDate dateSentToCompany, String salesRepNumber, String jobNumber, 
                       LocalDate dateReceivedFromCompany, LocalDate dateDeliveredToCustomer, 
                       String billNumber, Double price, TyreStatus status) {
        this.customerId = customerId;
        this.tyreNumber = tyreNumber;
        this.tyreSize = tyreSize;
        this.tyreBrand = tyreBrand;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.dateReceived = dateReceived;
        this.dateSentToCompany = dateSentToCompany;
        this.salesRepNumber = salesRepNumber;
        this.jobNumber = jobNumber;
        this.dateReceivedFromCompany = dateReceivedFromCompany;
        this.dateDeliveredToCustomer = dateDeliveredToCustomer;
        this.billNumber = billNumber;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getTyreNumber() {
        return tyreNumber;
    }

    public void setTyreNumber(Integer tyreNumber) {
        this.tyreNumber = tyreNumber;
    }

    public String getTyreSize() {
        return tyreSize;
    }

    public void setTyreSize(String tyreSize) {
        this.tyreSize = tyreSize;
    }

    public String getTyreBrand() {
        return tyreBrand;
    }

    public void setTyreBrand(String tyreBrand) {
        this.tyreBrand = tyreBrand;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

    public LocalDate getDateSentToCompany() {
        return dateSentToCompany;
    }

    public void setDateSentToCompany(LocalDate dateSentToCompany) {
        this.dateSentToCompany = dateSentToCompany;
    }

    public String getSalesRepNumber() {
        return salesRepNumber;
    }

    public void setSalesRepNumber(String salesRepNumber) {
        this.salesRepNumber = salesRepNumber;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public LocalDate getDateReceivedFromCompany() {
        return dateReceivedFromCompany;
    }

    public void setDateReceivedFromCompany(LocalDate dateReceivedFromCompany) {
        this.dateReceivedFromCompany = dateReceivedFromCompany;
    }

    public LocalDate getDateDeliveredToCustomer() {
        return dateDeliveredToCustomer;
    }

    public void setDateDeliveredToCustomer(LocalDate dateDeliveredToCustomer) {
        this.dateDeliveredToCustomer = dateDeliveredToCustomer;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TyreStatus getStatus() {
        return status;
    }

    public void setStatus(TyreStatus status) {
        this.status = status;
    }
}
