package com.spring.evalapi.dto;

import com.spring.evalapi.utils.Level;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private Long managerId;
    private String role;
    private Level level;
}
