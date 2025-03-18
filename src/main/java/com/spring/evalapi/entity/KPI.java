package com.spring.evalapi.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class KPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    @JsonBackReference
    private Cycle cycle;

//    @ManyToOne
//    @JoinColumn(name = "profile_id")
//    private Profile profile;


    @ElementCollection
    @MapKeyColumn(name = "role_level")
    @Column(name = "weight")
    private Map<String, Float> weights;
//
//    @ManyToMany(mappedBy = "kpis")
//    private List<Cycle> cycles;


    @OneToMany(mappedBy = "kpi" ,cascade = CascadeType.ALL )
    private List<Rating> ratings;

    public KPI() {
    }

    public KPI(String name, Cycle cycle, Map<String, Float> weights, List<Rating> ratings) {
        this.name = name;
        this.cycle = cycle;
        this.weights = weights;
        this.ratings = ratings;
    }

    public Map<String, Float> getWeights() {
        return weights;
    }

    public void setWeights(Map<String, Float> weights) {
        this.weights = weights;
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

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }



//    public Profile getProfile() {
//        return profile;
//    }
//
//    public void setProfile(Profile profile) {
//        this.profile = profile;
//    }
}
