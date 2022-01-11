package com.careerdevs.geekylikes.controllers;


import com.careerdevs.geekylikes.entities.auth.User;
import com.careerdevs.geekylikes.entities.developer.Developer;
import com.careerdevs.geekylikes.entities.relationship.ERelationship;
import com.careerdevs.geekylikes.entities.relationship.Relationship;
import com.careerdevs.geekylikes.payloads.response.MessageResponse;
import com.careerdevs.geekylikes.repositories.DeveloperRepository;
import com.careerdevs.geekylikes.repositories.RelationshipRepository;
import com.careerdevs.geekylikes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api/relationship")
public class RelationshipController {

    @Autowired
    private RelationshipRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    DeveloperRepository developerRepository;

    @PostMapping("/add/{rId}")
    public ResponseEntity<MessageResponse> addRelationship(@PathVariable Long rId){
        User currentUser=userService.getCurrentUser();

        if(currentUser==null)
            return new ResponseEntity<>(new MessageResponse("Invalid User"), HttpStatus.BAD_REQUEST);

        Developer originator=developerRepository.findByUser_id(currentUser.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Developer recipient=developerRepository.findByUser_id(rId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        repository.save(new Relationship(originator, recipient, ERelationship.PENDING));

        return new ResponseEntity<>(new MessageResponse("Success"),HttpStatus.CREATED);
    }


}
