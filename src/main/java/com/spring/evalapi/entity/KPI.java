package com.spring.evalapi.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class KPI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    @OneToMany(mappedBy = "kpi")
    private List<Rating> ratings;

    @ManyToOne
    @JoinColumn(name = "kpi_profile_id")
    private KPIProfile kpiProfile;

    public KPI() {
    }

    public KPI(String name, Cycle cycle, KPIProfile kpiProfile) {
        this.name = name;
        this.cycle = cycle;
        this.kpiProfile = kpiProfile;
    }

    public KPI(String name, Cycle cycle, List<Rating> ratings, KPIProfile kpiProfile) {
        this.name = name;
        this.cycle = cycle;
        this.ratings = ratings;
        this.kpiProfile = kpiProfile;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public KPIProfile getKpiProfile() {
        return kpiProfile;
    }

    public void setKpiProfile(KPIProfile kpiProfile) {
        this.kpiProfile = kpiProfile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cycle getCycleId() {
        return cycle;
    }

    public void setCycleId(Cycle cycleId) {
        this.cycle = cycleId;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
