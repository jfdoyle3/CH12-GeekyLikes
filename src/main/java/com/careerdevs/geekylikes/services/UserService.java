package com.careerdevs.geekylikes.services;

import com.careerdevs.geekylikes.entities.auth.User;
import com.careerdevs.geekylikes.repositories.UserRepository;

import com.careerdevs.geekylikes.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    // getById
    // authenticateUser
    // getCurrentUser
    // getLoggedinUser

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<User> currentUser = userRepository.findById(userDetails.getId());

        if(currentUser.isEmpty()) {
            return null;
        }

        return currentUser.get();

    }

}
