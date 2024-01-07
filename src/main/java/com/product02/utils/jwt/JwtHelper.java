package com.product02.utils.jwt;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
@Component
@Slf4j
public class JwtHelper {
    @Value("${jwt.secrect.key}")
    private String KEYSECR;
    @Value("${jwt.expiration}")
    private int EXPIRATION;
    public String generateToken(String data){
        Date now = new Date();
        Date dataExpired = new Date(now.getTime()+EXPIRATION);
        String token = Jwts.builder()
                .setSubject(data)
                .setIssuedAt(now)
                .setExpiration(dataExpired)
                .signWith(SignatureAlgorithm.HS512,KEYSECR)
                .compact();
        return token;
    }
    public Claims decodeToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(KEYSECR)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(KEYSECR).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;
    }
}
