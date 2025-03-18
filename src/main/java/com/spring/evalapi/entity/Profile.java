package com.spring.evalapi.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ElementCollection
    @MapKeyColumn(name = "role_level")
    @Column(name = "weight")
    private Map<String, Float> weights;

    @OneToMany(mappedBy = "profile")
    private List<KPI> kpis;
    public Profile() {
    }

    public Profile(String name, Map<String, Float> weights, List<KPI> kpis) {
        this.name = name;
        this.weights = weights;
        this.kpis = kpis;
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

    public List<KPI> getKpis() {
        return kpis;
    }

    public void setKpis(List<KPI> kpis) {
        this.kpis = kpis;
    }
    public Map<String, Float> getWeights() {
        return weights;
    }

    public void setWeights(Map<String, Float> weights) {
        this.weights = weights;
    }
}
