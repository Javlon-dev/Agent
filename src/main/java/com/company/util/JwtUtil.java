package com.company.util;

import com.company.dto.JwtDTO;
import com.company.enums.Role;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {

    private final static String secretKey = "agent";


    public static String encode(String id, Role role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(id);
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000))); // for an hour
        jwtBuilder.claim("role", role.name());
        jwtBuilder.setIssuer("Agent Production");

        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String jwt) {

        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();
        String role = (String) claims.get("role");
        return new JwtDTO(id, Role.valueOf(role));

    }

}
