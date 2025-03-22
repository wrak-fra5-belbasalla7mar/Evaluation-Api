package com.spring.evalapi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String level;


    @ManyToMany
    @JoinTable(
            name = "kpi_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "kpi_id")
    )
    @JsonIgnore
    private List<Kpi> kpis;



    public Role() {
    }

    public Role(String name, String level, ArrayList<Kpi> kpis) {
        this.name = name;
        this.level = level;
        this.kpis = kpis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Kpi> getKpis() {
        return kpis;
    }

    public void setKpis(ArrayList<Kpi> kpis) {
        this.kpis = kpis;
    }


}
