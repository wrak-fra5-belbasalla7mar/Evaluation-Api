package com.spring.evalapi.entity;


import com.spring.evalapi.utils.CycleState;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cycles")
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    private  String name;
    private  Date startDate;
    private Date endDate;
    @Enumerated(EnumType.STRING)
    private CycleState cycleState=CycleState.CLOSED;



    @OneToMany(mappedBy = "cycleId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KPI> kpis;

    @OneToMany(mappedBy = "cycleId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objectives> objectives;

    public Cycle() {
    }

    public Cycle(String name, Date startDate, Date endDate, CycleState cycleState, List<KPI> kpis, List<Objectives> objectives) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cycleState = cycleState;
        this.kpis = kpis;
        this.objectives = objectives;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CycleState getCycleState() {
        return cycleState;
    }

    public void setCycleState(CycleState cycleState) {
        this.cycleState = cycleState;
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
