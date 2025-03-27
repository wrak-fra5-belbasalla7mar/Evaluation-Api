package com.spring.evalapi.entity;


import com.fasterxml.jackson.annotation.*;
import com.spring.evalapi.utils.ObjectiveState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Objective {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ObjectiveState state;

    @NotNull(message = "provide a user id")
    private Long assignedUserId;

    @NotBlank(message = "objective name is required")
    private String title;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
        this.cycleId=cycle.getId();
    }
    public void setCycleId(Long cycleId) {
        this.cycleId = cycleId;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Objective(Long id, ObjectiveState state, Long assignedUserId, String title, String description, Cycle cycle, Long cycleId, LocalDate deadline) {
        this.id = id;
        this.state = state;
        this.assignedUserId = assignedUserId;
        this.title = title;
        this.description = description;
        this.cycle = cycle;
        this.cycleId = cycleId;
        this.deadline = deadline;
    }

    public ObjectiveState getState() {
        return state;
    }

    public void setState(ObjectiveState state) {
        this.state = state;
    }

    public Objective() {
    }
}
