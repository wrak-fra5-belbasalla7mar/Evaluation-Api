package com.spring.evalapi.controller;

import com.spring.evalapi.entity.Rating;
import com.spring.evalapi.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;


    @PostMapping
    public ResponseEntity<Rating> addRating(@Valid @RequestBody Rating rating) {
        Rating savedRating = ratingService.addRating(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRatingById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ratingService.getRatingById(id));
    }

    @GetMapping("/kpi/{kpiId}")
    public ResponseEntity<List<Rating>> getRatingByKpiId(@PathVariable Long kpiId) {
        return ResponseEntity.ok(ratingService.getRatingsByKpiId(kpiId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.deleteRating(id));
    }

    @GetMapping("/cycle/{cycleId}")
    public ResponseEntity<List<Rating>> getRatingsByCycleId(@PathVariable Long cycleId) {
        return ResponseEntity.ok(ratingService.getRatingsByCycleId(cycleId));
    }

    @GetMapping("/ratedPerson/{ratedPersonId}")
    public ResponseEntity<List<Rating>> getRatingsByRatedPersonId(@PathVariable Long ratedPersonId) {
        return ResponseEntity.ok(ratingService.getRatingsByRatedPersonId(ratedPersonId));
    }

    @GetMapping("/cycle/{cycleId}/ratedPerson/{ratedPersonId}")
    public ResponseEntity<List<Rating>> getRatingsByCycleIdAndRatedPersonId(@PathVariable Long cycleId, @PathVariable Long ratedPersonId) {
        return ResponseEntity.ok(ratingService.getRatingsByCycleIdAndRatedPersonId(cycleId, ratedPersonId));
    }
}
