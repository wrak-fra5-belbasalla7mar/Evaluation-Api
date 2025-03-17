package com.spring.evalapi.entity;


import jakarta.persistence.*;

@Entity
public class Objectives {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private long id;

    private long assignedUserId;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    public Objectives() {
    }

    public Objectives(long assignedUserId, String title, String description, Cycle cycleId) {
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
