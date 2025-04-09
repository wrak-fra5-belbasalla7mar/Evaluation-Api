package com.spring.evalapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "kpi_role")
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
    private double weight=1.0;

    public KpiRole() {
    }

    public KpiRole(Kpi kpi, Role role, Double weight) {
        this.kpi = kpi;
        this.role = role;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kpi getKpi() {
        return kpi;
    }

    public void setKpi(Kpi kpi) {
        this.kpi = kpi;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}