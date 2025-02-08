package org.rtss.mosad_backend.entity.rebuild_tyre;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
