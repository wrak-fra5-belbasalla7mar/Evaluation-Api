package com.spring.evalapi.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private Long managerId;
    private List<TeamMemberDto> members;
}
