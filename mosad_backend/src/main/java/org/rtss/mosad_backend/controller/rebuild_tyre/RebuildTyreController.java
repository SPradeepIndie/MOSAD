package org.rtss.mosad_backend.controller.rebuild_tyre;

import org.rtss.mosad_backend.entity.tyre_rebuild.RebuildTyre;
import org.rtss.mosad_backend.service.rebuild_tyre_management.RebuildTyreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rebuild-tyres")
public class RebuildTyreController {

    @Autowired
    private RebuildTyreService rebuildTyreService;

    @PostMapping
    public ResponseEntity<RebuildTyre> createRebuildTyre(@RequestBody RebuildTyre rebuildTyre) {
        RebuildTyre savedTyre = rebuildTyreService.saveRebuildTyre(rebuildTyre);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTyre);
    }

    @GetMapping
    public ResponseEntity<List<RebuildTyre>> getAllRebuildTyres() {
        return ResponseEntity.ok(rebuildTyreService.getAllRebuildTyres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RebuildTyre> getRebuildTyreById(@PathVariable Long id) {
        return rebuildTyreService.getRebuildTyreById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RebuildTyre>> getRebuildTiresByStatus(@PathVariable String status) {
        return ResponseEntity.ok(
                rebuildTyreService.getRebuildTyresByStatus(RebuildTyre.TyreStatus.valueOf(status.toUpperCase())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRebuildTyre(@PathVariable Long id) {
        rebuildTyreService.deleteRebuildTyre(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
