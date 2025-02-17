package org.rtss.mosad_backend.dto.rebuild_tyre_dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre.TyreStatus;

public class RebuildTyreDto {

    private Long itemId;

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

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

    // Remove the long constructor to satisfy SonarCloud rule S107.
    // Instead, use the Builder pattern to construct instances.

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
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
        private TyreStatus status;

        public Builder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder tyreNumber(Integer tyreNumber) {
            this.tyreNumber = tyreNumber;
            return this;
        }

        public Builder tyreSize(String tyreSize) {
            this.tyreSize = tyreSize;
            return this;
        }

        public Builder tyreBrand(String tyreBrand) {
            this.tyreBrand = tyreBrand;
            return this;
        }

        public Builder customerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder contactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            return this;
        }

        public Builder dateReceived(LocalDate dateReceived) {
            this.dateReceived = dateReceived;
            return this;
        }

        public Builder dateSentToCompany(LocalDate dateSentToCompany) {
            this.dateSentToCompany = dateSentToCompany;
            return this;
        }

        public Builder salesRepNumber(String salesRepNumber) {
            this.salesRepNumber = salesRepNumber;
            return this;
        }

        public Builder jobNumber(String jobNumber) {
            this.jobNumber = jobNumber;
            return this;
        }

        public Builder dateReceivedFromCompany(LocalDate dateReceivedFromCompany) {
            this.dateReceivedFromCompany = dateReceivedFromCompany;
            return this;
        }

        public Builder dateDeliveredToCustomer(LocalDate dateDeliveredToCustomer) {
            this.dateDeliveredToCustomer = dateDeliveredToCustomer;
            return this;
        }

        public Builder billNumber(String billNumber) {
            this.billNumber = billNumber;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder status(TyreStatus status) {
            this.status = status;
            return this;
        }

        public RebuildTyreDto build() {
            RebuildTyreDto dto = new RebuildTyreDto();
            dto.setCustomerId(this.customerId);
            dto.setTyreNumber(this.tyreNumber);
            dto.setTyreSize(this.tyreSize);
            dto.setTyreBrand(this.tyreBrand);
            dto.setCustomerName(this.customerName);
            dto.setContactNumber(this.contactNumber);
            dto.setDateReceived(this.dateReceived);
            dto.setDateSentToCompany(this.dateSentToCompany);
            dto.setSalesRepNumber(this.salesRepNumber);
            dto.setJobNumber(this.jobNumber);
            dto.setDateReceivedFromCompany(this.dateReceivedFromCompany);
            dto.setDateDeliveredToCustomer(this.dateDeliveredToCustomer);
            dto.setBillNumber(this.billNumber);
            dto.setPrice(this.price);
            dto.setStatus(this.status);
            return dto;
        }
    }

    // Getters and Setters
    
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
