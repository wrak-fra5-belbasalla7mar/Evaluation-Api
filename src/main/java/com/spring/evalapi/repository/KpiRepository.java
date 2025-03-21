package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface KpiRepository extends JpaRepository<Kpi,Long> {
    List<Kpi> findByCycle_Id(Long cycleId);
}
