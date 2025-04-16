package com.spring.evalapi.service;

import com.spring.evalapi.dto.UserDto;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.entity.KpiRole;
import com.spring.evalapi.entity.Role;
import com.spring.evalapi.exception.AccessDeniedException;
import com.spring.evalapi.exception.CycleStateException;
import com.spring.evalapi.exception.FieldIsRequiredException;
import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KpiRepository;
import com.spring.evalapi.repository.KpiRoleRepository;
import com.spring.evalapi.repository.RoleRepository;
import com.spring.evalapi.utils.CycleState;
import com.spring.evalapi.utils.Level;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class KPIService {

    private final KpiRepository kpiRepository;
    private final CycleRepository cycleRepository;
    private final RoleRepository roleRepository;
    private final KpiRoleRepository kpiRoleRepository;
    private final UserService userService;

    public Kpi getKpiById(Long id) {
        return kpiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("KPI with ID " + id + " not found"));
    }

    public List<Kpi> getKPIsByCycleId(Long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }

    public double getWeightByKpiIdAndRoleNameAndRoleLevel(Long kpiId, String roleName, Level roleLevel) {
        return kpiRoleRepository.findByKpi_IdAndRole_NameAndRole_Level(kpiId, roleName, roleLevel)
                .map(KpiRole::getWeight)
                .orElse(1.0);
    }

    public List<Kpi> getAllKpis() {
        return kpiRepository.findAll();
    }

    public Kpi addKpi(Kpi kpi, Long userId) {
        UserDto userDto = userService.getUserById(userId);
        if (!"COMPANY_MANAGER".equals(userDto.getRole())) {
            throw new AccessDeniedException("Only company managers can add a KPI");
        }

        if (kpi.getName() == null || kpi.getName().isEmpty()) {
            throw new FieldIsRequiredException("KPI name is required");
        }

        Cycle openCycle = cycleRepository.findByState(CycleState.OPEN);
        if (openCycle == null) {
            throw new CycleStateException("No cycle in OPEN state found. KPIs can only be added during the OPEN state.");
        }

        kpi.setCycle(openCycle);
        return kpiRepository.save(kpi);
    }

    public void assignKpiToRole(Long kpiId, String roleName, Level roleLevel, Double weight) {
        if (weight == null) {
            throw new FieldIsRequiredException("Weight is required");
        }

        Kpi kpi = getKpiById(kpiId);
        Role role = roleRepository.findByNameAndLevel(roleName, roleLevel)
                .orElseThrow(() -> new NotFoundException("Role with name " + roleName + " and level " + roleLevel + " not found"));

        KpiRole kpiRole = kpiRoleRepository.findByKpi_IdAndRole_NameAndRole_Level(kpiId, roleName, roleLevel)
                .orElse(new KpiRole(kpi, role, weight));

        kpiRole.setWeight(weight);
        kpiRoleRepository.save(kpiRole);
    }

    public Kpi updateKPI(Kpi kpiDetails) {
        Kpi existingKPI = getKpiById(kpiDetails.getId());

        if (kpiDetails.getName() != null && !kpiDetails.getName().isEmpty()) {
            existingKPI.setName(kpiDetails.getName());
        }

        return kpiRepository.save(existingKPI);
    }

    public Kpi assignKpiToCycle(Long kpiId, Long cycleId) {
        Kpi kpi = getKpiById(kpiId);
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new NotFoundException("Cycle with ID " + cycleId + " not found"));

        kpi.setCycle(cycle);
        return kpiRepository.save(kpi);
    }

    public void deleteKPI(Long id) {
        Kpi kpi = getKpiById(id);
        kpiRepository.delete(kpi);
    }
}
