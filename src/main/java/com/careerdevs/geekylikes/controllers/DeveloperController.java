package com.careerdevs.geekylikes.controllers;


import com.careerdevs.geekylikes.entities.auth.User;
import com.careerdevs.geekylikes.entities.avatar.Avatar;
import com.careerdevs.geekylikes.entities.developer.Developer;
import com.careerdevs.geekylikes.entities.geekout.Geekout;
import com.careerdevs.geekylikes.entities.language.Language;
import com.careerdevs.geekylikes.repositories.AvatarRepository;
import com.careerdevs.geekylikes.repositories.DeveloperRepository;
import com.careerdevs.geekylikes.repositories.GeekoutRepository;
import com.careerdevs.geekylikes.repositories.UserRepository;
import com.careerdevs.geekylikes.security.services.UserDetailsImpl;
import com.careerdevs.geekylikes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/developers")
public class DeveloperController {

    @Autowired
    private DeveloperRepository repository;

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private GeekoutRepository geekoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @GetMapping
    @ResponseBody
    public List<Developer> getDevelopers() {
        return repository.findAll();
    }

    @GetMapping("/lang/{langId}")
    public List<Developer> getDevsByLanguage(@PathVariable Long langId) {
        return repository.findAllByLanguages_id(langId);
    }

    @GetMapping("/cohort/{cohort}")
    public ResponseEntity<List<Developer>> getDevelopersByCohort(@PathVariable Integer cohort) {
        return new ResponseEntity<>(repository.findAllByCohort(cohort, Sort.by("name")), HttpStatus.OK);
    }

    @GetMapping("/likes/{devId}")
    public List<Geekout> getApprovedGeekouts(@PathVariable Long devId) {
        return geekoutRepository.findAllByApprovals_developer_id(devId);
    }

    @GetMapping("/self")
    public @ResponseBody Developer getSelf() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        return repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Developer getOneDeveloper(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer newDeveloper) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        //TODO add check for existing developer profile.
        newDeveloper.setUser(currentUser);
        return new ResponseEntity<>(repository.save(newDeveloper), HttpStatus.CREATED);
    }

    @PutMapping("/photo")
    public Developer addPhoto(@RequestBody Developer dev) { // TODO refactor dev to updates
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }

        Developer developer = repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (developer.getAvatar() != null) {
            Avatar avatar = developer.getAvatar();
            avatar.setUrl(dev.getAvatar().getUrl());
            avatarRepository.save(avatar);
            return developer;
        }
        Avatar avatar = avatarRepository.save(dev.getAvatar());
        developer.setAvatar(avatar);
        return repository.save(developer);
    }

    @PutMapping("/language")
    public Developer addLanguage(@RequestBody List<Language> updates) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Developer developer = repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        developer.languages.addAll(updates);
        return repository.save(developer);
    }

    @PutMapping
    public @ResponseBody Developer updateDeveloper(@RequestBody Developer updates) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        Developer developer = repository.findByUser_id(currentUser.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

//        updates.setId(developer.getId());
//        return repository.save(updates);
        if (updates.getName() != null) developer.setName(updates.getName());
        if (updates.getEmail() != null) developer.setEmail(updates.getEmail());
        if (updates.getCohort() != null) developer.setCohort(updates.getCohort());
    //    if (updates.languages != null) developer.languages = updates.languages;

        return repository.save(developer);
    }

    @DeleteMapping
    public ResponseEntity<String> destroyDeveloper() {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return null;
        }
        repository.deleteByUser_id(currentUser.getId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }



}
