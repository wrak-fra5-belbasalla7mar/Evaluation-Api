package com.spring.evalapi.dto;

import com.spring.evalapi.entity.KPI;
import com.spring.evalapi.entity.Objectives;
import com.spring.evalapi.utils.CycleState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

public class CycleDto {
    private  String name;
    private Date startDate;
    private Date endDate;
    private CycleState State=CycleState.CLOSED;
    private List<KPI> kpis;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CycleState getState() {
        return State;
    }

    public void setState(CycleState state) {
        State = state;
    }

    public List<KPI> getKpis() {
        return kpis;
    }

    public void setKpis(List<KPI> kpis) {
        this.kpis = kpis;
    }

    public CycleDto(String name, Date startDate, Date endDate, CycleState state, List<KPI> kpis) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        State = state;
        this.kpis = kpis;
    }
    public CycleDto() {

    }

}
