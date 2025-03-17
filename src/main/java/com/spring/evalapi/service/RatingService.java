package com.spring.evalapi.service;

import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.entity.Rating;
import com.spring.evalapi.repository.KPIRepository;
import com.spring.evalapi.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private KPIRepository kpiRepository;

    public Rating addRating(Long kpiId,Rating rating) {
        KPI kpi = kpiRepository.findById(kpiId).orElseThrow(() -> new IllegalArgumentException("KPI not found "));
       rating.setKpiId(kpi);
        return ratingRepository.save(rating);
    }

    public Optional<Rating> getRatingById(Long id){
        return ratingRepository.findById(id);
    }
    public List<Rating>getRatingsByKpiId(Long kpiId){
        return ratingRepository.findByKpi_id(kpiId);
    }
    public String deleteRating(Long id){
        if (!ratingRepository.existsById(id))
            throw new IllegalArgumentException("This Rating not found");
        ratingRepository.deleteById(id);
        return "Rating Deleted Successfully!";
    }
}
