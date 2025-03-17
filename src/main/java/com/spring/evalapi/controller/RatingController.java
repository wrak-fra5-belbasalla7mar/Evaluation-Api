package com.spring.evalapi.controller;


import com.spring.evalapi.entity.Rating;
import com.spring.evalapi.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/kpi/{kpiId}")
    public ResponseEntity<Rating>addRating(@PathVariable Long kpiId, @RequestBody Rating rating){
        return ResponseEntity.ok(ratingService.addRating(kpiId, rating));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Optional<Rating>>getRatingById(@PathVariable Long id){
        return ResponseEntity.ok(ratingService.getRatingById(id));
    }

    @GetMapping("/kpi/{kpiId}")
    public ResponseEntity<List<Rating>>getRatingByKpiId(@PathVariable Long kpiId){
        return ResponseEntity.ok(ratingService.getRatingsByKpiId(kpiId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteRating(@PathVariable Long id){
        return ResponseEntity.ok(ratingService.deleteRating(id));
    }
}
