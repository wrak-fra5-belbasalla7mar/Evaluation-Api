package com.spring.evalapi.controller;

import com.spring.evalapi.entity.Profile;
import com.spring.evalapi.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @PreAuthorize("hasRole('COMPANY_MANAGER')")
    public ResponseEntity<Profile> addProfile(@Valid @RequestBody Profile profile) {
        Profile savedProfile = profileService.addProfile(profile);
        return new ResponseEntity<>(savedProfile, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok(profileService.getAllProfiles());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'TEAM_MANAGER', 'COMPANY_MANAGER')")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getProfileById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY_MANAGER')")
    public ResponseEntity<Profile> updateProfile( @Valid @RequestBody Profile profileDetails) {
        return ResponseEntity.ok(profileService.updateProfile( profileDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COMPANY_MANAGER')")
    public ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.deleteProfile(id));
    }
}