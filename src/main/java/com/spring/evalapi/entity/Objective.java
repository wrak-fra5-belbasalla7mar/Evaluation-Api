package com.spring.evalapi.entity;


import com.fasterxml.jackson.annotation.*;
import com.spring.evalapi.utils.ObjectiveState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Objective {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ObjectiveState state;

    @NotNull(message = "provide a user id")
    private Long assignedUserId;

    @NotNull(message = "provide a manager id")
    private Long managerId;

    @NotBlank(message = "objective name is required")
    private String title;

    @NotNull(message = "provide team id")
    private Long TeamId;



    @NotBlank(message = "description name is required")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnore
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;


    @Transient
    @NotNull(message = "Cycle ID is required")
    private Long cycleId;

    @JsonProperty("cycleId")
    public Long getCycleId() {
        return cycle != null ? cycle.getId() : cycleId;
    }

    @NotNull(message = "Deadline cannot be null")
    private LocalDate deadline;




}
