package com.spring.evalapi.service;

import com.spring.evalapi.exception.FieldIsRequiredException;
import com.spring.evalapi.entity.KpiRole;
import com.spring.evalapi.entity.Role;
import com.spring.evalapi.repository.KpiRoleRepository;
import com.spring.evalapi.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final KpiRoleRepository kpiRoleRepository;


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role addRole(Role role) {
        if (role.getName() == null || role.getName().isEmpty()) {
            throw new FieldIsRequiredException("Role name is required");
        }
        if (role.getLevel() == null || role.getLevel().isEmpty()) {
            throw new FieldIsRequiredException("Role level is required");
        }
        if (roleRepository.findByNameAndLevel(role.getName(), role.getLevel()).isPresent()) {
            throw new FieldIsRequiredException("Role with name " + role.getName() + " and level " + role.getLevel() + " already exists");
        }
        return roleRepository.save(role);
    }

    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

    public Role getRoleByNameAndLevel(String name, String level) {
        return roleRepository.findByNameAndLevel(name, level).orElseThrow();
    }

    public List<KpiRole> getKpisForRole(String roleName, String roleLevel) {
        return kpiRoleRepository.findByRole_NameAndRole_Level(roleName, roleLevel);
    }
}