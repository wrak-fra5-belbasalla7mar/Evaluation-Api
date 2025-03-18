package com.spring.evalapi.controller;


import com.spring.evalapi.entity.Profile;
import com.spring.evalapi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles(){
        return ResponseEntity.ok(profileService.getAllProfiles());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id){
        return ResponseEntity.ok(profileService.getProfileById(id));
    }


}
