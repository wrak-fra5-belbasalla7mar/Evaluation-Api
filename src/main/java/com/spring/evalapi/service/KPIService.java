package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.CycleNotFoundException;
import com.spring.evalapi.common.exception.KpiNotFoundException;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.entity.KpiRole;
import com.spring.evalapi.entity.Role;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KpiRepository;
import com.spring.evalapi.repository.KpiRoleRepository;
import com.spring.evalapi.repository.RoleRepository;
import com.spring.evalapi.utils.CycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KPIService {

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private KpiRoleRepository kpiRoleRepository;

    public Kpi getKpiById(Long id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + id + " not found"));
    }

    public List<Kpi> getKPIsByCycleId(Long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }

    public Kpi addKpi(Kpi kpi) {
        if (kpi.getName() == null || kpi.getName().isEmpty()) {
            throw new IllegalArgumentException("KPI name is required");
        }

        Cycle passedCycle = cycleRepository.findByState(CycleState.PASSED);
        if (passedCycle == null) {
            throw new IllegalStateException("No cycle in PASSED state found. KPIs can only be added during the PASSED state.");
        }

        kpi.setCycle(passedCycle);
        return kpiRepository.save(kpi);
    }

    public void assignKpiToRole(Long kpiId, String roleName, String roleLevel, Double weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Weight is required");
        }

        // Find the KPI
        Kpi kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + kpiId + " not found"));

        // Find the Role
        Role role = roleRepository.findByNameAndLevel(roleName, roleLevel)
                .orElseThrow(() -> new IllegalArgumentException("Role with name " + roleName + " and level " + roleLevel + " not found"));

        KpiRole existingKpiRole = kpiRoleRepository.findByKpi_IdAndRole_NameAndRole_Level(kpiId, roleName, roleLevel)
                .orElse(null);

        KpiRole kpiRole;
        if (existingKpiRole != null) {
            // Update the existing weight
            kpiRole = existingKpiRole;
            kpiRole.setWeight(weight);
        } else {
            // Link the KPI and Role with the weight
            kpiRole = new KpiRole(kpi, role, weight);
        }
        kpiRoleRepository.save(kpiRole);
    }

    public Kpi updateKPI(Kpi kpiDetails) {
        Kpi existingKPI = kpiRepository.findById(kpiDetails.getId())
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + kpiDetails.getId() + " not found"));

        if (kpiDetails.getName() != null && !kpiDetails.getName().isEmpty()) {
            existingKPI.setName(kpiDetails.getName());
        }

        return kpiRepository.save(existingKPI);
    }

    public Kpi assignKpiToCycle(Long kpiId, Long cycleId) {
        Kpi kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + kpiId + " not found"));
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new CycleNotFoundException("Cycle with ID " + cycleId + " not found"));

        kpi.setCycle(cycle);
        return kpiRepository.save(kpi);
    }

    public void deleteKPI(Long id) {
        Kpi kpi = kpiRepository.findById(id)
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + id + " not found"));
        kpiRepository.delete(kpi);
    }
}