package com.spring.evalapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class KPI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Cycle cycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(mappedBy = "kpi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;

    public KPI() {
    }

    public KPI(String name, Cycle cycle, Profile profile, List<Rating> ratings) {
        this.name = name;
        this.cycle = cycle;
        this.profile = profile;
        this.ratings = ratings;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Map<String, Float> getWeights() {
        return this.profile != null ? this.profile.getWeights() : null;
    }
}