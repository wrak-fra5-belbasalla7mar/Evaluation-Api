package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.RatingNotFoundException;
import com.spring.evalapi.entity.Rating;
import com.spring.evalapi.repository.KPIRepository;
import com.spring.evalapi.repository.RatingRepository;
import com.spring.evalapi.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private KPIRepository kpiRepository;

    @Autowired
    private  JwtUtils jwtUtils;
    @Transactional
    public Rating addRating(Rating rating, String jwtToken) {
        Long submitterId = jwtUtils.getUserIdFromJwtToken(jwtToken);
        rating.setSubmitterId(submitterId);
        if (kpiRepository.findById(rating.getKpi().getId())==null)throw new RatingNotFoundException("KPI with ID " + rating.getKpi().getId() + " not found");
        return ratingRepository.save(rating);
    }

    public Rating getRatingById(Long id){
        return ratingRepository.findById(id).orElseThrow(() -> new RatingNotFoundException("Rating with ID " + id + " not found"));
    }
    public List<Rating>getRatingsByKpiId(Long kpiId){
        return ratingRepository.findByKpi_Id(kpiId);
    }

    public String deleteRating(Long id){
        if (!ratingRepository.existsById(id))
           throw  new RatingNotFoundException("Rating with ID " + id + " not found");
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
