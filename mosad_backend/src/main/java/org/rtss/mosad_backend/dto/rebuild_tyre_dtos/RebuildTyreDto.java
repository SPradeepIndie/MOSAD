package org.rtss.mosad_backend.dto.rebuild_tyre_dtos;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre.TyreStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RebuildTyreDto {

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
}
