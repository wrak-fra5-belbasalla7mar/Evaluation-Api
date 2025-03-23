package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.service.CycleService;
import com.spring.evalapi.utils.CycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface CycleRepository extends JpaRepository<Cycle,Long> {

    @Query("SELECT c FROM Cycle c ORDER BY c.startDate DESC LIMIT 1")
    Cycle findLatestCycle();
    Cycle findByState(CycleState state);
    List<Cycle> findAllByOrderByStartDateAsc();
    List<Cycle> findAllByOrderByStartDateDesc();
    Cycle findByEndDateBefore(Date endDate);
}

