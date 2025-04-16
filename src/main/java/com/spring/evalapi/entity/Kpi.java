package com.spring.evalapi.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Setter
@Getter
public class Kpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Name is required")
    private String name;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    @JsonIgnore
    private Cycle cycle;

    @OneToMany(mappedBy = "kpi", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Rating> ratings;

    @OneToMany(mappedBy = "kpi", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<KpiRole> kpiRoles = new ArrayList<>();
}
