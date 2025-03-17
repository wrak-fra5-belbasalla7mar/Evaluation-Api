package com.spring.evalapi.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "kpi_profiles")
public class KPIProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "kpiProfile")
    private List<KPI> kpis;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    public KPIProfile() {
    }

    public KPIProfile(String name, List<KPI> kpis, Cycle cycle) {
        this.name = name;
        this.kpis = kpis;
        this.cycle = cycle;
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

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }
}
