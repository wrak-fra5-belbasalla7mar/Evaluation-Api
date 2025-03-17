package com.spring.evalapi.entity;


import jakarta.persistence.*;

@Entity
@Table(name="objectives")
public class Objectives {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private long objectiveId;

    private long assignedUserId;
    private String title;
    private String description;

    @ManyToOne
    private Cycle cycleId;

    public Objectives() {
    }

    public Objectives(long assignedUserId, String title, String description, Cycle cycleId) {
        this.assignedUserId = assignedUserId;
        this.title = title;
        this.description = description;
        this.cycleId = cycleId;
    }

    public long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public Cycle getCycleId() {
        return cycleId;
    }

    public void setCycleId(Cycle cycleId) {
        this.cycleId = cycleId;
    }

    public long getObjectiveId() {
        return objectiveId;
    }

    public void setObjectiveId(long objectiveId) {
        this.objectiveId = objectiveId;
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
