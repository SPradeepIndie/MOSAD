package org.rtss.mosad_backend.controller.services_management;

import org.rtss.mosad_backend.dto.customer_dtos.CustomerDTO;
import org.rtss.mosad_backend.dto.customer_dtos.CustomerDetailsDTO;
import org.rtss.mosad_backend.dto.services_dtos.ServicesDTO;
import org.rtss.mosad_backend.service.services_management.ServicesSectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")

public class ServicesSectionController {

    private final ServicesSectionService servicesSectionService;

    public ServicesSectionController(ServicesSectionService servicesSectionService) {
        this.servicesSectionService = servicesSectionService;
    }

    @PostMapping()
    public ResponseEntity<ServicesDTO> addServices(@RequestBody ServicesDTO servicesDTO) {
        ServicesDTO savedServicesDTO = servicesSectionService.saveServices(servicesDTO); // ✅ Fix: Different variable name
        return ResponseEntity.ok(savedServicesDTO);
    }

    @GetMapping()
    public ResponseEntity<List<ServicesDTO>> getServices() {
        List<ServicesDTO> servicesList = servicesSectionService.getAllServices();
        return ResponseEntity.ok(servicesList);
    }


}
