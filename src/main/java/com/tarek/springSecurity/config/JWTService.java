package com.tarek.springSecurity.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secret;

     private SecretKey getSingingKey()
     {
         return Keys.hmacShaKeyFor(
                 Decoders.BASE64.decode(secret)
         );
     }
    private String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis()+1000*60*60)
                )
                .signWith(getSingingKey())
                .compact();


    };
    public String extractUsername(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSingingKey())
                .build()
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    };


    public boolean isValidToken(String toke)
    {
      try {
          Jwts.parserBuilder().setSigningKey(getSingingKey()).build().parseClaimsJwt(toke);
          return true;
      }
      catch (JwtException | IllegalArgumentException e) {
          return false;
      }
    }


}
