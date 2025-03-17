package com.spring.evalapi.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "kpis")
public class KPI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

//    @ManyToOne
//    @JoinColumn(name = "profile_id")
//    private Profile profile;

    @OneToMany(mappedBy = "kpi" ,cascade = CascadeType.ALL )
    private List<Rating> ratings;

    public KPI() {
    }

    public KPI(String name, Cycle cycle) {
        this.name = name;
        this.cycle = cycle;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

//    public Profile getProfile() {
//        return profile;
//    }
//
//    public void setProfile(Profile profile) {
//        this.profile = profile;
//    }

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
}
