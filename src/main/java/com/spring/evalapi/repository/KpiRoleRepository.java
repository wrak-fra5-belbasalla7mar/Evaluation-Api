package com.spring.evalapi.repository;

import com.spring.evalapi.entity.KpiRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KpiRoleRepository extends JpaRepository<KpiRole, Long> {
    Optional<KpiRole> findByKpi_IdAndRole_NameAndRole_Level(Long kpiId, String roleName, String roleLevel);

    List<KpiRole> findByRole_NameAndRole_Level(String roleName, String roleLevel);
}