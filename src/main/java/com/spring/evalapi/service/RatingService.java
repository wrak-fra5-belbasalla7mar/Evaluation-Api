package com.spring.evalapi.service;

import com.spring.evalapi.dto.UserDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    private final KpiRepository kpiRepository;

    private final CycleRepository cycleRepository;
    private final UserService userService;

    private final TeamService teamService;
    private final KPIService kpiService;


    //    private static final Logger log = LoggerFactory.getLogger(RatingService.class);
    @Transactional
    public Rating addRating(Rating rating) throws IllegalStateException {
        Cycle passedCycle = cycleRepository.findByState(CycleState.PASSED);
        if (passedCycle == null) {
            throw new CycleStateException("No cycle in PASSED state found. Ratings can only be added during the PASSED state.");
        }

        UserDto submitterPerson = userService.getUserById(rating.getSubmitterId());
        UserDto ratedPerson = userService.getUserById(rating.getRatedPersonId());
        TeamDto submitterTeam = teamService.getTeamByMemberId(submitterPerson.getId());
        TeamDto ratedPersonTeam = teamService.getTeamByMemberId(ratedPerson.getId());
//        System.out.println("Submitter Team ID: " + submitterTeam.getId());
//        System.out.println("Rated Person Team ID: " + ratedPersonTeam.getId());
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

    @Transactional(readOnly = true)
    public Map<Long, Double> calculateAverageScores(Long cycleId) {
        List<Rating> cycleRatings = ratingRepository.findByCycle_Id(cycleId);
        Map<Long, List<Rating>> ratingsByPerson = cycleRatings.stream()
                .collect(Collectors.groupingBy(Rating::getRatedPersonId));

        Map<Long, Double> averageScores = new HashMap<>();

        for (Map.Entry<Long, List<Rating>> entry : ratingsByPerson.entrySet()) {
            Long ratedPersonId = entry.getKey();
            List<Rating> personRatings = entry.getValue();

            double weightedSum = 0.0,totalWeight = 0.0;
            UserDto user = userService.getUserById(ratedPersonId);
            String roleName = user.getRole();
            String roleLevel = user.getLevel().name();

            for (Rating rating : personRatings) {
                double score = rating.getScore();
                Kpi kpi = rating.getKpi();

                Double weight = kpiService.getWeightByKpiIdAndRoleNameAndRoleLevel(kpi.getId(), roleName, roleLevel);

                weightedSum += score * weight;
                totalWeight += weight;
            }

            double averageScore = (totalWeight > 0) ? weightedSum / totalWeight : 0.0;
            averageScores.put(ratedPersonId, averageScore);

            // log.info("Calculated average score for person ID {} in cycle ID {}: {}", ratedPersonId, cycleId, averageScore);
        }

        return averageScores;
    }
}