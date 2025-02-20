package org.rtss.mosad_backend.dto_mapper.rebuild_tyre_dto_mapper;


import org.rtss.mosad_backend.dto.rebuild_tyre_dtos.RebuildTyreDto;
import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre;

public class RebuildTyreMapper {

    public static RebuildTyre toEntity(RebuildTyreDto dto) {
        RebuildTyre tyre = new RebuildTyre();
        tyre.setCustomerId(dto.getCustomerId());
        tyre.setTyreNumber(dto.getTyreNumber());
        tyre.setTyreSize(dto.getTyreSize());
        tyre.setTyreBrand(dto.getTyreBrand());
        tyre.setCustomerName(dto.getCustomerName());
        tyre.setContactNumber(dto.getContactNumber());
        tyre.setDateReceived(dto.getDateReceived());
        tyre.setDateSentToCompany(dto.getDateSentToCompany());
        tyre.setSalesRepNumber(dto.getSalesRepNumber());
        tyre.setDateReceivedFromCompany(dto.getDateReceivedFromCompany());
        tyre.setDateDeliveredToCustomer(dto.getDateDeliveredToCustomer());
        tyre.setBillNumber(dto.getBillNumber());
        tyre.setPrice(dto.getPrice());
        tyre.setStatus(dto.getStatus());
        return tyre;
    }

    public static RebuildTyreDto toDto(RebuildTyre tyre) {
        RebuildTyreDto dto = new RebuildTyreDto();
        dto.setCustomerId(tyre.getCustomerId());
        dto.setTyreNumber(tyre.getTyreNumber());
        dto.setTyreSize(tyre.getTyreSize());
        dto.setTyreBrand(tyre.getTyreBrand());
        dto.setCustomerName(tyre.getCustomerName());
        dto.setContactNumber(tyre.getContactNumber());
        dto.setDateReceived(tyre.getDateReceived());
        dto.setDateSentToCompany(tyre.getDateSentToCompany());
        dto.setSalesRepNumber(tyre.getSalesRepNumber());
        dto.setJobNumber(tyre.getJobNumber());
        dto.setDateReceivedFromCompany(tyre.getDateReceivedFromCompany());
        dto.setDateDeliveredToCustomer(tyre.getDateDeliveredToCustomer());
        dto.setBillNumber(tyre.getBillNumber());
        dto.setPrice(tyre.getPrice());
        dto.setStatus(tyre.getStatus());
        return dto;
    }
}
