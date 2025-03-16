package org.fawry.evaluationapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="kpi")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KPI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int kpiId;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    @OneToMany(mappedBy = "kpi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ratings> ratings;

    private String name;
}
