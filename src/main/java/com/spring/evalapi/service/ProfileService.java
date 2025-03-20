package com.spring.evalapi.service;


import com.spring.evalapi.common.exception.ProfileNotFoundException;
import com.spring.evalapi.entity.Profile;

import com.spring.evalapi.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("Profile with ID " + id + " not found"));
    }
    public List<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }
    public Profile addProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public String deleteProfile(Long id){
        if (!profileRepository.existsById(id))
            throw new ProfileNotFoundException("Profile with ID " + id + " not found");
        profileRepository.deleteById(id);
        return "Profile Deleted Successfully!";
    }

}
