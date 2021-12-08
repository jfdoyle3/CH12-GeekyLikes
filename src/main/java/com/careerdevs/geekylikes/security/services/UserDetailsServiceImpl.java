package com.careerdevs.geekylikes.security.services;

import com.careerdevs.geekylikes.entities.auth.User;
import com.careerdevs.geekylikes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user=userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(("User Not Found: "+username)));

        return UserDetailsImpl.build(user);
    }


}
