package org.rtss.mosad_backend.service.services_management;


import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto.services_dtos.ServicesDTO;
import org.rtss.mosad_backend.dto_mapper.services_dto_mapper.ServicesDTOMapper;
import org.rtss.mosad_backend.entity.customer.Customer;
import org.rtss.mosad_backend.entity.services_section.ServicesSection;
import org.rtss.mosad_backend.repository.services_repository.ServicesSectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class ServicesSectionService {
    private final ServicesSectionRepository servicessectionrepository;
    private final ServicesDTOMapper servicesDTOMapper;

    public ServicesSectionService(ServicesSectionRepository servicessectionrepository, ServicesDTOMapper servicesDTOMapper) {
        this.servicessectionrepository = servicessectionrepository;
        this.servicesDTOMapper = servicesDTOMapper;
    }

    @Transactional
    public ServicesDTO saveServices(ServicesDTO servicesDTO) {
        ServicesSection serviceEntity = servicesDTOMapper.toEntity(servicesDTO);

        // Save entity to the repository
        ServicesSection savedService = servicessectionrepository.save(serviceEntity);

        // Convert entity back to DTO
        return servicesDTOMapper.toDTO(savedService);
    }


    public List<ServicesDTO> getAllServices() {
        List<ServicesSection> ServicesSectionList= servicessectionrepository.findAll();
        if(ServicesSectionList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No customers found");
        }
        return ServicesSectionList.stream().map(servicesDTOMapper::toDTO).toList();
    }

}
