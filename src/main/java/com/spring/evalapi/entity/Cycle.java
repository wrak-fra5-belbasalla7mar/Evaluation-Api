package com.spring.evalapi.entity;


import com.spring.evalapi.utils.CycleState;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cycles")
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private  String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private CycleState state=CycleState.CLOSED;

    @OneToMany(mappedBy = "cycle", orphanRemoval = true ,cascade = CascadeType.ALL)
    private List<KPI> kpis;

    @OneToMany(mappedBy = "cycle",  orphanRemoval = true ,cascade = CascadeType.ALL)
    private List<Objectives> objectives;

//    @OneToMany(mappedBy = "cycle")
//    private List<Profile> profiles;


    public Cycle() {
    }


    public Cycle(String name, LocalDate startDate, LocalDate endDate, CycleState state, List<KPI> kpis, List<Objectives> objectives) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.kpis = kpis;
        this.objectives = objectives;

    }

    public CycleState getState() {
        return state;
    }

    public void setState(CycleState state) {
        this.state = state;
    }

//    public List<Profile> getKpiProfiles() {
//        return profiles;
//    }
//
//    public void setKpiProfiles(List<Profile> profiles) {
//        this.profiles = profiles;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public CycleState getCycleState() {
        return state;
    }

    public void setCycleState(CycleState cycleState) {
        this.state = cycleState;
    }

    public List<KPI> getKpis() {
        return kpis;
    }

    public void setKpis(List<KPI> kpis) {
        this.kpis = kpis;
    }

    public List<Objectives> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objectives> objectives) {
        this.objectives = objectives;
    }
}
