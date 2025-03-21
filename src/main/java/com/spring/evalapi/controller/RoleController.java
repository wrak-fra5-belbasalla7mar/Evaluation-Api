package com.spring.evalapi.controller;

import com.spring.evalapi.entity.KpiRole;
import com.spring.evalapi.entity.Role;
import com.spring.evalapi.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> addRole(@Valid @RequestBody Role role) {
        Role savedRole = roleService.addRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping("/{name}/{level}")
    public ResponseEntity<Role> getRoleByNameAndLevel(@PathVariable String name, @PathVariable String level) {
        Role role = roleService.getRoleByNameAndLevel(name, level);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/{name}/{level}/kpis")
    public ResponseEntity<List<KpiRole>> getKpisForRole(@PathVariable String name, @PathVariable String level) {
        List<KpiRole> kpiRoles = roleService.getKpisForRole(name, level);
        return ResponseEntity.ok(kpiRoles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.NO_CONTENT);
    }
}