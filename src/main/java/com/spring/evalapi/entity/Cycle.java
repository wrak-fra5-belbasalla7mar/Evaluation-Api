package com.spring.evalapi.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Long companyManagerId;

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

    @OneToMany(mappedBy = "cycle", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Kpi> kpis = new ArrayList<>();

    @OneToMany(mappedBy = "cycle", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Objective> objectives = new ArrayList<>();


    public void addKPI(Kpi kpi) {
        kpis.add(kpi);
        kpi.setCycle(this);
    }
    public void addObjective(Objective objective) {
        objectives.add(objective);
        objective.setCycle(this);
    }

    public Cycle(String name, LocalDate startDate, LocalDate endDate, CycleState state, List<Kpi> kpis, Long companyManagerId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.kpis = kpis;
        this.companyManagerId = companyManagerId;
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

    public CycleState getState() {
        return state;
    }

    public void setState(CycleState state) {
        this.state = state;
    }

    public List<Kpi> getKpis() {
        return kpis;
    }

    public void setKpis(List<Kpi> kpis) {
        this.kpis = kpis;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public Cycle(Long id, String name, LocalDate startDate, LocalDate endDate, CycleState state, List<Kpi> kpis, List<Objective> objectives) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = state;
        this.kpis = kpis;
        this.objectives = objectives;
    }

    public Cycle() {
    }

    public Long getCompanyManagerId() {
        return companyManagerId;
    }

    public void setCompanyManagerId(Long companyManagerId) {
        this.companyManagerId = companyManagerId;
    }
}
