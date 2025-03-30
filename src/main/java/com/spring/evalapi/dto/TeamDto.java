package com.spring.evalapi.dto;


import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private Long id;
    private Long managerId;
    private String name;
    private List<TeamMemberDto> members;
}
