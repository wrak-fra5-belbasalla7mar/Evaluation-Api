package com.spring.evalapi.controller;

import com.spring.evalapi.entity.KpiRole;
import com.spring.evalapi.entity.Role;
import com.spring.evalapi.service.RoleService;
import com.spring.evalapi.utils.Level;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> addRole(@Valid @RequestBody Role role) {
        Role savedRole = roleService.addRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{name}/{level}")
    public ResponseEntity<Role> getRoleByNameAndLevel(@PathVariable String name, @PathVariable Level level) {
        Role role = roleService.getRoleByNameAndLevel(name, level);
        return ResponseEntity.ok(role);
    }

    @GetMapping("/{name}/{level}/kpis")
    public ResponseEntity<List<KpiRole>> getKpisForRole(@PathVariable String name, @PathVariable Level level) {
        List<KpiRole> kpiRoles = roleService.getKpisForRole(name, level);
        return ResponseEntity.ok(kpiRoles);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.NO_CONTENT);
    }
}