package com.spring.evalapi.dto;

import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.utils.CycleState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class NewCycleDto
{

    private String name;


    private LocalDate startDate;


    private LocalDate endDate;

    private CycleState state ;

    private List<KPI> kpis;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<KPI> getKpis() {
        return kpis;
    }

    public void setKpis(List<KPI> kpis) {
        this.kpis = kpis;
    }

    public NewCycleDto(String name, LocalDate startDate, LocalDate endDate, List<KPI> kpis) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kpis = kpis;
    }

    public NewCycleDto() {
    }
}
