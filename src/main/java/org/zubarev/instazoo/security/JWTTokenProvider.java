package org.zubarev.instazoo.security;

import io.jsonwebtoken.*;
import jakarta.persistence.CollectionTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.zubarev.instazoo.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    public static final Logger log= LoggerFactory.getLogger(JWTTokenProvider.class);

    public String generateToken(Authentication authentication){
        User user= (User) authentication.getPrincipal();
        Date now=new Date(System.currentTimeMillis());
        Date expiryDate=new Date(now.getTime()+SecurityConstants.EXPIRATION_TIME);

        String userId=Long.toString(user.getId());
        Map<String,Object> claimsMap=new HashMap<>();
        claimsMap.put("id",userId);
        claimsMap.put("username",user.getEmail());
        claimsMap.put("firstname",user.getName());
        claimsMap.put("lastname", user.getLastname());
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.ES512,SecurityConstants.SECRET)
                .compact();
    }

    /**
     *
     *Логика для валидации- если все правильно,
     * то возвращаем true, если нет логгируем исключение
     * и возвращаем false
     */
    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        }catch (SignatureException | MalformedJwtException |
                ExpiredJwtException |
        UnsupportedJwtException | IllegalArgumentException exception){
            log.error(exception.getMessage());
            return false;
        }
    }

    public Long getUserIdFromToken(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJwt(token)
                .getBody();
        String id= (String) claims.get("id");
        return Long.parseLong(id);
    }

}
