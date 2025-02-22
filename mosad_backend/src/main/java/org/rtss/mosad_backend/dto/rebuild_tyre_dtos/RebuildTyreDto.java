package org.rtss.mosad_backend.dto.rebuild_tyre_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre.TyreStatus;

public class RebuildTyreDto {

    private Long itemId;


    @NotNull(message = "Tyre number cannot be null")
    private Integer tyreNumber;

    @NotBlank(message = "Tyre size cannot be blank")
    private String tyreSize;

    @NotBlank(message = "Tyre brand cannot be blank")
    private String tyreBrand;

    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;

    @NotBlank(message = "Contact number cannot be blank")
    private String contactNumber;

    @NotNull(message = "Date received cannot be null")
    private LocalDate dateReceived;

    private LocalDate dateSentToCompany;
    private String salesRepNumber;
    private String jobNumber;
    private LocalDate dateReceivedFromCompany;
    private LocalDate dateDeliveredToCustomer;
    private String billNumber;
    private Double price;

    @NotNull(message = "Tyre status cannot be null")
    private TyreStatus status;

    // No-argument constructor
    public RebuildTyreDto() {}

    public RebuildTyreDto(Long itemId, Integer tyreNumber, String tyreSize, String tyreBrand, String customerName, String contactNumber, LocalDate dateReceived, LocalDate dateSentToCompany, String salesRepNumber, String jobNumber, LocalDate dateReceivedFromCompany, LocalDate dateDeliveredToCustomer, String billNumber, Double price, TyreStatus status) {
        this.itemId = itemId;
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
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
