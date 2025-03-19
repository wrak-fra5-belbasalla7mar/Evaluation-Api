package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.KpiNotFoundException;
import com.spring.evalapi.common.exception.RatingNotFoundException;
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

    public Rating addRating(Rating rating) {
        if (kpiRepository.findById(rating.getKpi().getId())==null)throw new KpiNotFoundException("KPI with ID " + rating.getKpi().getId() + " not found");
        return ratingRepository.save(rating);
    }

    public Optional<Rating> getRatingById(Long id){
        return ratingRepository.findById(id);
    }
    public List<Rating>getRatingsByKpiId(Long kpiId){
        return ratingRepository.findByKpi_Id(kpiId);
    }

    public String deleteRating(Long id){
        if (!ratingRepository.existsById(id))
            throw new RatingNotFoundException();
        ratingRepository.deleteById(id);
        return "Rating Deleted Successfully!";
    }
    public List<Rating> getRatingsByCycleId(Long cycleId) {
        return ratingRepository.findByCycle_Id(cycleId);
    }

    public List<Rating> getRatingsByRatedPersonId(Long ratedPersonId) {
        return ratingRepository.findByRatedPersonId(ratedPersonId);
    }

    public List<Rating> getRatingsByCycleIdAndRatedPersonId(Long cycleId, Long ratedPersonId) {
        return ratingRepository.findByCycle_IdAndRatedPersonId(cycleId, ratedPersonId);
    }
}
