package org.rtss.mosad_backend.service.rebuild_tyre_management;

import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre;
import org.rtss.mosad_backend.repository.rebuild_tyre.RebuildTyreRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RebuildTyreService {
    private final RebuildTyreRepository rebuildTyreRepository;

    public RebuildTyreService(RebuildTyreRepository rebuildTyreRepository) {
        this.rebuildTyreRepository = rebuildTyreRepository;
    }

    public RebuildTyre saveRebuildTire(RebuildTyre rebuildTyre) {
        return rebuildTyreRepository.save(rebuildTyre);
    }

    public List<RebuildTyre> getAllRebuildTyres() {
        return rebuildTyreRepository.findAll();
    }

    public Optional<RebuildTyre> getRebuildTyreById(Long id) {
        return rebuildTyreRepository.findById(id);
    }

    public List<RebuildTyre> getRebuildTiresByStatus(RebuildTyre.TyreStatus status) {
        return rebuildTyreRepository.findByStatus(status);
    }

    public void deleteRebuildTyre(Long id) {
        rebuildTyreRepository.deleteById(id);
    }

    public RebuildTyre saveRebuildTyre(RebuildTyre rebuildTyre) {
        return rebuildTyre;
    }

    public List<RebuildTyre> getRebuildTyresByStatus(RebuildTyre.TyreStatus tyreStatus) {
        return new ArrayList<>(rebuildTyreRepository.findByStatus(tyreStatus));
    }
}
