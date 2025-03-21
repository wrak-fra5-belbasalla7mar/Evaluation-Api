package com.spring.evalapi.controller;

import com.spring.evalapi.entity.Rating;
import com.spring.evalapi.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
//    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
//    public ResponseEntity<Rating> addRating(@Valid @RequestBody Rating rating, @RequestHeader("Authorization") String authHeader) {
//        String jwtToken = authHeader.substring(7);
//        Rating savedRating = ratingService.addRating(rating, jwtToken);
//        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
//    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<Rating> getRatingById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ratingService.getRatingById(id));
    }

    @GetMapping("/kpi/{kpiId}")
//    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<Rating>> getRatingByKpiId(@PathVariable Long kpiId) {
        return ResponseEntity.ok(ratingService.getRatingsByKpiId(kpiId));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<String> deleteRating(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.deleteRating(id));
    }

    @GetMapping("/cycle/{cycleId}")
//    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<Rating>> getRatingsByCycleId(@PathVariable Long cycleId) {
        return ResponseEntity.ok(ratingService.getRatingsByCycleId(cycleId));
    }

    @GetMapping("/ratedPerson/{ratedPersonId}")
//    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<Rating>> getRatingsByRatedPersonId(@PathVariable Long ratedPersonId) {
        return ResponseEntity.ok(ratingService.getRatingsByRatedPersonId(ratedPersonId));
    }

    @GetMapping("/cycle/{cycleId}/ratedPerson/{ratedPersonId}")
//    @PreAuthorize("hasAnyRole('TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<Rating>> getRatingsByCycleIdAndRatedPersonId(@PathVariable Long cycleId, @PathVariable Long ratedPersonId) {
        return ResponseEntity.ok(ratingService.getRatingsByCycleIdAndRatedPersonId(cycleId, ratedPersonId));
    }
}