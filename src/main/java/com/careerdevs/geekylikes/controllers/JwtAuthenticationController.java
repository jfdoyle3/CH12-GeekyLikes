package com.careerdevs.geekylikes.controllers;


import com.careerdevs.geekylikes.services.JwtUserDetailsService;
import com.careerdevs.geekylikes.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

//    @PostMapping("/api/authenticate")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequst)
}
