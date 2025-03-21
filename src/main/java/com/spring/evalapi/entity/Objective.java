package com.spring.evalapi.entity;


import com.fasterxml.jackson.annotation.*;
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
    @JsonIgnore
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    @JsonProperty("cycleId")
    public Long getCycleId() {
        return cycle != null ? cycle.getId() : null;
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
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Objective(Long id, Long assignedUserId, String title, String description, Cycle cycle, LocalDate deadline) {
        this.id = id;
        this.assignedUserId = assignedUserId;
        this.title = title;
        this.description = description;
        this.cycle = cycle;
        this.deadline = deadline;
    }

    public Objective() {
    }
}
