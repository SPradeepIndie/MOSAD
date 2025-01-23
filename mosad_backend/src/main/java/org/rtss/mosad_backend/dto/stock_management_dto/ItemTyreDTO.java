package org.rtss.mosad_backend.dto.stock_management_dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemTyreDTO {
    private Long itemId;
    private String tyreSize;
    private String pattern;
    private String vehicleType;

}
