package org.rtss.mosad_backend.service.rebuild_tyre_management;

import org.rtss.mosad_backend.entity.tyre_rebuild.RebuildTyre;
import org.rtss.mosad_backend.repository.tyre_rebuild.RebuildTyreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RebuildTyreService {

    @Autowired
    private RebuildTyreRepository rebuildTyreRepository;

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
        return null;
    }
}
