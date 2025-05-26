package org.rtss.mosad_backend.service.rebuild_tyre_management;

import org.rtss.mosad_backend.entity.rebuild_tyre.RebuildTyre;
import org.rtss.mosad_backend.repository.rebuild_tyre.RebuildTyreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RebuildTyreService {

    private final RebuildTyreRepository rebuildTyreRepository;

    public RebuildTyreService(RebuildTyreRepository rebuildTyreRepository) {
        this.rebuildTyreRepository = rebuildTyreRepository;
    }

    public RebuildTyre createRebuildTyre(RebuildTyre tyre) {
        return rebuildTyreRepository.save(tyre);
    }

    public List<RebuildTyre> getTyresByContactNumber(String contactNumber) {
        return rebuildTyreRepository.findAllByContactNumber(contactNumber);
    }

    public Optional<RebuildTyre> updateRebuildTyre(Long id, RebuildTyre updatedTyre) {
        return rebuildTyreRepository.findById(id).map(existingTyre -> {
            // Update all fields
            existingTyre.setCustomerId(updatedTyre.getCustomerId());
            existingTyre.setTyreNumber(updatedTyre.getTyreNumber());
            existingTyre.setTyreSize(updatedTyre.getTyreSize());
            existingTyre.setTyreBrand(updatedTyre.getTyreBrand());
            existingTyre.setCustomerName(updatedTyre.getCustomerName());
            existingTyre.setContactNumber(updatedTyre.getContactNumber());
            existingTyre.setDateReceived(updatedTyre.getDateReceived());
            existingTyre.setDateSentToCompany(updatedTyre.getDateSentToCompany());
            existingTyre.setSalesRepNumber(updatedTyre.getSalesRepNumber());
            existingTyre.setJobNumber(updatedTyre.getJobNumber());
            existingTyre.setDateReceivedFromCompany(updatedTyre.getDateReceivedFromCompany());
            existingTyre.setDateDeliveredToCustomer(updatedTyre.getDateDeliveredToCustomer());
            existingTyre.setBillNumber(updatedTyre.getBillNumber());
            existingTyre.setPrice(updatedTyre.getPrice());
            existingTyre.setStatus(updatedTyre.getStatus());
            return rebuildTyreRepository.save(existingTyre);
        });
    }

    public List<RebuildTyre> getAllRebuildTyres() {
        return rebuildTyreRepository.findAll();
    }

    public void deleteRebuildTyre(Long id) {
        rebuildTyreRepository.deleteById(id);
    }
}
