package org.rtss.mosad_backend.controller.reports;
import org.rtss.mosad_backend.dto.ItemDataDTO;
import org.rtss.mosad_backend.service.ReportServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportServices reportServices;

    public ReportController(ReportServices reportServices) {
        this.reportServices = reportServices;
    }

    @GetMapping
    public ResponseEntity<List<ItemDataDTO>> getReport() {
        return ResponseEntity.ok(reportServices.getCategoryCounts());
    }
}
