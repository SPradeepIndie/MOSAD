package org.rtss.mosad_backend.repository.tyre_rebuild;

import org.rtss.mosad_backend.entity.tyre_rebuild.RebuildTyre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RebuildTyreRepository extends JpaRepository<RebuildTyre, Long> {
    List<RebuildTyre> findByStatus(RebuildTyre.TyreStatus status);
}
