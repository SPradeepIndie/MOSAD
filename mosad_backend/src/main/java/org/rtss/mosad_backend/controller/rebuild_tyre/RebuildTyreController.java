package org.rtss.mosad_backend.controller.rebuild_tyre;

import org.rtss.mosad_backend.dto.rebuild_tyre_dtos.RebuildTyreDto;
import org.rtss.mosad_backend.dto_mapper.rebuild_tyre_dto_mapper.RebuildTyreMapper;
import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre;
import org.rtss.mosad_backend.service.rebuild_tyre_management.RebuildTyreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/rebuild-tyres")
public class RebuildTyreController {

    private final RebuildTyreService rebuildTyreService;

    public RebuildTyreController(RebuildTyreService rebuildTyreService) {
        this.rebuildTyreService = rebuildTyreService;
    }

    @GetMapping
    public ResponseEntity<String> getAllTyres() {
        return ResponseEntity.ok("Fetched");
    }

    // Create a new tyre entry.
    @PostMapping
    public ResponseEntity<RebuildTyreDto> createRebuildTyre(@Valid @RequestBody RebuildTyreDto rebuildTyreDto) {
        RebuildTyre tyre = RebuildTyreMapper.toEntity(rebuildTyreDto);
        RebuildTyre savedTyre = rebuildTyreService.createRebuildTyre(tyre);
        RebuildTyreDto responseDto = RebuildTyreMapper.toDto(savedTyre);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<RebuildTyreDto>> getAllRebuildTyres() {
        List<RebuildTyre> tyres = rebuildTyreService.getAllRebuildTyres();
        List<RebuildTyreDto> dtoList = tyres.stream()
                .map(RebuildTyreMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Find tyres by the customer's contact number.
    @GetMapping("/contact/{contactNumber}")
    public ResponseEntity<List<RebuildTyreDto>> getTyresByContactNumber(@PathVariable String contactNumber) {
        List<RebuildTyre> tyres = rebuildTyreService.getTyresByContactNumber(contactNumber);
        List<RebuildTyreDto> dtoList = tyres.stream()
                .map(RebuildTyreMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    // Update a tyre entry by its id.
    @PutMapping("/{id}")
    public ResponseEntity<RebuildTyreDto> updateRebuildTyre(@PathVariable Long id, @Valid @RequestBody RebuildTyreDto rebuildTyreDto) {
        RebuildTyre tyreToUpdate = RebuildTyreMapper.toEntity(rebuildTyreDto);
        return rebuildTyreService.updateRebuildTyre(id, tyreToUpdate)
                .map(updatedTyre -> {
                    RebuildTyreDto updatedDto = RebuildTyreMapper.toDto(updatedTyre);
                    return ResponseEntity.ok(updatedDto);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Delete a tyre entry by its id.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRebuildTyre(@PathVariable Long id) {
        rebuildTyreService.deleteRebuildTyre(id);
        return ResponseEntity.noContent().build();
    }
}
