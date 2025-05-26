package org.rtss.mosad_backend.controller;

import org.rtss.mosad_backend.dto.HomeCalDTO;
import org.rtss.mosad_backend.dto.HomeStatsDTO;
import org.rtss.mosad_backend.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/homestats")
public class HomeStatsController {
    private final HomeService homeService;

    public HomeStatsController(HomeService homeService) {
        this.homeService = homeService;
    }


    @GetMapping("/cards")
    public HomeStatsDTO getHomeStats() {
        return homeService.getStats();
    }

    @GetMapping("/cal")
    public ResponseEntity<ArrayList<HomeCalDTO>> getHomeCal() {
        return ResponseEntity.ok(homeService.getDueDAtes());
    }
}
