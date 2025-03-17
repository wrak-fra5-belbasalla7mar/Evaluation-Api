package com.spring.evalapi.repository;

import com.spring.evalapi.entity.KPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface KpiRepository extends JpaRepository<KPI,Long> {
    List<KPI> findByCycle_Id(Long cycleId);
    List<KPI> findByKpiProfile_Id(Long kpiProfileId);

}
