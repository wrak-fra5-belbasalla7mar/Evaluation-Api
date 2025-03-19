package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByKpi_Id(Long kpiId);

    List<Rating> findByCycle_Id(Long cycleId);

    List<Rating> findByRatedPersonId(Long ratedPersonId);

    List<Rating> findByCycle_IdAndRatedPersonId(Long cycleId, Long ratedPersonId);
}