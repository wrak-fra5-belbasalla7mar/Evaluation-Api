package com.spring.evalapi.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spring.evalapi.utils.CycleState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "Cycle name is required")
    private  String name;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private CycleState state ;

    public void addKPI(KPI kpi) {
        kpis.add(kpi);
        kpi.setCycle(this);
    }


    @OneToMany(mappedBy = "cycle", orphanRemoval = true ,cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<KPI> kpis = new ArrayList<>();

    @OneToMany(mappedBy = "cycle",  orphanRemoval = true ,cascade = CascadeType.ALL)
    private List<Objective> objectives;

//    @OneToMany(mappedBy = "cycle")
//    private List<Profile> profiles;


    public Cycle() {
    }
    public Cycle(String name, LocalDate startDate, LocalDate endDate, CycleState state, List<KPI> kpis) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.kpis = kpis != null ? kpis : new ArrayList<>();
    }

    public CycleState getState() {
        return state;
    }

    public void setState(CycleState state) {
        this.state = state;
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

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }
}
