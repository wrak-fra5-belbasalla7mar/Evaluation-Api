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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class KPIService {

    private final KpiRepository kpiRepository;
    private final CycleRepository cycleRepository;
    private final RoleRepository roleRepository;
    private final KpiRoleRepository kpiRoleRepository;
    private final UserService userService;

    public KPIService(KpiRepository kpiRepository, CycleRepository cycleRepository, RoleRepository roleRepository, KpiRoleRepository kpiRoleRepository, UserService userService) {
        this.kpiRepository = kpiRepository;
        this.cycleRepository = cycleRepository;
        this.roleRepository = roleRepository;
        this.kpiRoleRepository = kpiRoleRepository;
        this.userService = userService;
    }

    public Kpi getKpiById(Long id) {
        return kpiRepository.findById(id).orElseThrow(() -> new NotFoundException("KPI with ID " + id + " not found"));
    }

    public List<Kpi> getKPIsByCycleId(Long cycleId) {
        return kpiRepository.findByCycle_Id(cycleId);
    }


    @Transactional
    public Kpi addKpi(Kpi kpi,Long id) {
        UserDto userDto = userService.getUserById(id);
        if (!userDto.getRole().equals("CompanyManager")) {
            throw new AccessDeniedException("Only company managers can add a kpi");
        }
        if (kpi.getName() == null || kpi.getName().isEmpty()) {
            throw new FieldIsRequiredException("KPI name is required");
        }
        Cycle passedCycle = cycleRepository.findByState(CycleState.PASSED);
        if (passedCycle == null) {
            throw new CycleStateException("No cycle in PASSED state found. KPIs can only be added during the PASSED state.");
        }
        kpi.setCycle(passedCycle);
        return kpiRepository.save(kpi);
    }


    @Transactional
    public void assignKpiToRole(Long kpiId, String roleName, String roleLevel, Double weight) {
        if (weight == null) {
            throw new FieldIsRequiredException("Weight is required");
        }

        Kpi kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new NotFoundException("KPI with ID " + kpiId + " not found"));

        Role role = roleRepository.findByNameAndLevel(roleName, roleLevel)
                .orElseThrow(() -> new NotFoundException("Role with name " + roleName + " and level " + roleLevel + " not found"));

        KpiRole existingKpiRole = kpiRoleRepository.findByKpi_IdAndRole_NameAndRole_Level(kpiId, roleName, roleLevel)
                .orElse(null);

        KpiRole kpiRole;
        if (existingKpiRole != null) {
            kpiRole = existingKpiRole;
            kpiRole.setWeight(weight);
        } else {
            kpiRole = new KpiRole(kpi, role, weight);
        }
        kpiRoleRepository.save(kpiRole);
    }

    public Kpi updateKPI(Kpi kpiDetails) {
        Kpi existingKPI = kpiRepository.findById(kpiDetails.getId())
                .orElseThrow(() -> new NotFoundException("KPI with ID " + kpiDetails.getId() + " not found"));

        if (kpiDetails.getName() != null && !kpiDetails.getName().isEmpty()) {
            existingKPI.setName(kpiDetails.getName());
        }

        return kpiRepository.save(existingKPI);
    }


    @Transactional
    public Kpi assignKpiToCycle(Long kpiId, Long cycleId) {
        Kpi kpi = kpiRepository.findById(kpiId).orElseThrow(() -> new NotFoundException("KPI with ID " + kpiId + " not found"));
        Cycle cycle = cycleRepository.findById(cycleId).orElseThrow(() -> new NotFoundException("Cycle with ID " + cycleId + " not found"));
        kpi.setCycle(cycle);
        return kpiRepository.save(kpi);
    }


    public void deleteKPI(Long id) {
        Kpi kpi = kpiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("KPI with ID " + id + " not found"));
        kpiRepository.delete(kpi);
    }
}