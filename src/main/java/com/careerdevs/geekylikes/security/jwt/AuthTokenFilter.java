package com.careerdevs.geekylikes.security.jwt;

import com.careerdevs.geekylikes.security.services.UserDetailsServiceImpl;
import org.hibernate.boot.model.relational.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static Logger logger= LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt=parseJwt(request);
            if(jwt != null && jwtUtils.vali)
        }catch(){

        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth=request.getHeader("Authorization");
        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Baerer ")) {
            return headerAuth.trim().substring(7);
        }
    }
}
