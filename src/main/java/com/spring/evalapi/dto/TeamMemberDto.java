package com.spring.evalapi.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class TeamMemberDto {
    private Long id;
    private Long userId;
    private TeamDto team;
}
