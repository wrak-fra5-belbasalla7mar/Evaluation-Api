package com.spring.evalapi.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long submitterId;
    private long ratedPersonId;

    @Transient
    private Float score;
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "kpi_id")
    private KPI kpi;

    public Rating() {
    }

    public Rating(long submitterId, long ratedPersonId, Float score, String feedback, KPI kpiId) {
        this.submitterId = submitterId;
        this.ratedPersonId = ratedPersonId;
        this.score = score;
        this.feedback = feedback;
        this.kpi = kpiId;
    }

    public KPI getKpi() {
        return kpi;
    }

    public void setKpi(KPI kpi) {
        this.kpi = kpi;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(long submitterId) {
        this.submitterId = submitterId;
    }

    public long getRatedPersonId() {
        return ratedPersonId;
    }

    public void setRatedPersonId(long ratedPersonId) {
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
}
