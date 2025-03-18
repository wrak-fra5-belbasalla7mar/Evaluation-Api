package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.common.exception.KpiAlreadyAssignedException;
import com.spring.evalapi.common.exception.KpiNotFoundException;
import com.spring.evalapi.common.exception.ProfileNotFoundException;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.entity.Profile;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KPIRepository;

import com.spring.evalapi.repository.ProfileRepository;
import com.spring.evalapi.utils.CycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class KPIService {

    @Autowired
    private KPIRepository kpiRepository;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public KPI getKPIById(Long id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + id + " not found"));
    }

    public List<KPI> getKPIsByCycleId(Long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }

    public List<KPI> getKPIsByProfileId(Long profileId) {
        return kpiRepository.findByProfile_Id(profileId);
    }

    public KPI addKPI(Long profileId, KPI kpi) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile with ID " + profileId + " not found"));
        kpi.setProfile(profile);
        return kpiRepository.save(kpi);
    }

    public KPI updateKPI(KPI kpiDetails) {
        if (!kpiRepository.existsById(kpiDetails.getId())) {
            throw new KpiNotFoundException("KPI with ID " + kpiDetails.getId() + " not found");
        }

        KPI existingKPI = kpiRepository.findById(kpiDetails.getId())
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + kpiDetails.getId() + " not found"));

        if (kpiDetails.getName() != null && !kpiDetails.getName().isEmpty()) {
            existingKPI.setName(kpiDetails.getName());
        }

        if (kpiDetails.getProfile() != null && kpiDetails.getProfile().getId() != 0) {
            Profile newProfile = profileRepository.findById(kpiDetails.getProfile().getId())
                    .orElseThrow(() -> new ProfileNotFoundException("Profile with ID " + kpiDetails.getProfile().getId() + " not found"));
            existingKPI.setProfile(newProfile);
        }

        return kpiRepository.save(existingKPI);
    }

    public KPI assignKpiToCycle(Long kpiId, Long cycleId) {
        KPI kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + kpiId + " not found"));
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new CycleNotFoundException("Cycle with ID " + cycleId + " not found"));

        if (kpi.getCycle() != null && kpi.getCycle().getCycleState() == CycleState.OPEN) {
            throw new KpiAlreadyAssignedException("KPI is already assigned to an open cycle");
        }

        kpi.setCycle(cycle);
        return kpiRepository.save(kpi);
    }

    public String deleteKPI(Long id) {
        if (!kpiRepository.existsById(id)) {
            throw new KpiNotFoundException("KPI with ID " + id + " not found");
        }
        kpiRepository.deleteById(id);
        return "KPI was Deleted Successfully!";
    }
}