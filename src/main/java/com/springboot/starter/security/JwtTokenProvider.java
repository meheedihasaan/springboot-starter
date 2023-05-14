package com.springboot.starter.security;

import com.springboot.starter.SpringBootStarterApplication;
import com.springboot.starter.constants.SecurityConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

import static com.springboot.starter.SpringBootStarterApplication.LOGGER;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        UserDetails  userDetails = (UserDetails) authentication.getPrincipal();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstant.JWT_TOKEN_EXPIRATION_TIME);

        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(getSecretKey(), SecurityConstant.SIGNATURE_ALGORITHM).compact();

    }

    public String generateToken(UserDetails userDetails) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstant.JWT_TOKEN_EXPIRATION_TIME);

        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(getSecretKey(), SecurityConstant.SIGNATURE_ALGORITHM)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(getSecretKey(), SecurityConstant.SIGNATURE_ALGORITHM)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            getJwtParser().parseClaimsJws(token);
            return true;
        }
        catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token.");
        }
        catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token.");
        }
        catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token.");
        }
        catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }

        return false;
    }

    public String getUsernameFromJwt(String token) {
        return getJwtParser().parseClaimsJws(token).getBody().getSubject();
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build();
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SecurityConstant.SECRET_KEY));
    }

}
