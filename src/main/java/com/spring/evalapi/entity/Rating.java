package com.spring.evalapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Submitter ID is required")
    private Long submitterId;

    @NotNull(message = "Rated person ID is required")
    private Long ratedPersonId;

    @NotNull(message = "Score is required")
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must be at most 5")
    private Float score;

    private String feedback;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "kpi_id")
    private Kpi kpi;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    @JsonIgnore
    private Cycle cycle;


    public Rating() {
    }

    public Rating(Long submitterId, Long ratedPersonId, Float score, String feedback, Kpi kpi, Cycle cycle) {
        this.submitterId = submitterId;
        this.ratedPersonId = ratedPersonId;
        this.score = score;
        this.feedback = feedback;
        this.kpi = kpi;
        this.cycle = cycle;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(Long submitterId) {
        this.submitterId = submitterId;
    }

    public Long getRatedPersonId() {
        return ratedPersonId;
    }

    public void setRatedPersonId(Long ratedPersonId) {
        this.ratedPersonId = ratedPersonId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Kpi getKpi() {
        return kpi;
    }

    public void setKpi(Kpi kpi) {
        this.kpi = kpi;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

}