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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/relationships")
public class RelationshipController {
    @Autowired
    private RelationshipRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private DeveloperRepository developerRepository;

    @PostMapping("/add/{rId}") // /add/38?type=block // pending
    public ResponseEntity<MessageResponse> addRelationship(@PathVariable Long rId) {
        // create a pending relationship between logged in user and rId (recipientId)
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ResponseEntity<>(new MessageResponse("Invalid User"), HttpStatus.BAD_REQUEST);
        }

        Developer originator = developerRepository.findByUser_id(currentUser.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Developer recipient = developerRepository.findById(rId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Optional<Relationship> rel = repository.findByOriginator_idAndRecipient_id(originator.getId(), recipient.getId());

        if (rel.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Nice try, be patient"), HttpStatus.OK);
        }

        Optional<Relationship> invRel = repository.findByOriginator_idAndRecipient_id(recipient.getId(), originator.getId());

        if (invRel.isPresent()) {
            switch (invRel.get().getType()) {
                case PENDING:
                    invRel.get().setType(ERelationship.ACCEPTED);
                    repository.save(invRel.get());
                    return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.CREATED);
                case ACCEPTED:
                    return new ResponseEntity<>(new MessageResponse("You are already friends stop taxing our system"), HttpStatus.OK);
                case BLOCKED:
                    return new ResponseEntity<>(new MessageResponse("GG"), HttpStatus.OK);
                default:
                    return new ResponseEntity<>(new MessageResponse("SERVER ERROR _ DEFAULT RELATIONSHIP"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        try {
            repository.save(new Relationship(originator, recipient, ERelationship.PENDING));
        } catch (Exception e) {
            System.out.println("error" + e.getLocalizedMessage());
            return new ResponseEntity<>(new MessageResponse("Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.CREATED);

    }

    @PostMapping("/block/{rId}")
    public ResponseEntity<MessageResponse> blockRecipient(@PathVariable Long rId) {
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ResponseEntity<>(new MessageResponse("Invalid User"), HttpStatus.BAD_REQUEST);
        }

        Developer originator = developerRepository.findByUser_id(currentUser.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Developer recipient = developerRepository.findById(rId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Optional<Relationship> rel = repository.findByOriginator_idAndRecipient_id(originator.getId(), recipient.getId());

        if (rel.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Nice try, be patient"), HttpStatus.OK);
        }

        Optional<Relationship> invRel = repository.findByOriginator_idAndRecipient_id(recipient.getId(), originator.getId());


        // create the edge case solutions
        // T N already exists
        //        // if pending change to Blocked
        //        // if approved change to blocked
        //        // if blocked do nothing
        if (rel.isPresent()) {
            switch (rel.get().getType()) {
                case PENDING:
                    invRel.get().setType(ERelationship.ACCEPTED);
                    repository.save(invRel.get());
                    return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.CREATED);
                case ACCEPTED:
                    return new ResponseEntity<>(new MessageResponse("You are already friends stop taxing our system"), HttpStatus.OK);
                case BLOCKED:
                    return new ResponseEntity<>(new MessageResponse("GG"), HttpStatus.OK);
                default:
                    return new ResponseEntity<>(new MessageResponse("SERVER ERROR _ DEFAULT RELATIONSHIP"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }


        // N t exists
        // if pending change to blocked
        // if approved change to blocked
        // if blocked do nothing
        if (invRel.isPresent()) {
            switch (invRel.get().getType()) {
                case PENDING:
                    invRel.get().setType(ERelationship.ACCEPTED);
                    repository.save(invRel.get());
                    return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.CREATED);
                case ACCEPTED:
                    return new ResponseEntity<>(new MessageResponse("You are already friends stop taxing our system"), HttpStatus.OK);
                case BLOCKED:
                    return new ResponseEntity<>(new MessageResponse("GG"), HttpStatus.OK);
                default:
                    return new ResponseEntity<>(new MessageResponse("SERVER ERROR _ DEFAULT RELATIONSHIP"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        try {
            repository.save(new Relationship(originator, recipient, ERelationship.BLOCKED));
        } catch (Exception e) {
            System.out.println("error" + e.getLocalizedMessage());
            return new ResponseEntity<>(new MessageResponse("Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.CREATED);
    }

}

