package com.spring.evalapi.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Objective {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "provide a user id")
    private long assignedUserId;

    @NotBlank(message = "objective name is required")
    private String title;

    @NotBlank(message = "description name is required")
    private String description;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    public Objective() {
    }

    public Objective(long assignedUserId, String title, String description, Cycle cycleId) {
        this.assignedUserId = assignedUserId;
        this.title = title;
        this.description = description;
        this.cycle = cycleId;
    }

    public long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Cycle getCycleId() {
        return cycle;
    }

    public void setCycleId(Cycle cycleId) {
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
}
