package com.spring.evalapi.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="kpis")
public class KPI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycleId;

    @OneToMany(mappedBy = "kpiId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;

    public KPI() {
    }
    public KPI(String name, Cycle cycleId, List<Rating> ratings) {
        this.name = name;
        this.cycleId = cycleId;
        this.ratings = ratings;
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
        return cycleId;
    }

    public void setCycleId(Cycle cycleId) {
        this.cycleId = cycleId;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
