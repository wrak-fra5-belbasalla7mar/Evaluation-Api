package com.spring.evalapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kpi_role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KpiRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kpi_id")
    private Kpi kpi;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(nullable = false)
    private double weight = 1.0;

    public KpiRole(Kpi kpi, Role role, Double weight) {
        this.kpi = kpi;
        this.role = role;
        this.weight = weight;
    }
}
