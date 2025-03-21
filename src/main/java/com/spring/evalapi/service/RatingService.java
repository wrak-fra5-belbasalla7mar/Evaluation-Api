package com.spring.evalapi.service;

import com.spring.evalapi.common.exception.KpiNotFoundException;
import com.spring.evalapi.common.exception.RatingNotFoundException;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.entity.Rating;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KpiRepository;
import com.spring.evalapi.repository.RatingRepository;
import com.spring.evalapi.utils.CycleState;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private KpiRepository kpiRepository;

    @Autowired
    private CycleRepository cycleRepository;

    private static final Logger log = LoggerFactory.getLogger(CycleService.class);

    @Transactional
    public Rating addRating(Rating rating) {
        if (rating.getKpi() == null) {
            throw new IllegalArgumentException("KPI is required for a Rating");
        }
        Kpi kpi = kpiRepository.findById(rating.getKpi().getId())
                .orElseThrow(() -> new KpiNotFoundException("KPI with ID " + rating.getKpi().getId() + " not found"));

        log.info("Loaded Kpi: id={}, name={}, description={}, role={}, level={}, weight={}",
                kpi.getId(), kpi.getName(), kpi.getDescription(), kpi.getRole(), kpi.getLevel(), kpi.getWeight());
        Cycle passedCycle = cycleRepository.findByState(CycleState.PASSED);
        if (passedCycle==null) {
            throw new IllegalStateException("No cycle in PASSED state found. Ratings can only be added during the PASSED state.");
        }
        if (kpi.getCycle() == null || !kpi.getCycle().getId().equals(passedCycle.getId())) {
            throw new IllegalStateException("KPI with ID " + kpi.getId() + " is not associated with the current cycle (ID: " + passedCycle.getId() + ").");
        }
        rating.setKpi(kpi);
        rating.setCycle(passedCycle);
        return ratingRepository.save(rating);
    }

    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Rating with ID " + id + " not found"));
    }

    public List<Rating> getRatingsByKpiId(Long kpiId) {
        return ratingRepository.findByKpi_Id(kpiId);
    }

    @Transactional
    public String deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new RatingNotFoundException("Rating with ID " + id + " not found");
        }
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

    @Transactional
    public void calculateAndStoreAverage(Long cycleId) {
        List<Rating> cycleRatings = ratingRepository.findByCycle_Id(cycleId);
        Map<Long, List<Rating>> ratingsByPerson = cycleRatings.stream()
                .collect(Collectors.groupingBy(Rating::getRatedPersonId));
        for (Map.Entry<Long, List<Rating>> entry : ratingsByPerson.entrySet()) {
            List<Rating> personRatings = entry.getValue();

            double weightedSum = 0.0,totalWeight = 0.0,averageScore=0.0;

            for (Rating rating : personRatings) {
                double score = rating.getScore();
                double weight = rating.getKpi().getWeight();
                weightedSum += score * weight;
                totalWeight += weight;
            }
            if (totalWeight!=0)
             averageScore= weightedSum / totalWeight;
            for (Rating rating : personRatings) {
                rating.setAverageScore(averageScore);
                ratingRepository.save(rating);
            }
        }
    }
}