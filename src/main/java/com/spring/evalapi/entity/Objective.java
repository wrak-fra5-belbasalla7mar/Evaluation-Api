package com.spring.evalapi.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Objective {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "provide a user id")
    private Long assignedUserId;

    @NotBlank(message = "objective name is required")
    private String title;

    @NotBlank(message = "description name is required")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    @NotBlank(message = "deadline name is required")
    private LocalDate deadline;

    public Objective() {
    }

    public long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycleId) {
        this.cycle = cycleId;
    }

    public long getObjectiveId() {
        return id;
    }

    public void setObjectiveId(long objectiveId) {
        this.id = objectiveId;
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

    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }


}
