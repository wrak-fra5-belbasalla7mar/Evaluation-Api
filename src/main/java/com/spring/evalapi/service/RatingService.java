package com.spring.evalapi.service;

import com.spring.evalapi.exception.CycleStateException;
import com.spring.evalapi.exception.KpiNotAssociatedException;
import com.spring.evalapi.exception.NotFoundException;
import com.spring.evalapi.dto.TeamDto;
import com.spring.evalapi.entity.Cycle;
import com.spring.evalapi.entity.Kpi;
import com.spring.evalapi.entity.Rating;
import com.spring.evalapi.repository.CycleRepository;
import com.spring.evalapi.repository.KpiRepository;
import com.spring.evalapi.repository.RatingRepository;
import com.spring.evalapi.utils.CycleState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingService {

    private final RatingRepository ratingRepository;

    private final KpiRepository kpiRepository;

    private final CycleRepository cycleRepository;
    private final UserService userService;

    private final TeamService teamService;

    public RatingService(RatingRepository ratingRepository, KpiRepository kpiRepository, CycleRepository cycleRepository, UserService userService, TeamService teamService) {
        this.ratingRepository = ratingRepository;
        this.kpiRepository = kpiRepository;
        this.cycleRepository = cycleRepository;
        this.userService = userService;
        this.teamService = teamService;
    }


    //    private static final Logger log = LoggerFactory.getLogger(RatingService.class);
    @Transactional
    public Rating addRating(Rating rating) throws IllegalStateException {
        Cycle passedCycle = cycleRepository.findByState(CycleState.PASSED);
        if (passedCycle == null) {
            throw new CycleStateException("No cycle in PASSED state found. Ratings can only be added during the PASSED state.");
        }

        userService.getUserById(rating.getSubmitterId());
        userService.getUserById(rating.getRatedPersonId());
        TeamDto submitterTeam = teamService.getTeamByUserId(rating.getSubmitterId());
        TeamDto ratedPersonTeam = teamService.getTeamByUserId(rating.getRatedPersonId());
        if (!submitterTeam.getId().equals(ratedPersonTeam.getId())) {
            throw new NotFoundException("Submitter (ID: " + rating.getSubmitterId() + ") and Rated Person (ID: " + rating.getRatedPersonId() + ") are not in the same team.");
        }

        Long kpiId = rating.getKpi().getId();
        Kpi kpi = kpiRepository.findById(kpiId)
                .orElseThrow(() -> new NotFoundException("KPI with ID " + kpiId + " not found"));


        if (kpi.getCycle() == null || !kpi.getCycle().getId().equals(passedCycle.getId())) {
            throw new KpiNotAssociatedException("KPI with ID " + kpiId + " is not associated with the current cycle (ID: " + passedCycle.getId() + ").");
        }

        rating.setKpi(kpi);
        rating.setCycle(passedCycle);
        return ratingRepository.save(rating);
    }
    public Rating getRatingById(Long id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rating with ID " + id + " not found"));
    }

    public List<Rating> getRatingsByKpiId(Long kpiId) {
        return ratingRepository.findByKpi_Id(kpiId);
    }

    public String deleteRating(Long id) {
        ratingRepository.delete(ratingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rating with ID " + id + " not found")));
        return "Rating deleted successfully";
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

            double weightedSum = 0.0, totalWeight = 0.0, averageScore = 0.0;

            for (Rating rating : personRatings) {
                double score = rating.getScore();
                double weight = 0.0;
                weightedSum += score * weight;
                totalWeight += weight;
            }

            if (totalWeight != 0) {
                averageScore = weightedSum / totalWeight;
            }

//            log.info("Calculated average score for person ID {} in cycle ID {}: {}", ratedPersonId, cycleId, averageScore);
            for (Rating rating : personRatings) {
                rating.setAverageScore(averageScore);
                ratingRepository.save(rating);
            }
        }
    }
}