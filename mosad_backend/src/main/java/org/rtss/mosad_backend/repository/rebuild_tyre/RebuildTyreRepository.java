package org.rtss.mosad_backend.repository.rebuild_tyre;

import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RebuildTyreRepository extends JpaRepository<RebuildTyre, Long> {
    List<RebuildTyre> findAllByContactNumber(String contactNumber);
}
