package org.fawry.evaluationapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fawry.evaluationapi.utils.CycleState;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="cycle")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cycle_id")
    private  int cycleId;

    private String name;

    private Date startDate;

    private Date endDate;

    @Enumerated(EnumType.STRING)
    private CycleState cycleState=CycleState.CLOSED;

    @OneToMany(mappedBy = "cycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KPI> kpis;

    @OneToMany(mappedBy = "cycle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objectives> objectives;
}
