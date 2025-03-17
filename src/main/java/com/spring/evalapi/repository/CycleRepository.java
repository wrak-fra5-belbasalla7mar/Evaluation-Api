package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


public interface CycleRepository extends JpaRepository<Cycle,Long> {

    @Query("SELECT c FROM Cycle c ORDER BY ABS(FUNCTION('DATEDIFF', c.startDate, :now)) ASC")
    Cycle findFirstCycleClosestToNow(@Param("now") LocalDateTime now);

}
