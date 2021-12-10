package com.careerdevs.geekylikes.security.jwt;

import com.careerdevs.geekylikes.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${geeykylikes.app.jwtSecret}")
    private String jwtSecret;

//    @Value("${geekylikes.app.jwtExpirationMs}")
//    private int jwtExpiration;


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalied JWT Signature {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT Cliams string is empty {}", e.getMessage());
        }
        return false;
    }

    public String generateJwtToken(Authentication authentication){
        UserDetailsServiceImpl userPrincipal=(UserDetailsServiceImpl)  authentication.getPrincipal();

        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime()+jwtExpiration));
    }

}
