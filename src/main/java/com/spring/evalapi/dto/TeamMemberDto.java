package com.spring.evalapi.dto;

import lombok.Data;

@Data
public class TeamMemberDto {
    private Long id;
    private Long userId;
    private TeamDto team;
}
