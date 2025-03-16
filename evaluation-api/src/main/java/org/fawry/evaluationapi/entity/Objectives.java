package org.fawry.evaluationapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="objectives")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Objectives {

    @Id
    private long objectiveId;

    @ManyToOne
    @JoinColumn(name="cycle_id")
    private Cycle cycle;

    private String title;
    private String description;

    private long assignedUserId;
    private Date deadline;
}
