package com.spring.evalapi.repository;

import com.spring.evalapi.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {
    List<Rating> findByKpi_id(Long KpiId);

}
