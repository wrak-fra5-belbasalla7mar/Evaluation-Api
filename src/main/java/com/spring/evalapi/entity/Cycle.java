package com.spring.evalapi.entity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.evalapi.utils.CycleState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@AllArgsConstructor
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


    public Cycle(String name, LocalDate startDate, LocalDate endDate, CycleState cycleState, List<Kpi> kpis, Long companyManagerId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = cycleState;
        this.kpis = kpis;
        this.companyManagerId = companyManagerId;
    }
}
