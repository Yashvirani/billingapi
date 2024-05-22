package com.billing.software.utils;

import com.billing.software.Dao.CustomerDao;
import com.billing.software.Model.Authority;
import com.billing.software.Model.Customer;
import com.billing.software.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class JwtUtils {

    @Autowired
    private CustomerDao customerDao;

    private final static SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

    private String populateAuthorities(Set<Authority> collection) {
        Set<String> grantedAuthorities = new HashSet<>();
        for (Authority authority : collection) {
            grantedAuthorities.add(authority.getName());
        }
        return String.join(",", grantedAuthorities);
    }

    public String generateJwt(String email){
        List<Customer> customer = customerDao.findByEmail(email);
        String authorities = populateAuthorities(customer.get(0).getAuthorities());
        String jwt = Jwts.builder()
                .setSubject(email)
                .setIssuer("billing.com")
                .claim("email",email)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 30000000))
                .signWith(key)
                .compact();

        return jwt;
    }

    public boolean validateJwt(String token){
        if(getEmail(token) != null && tokenNotExpired(token)){
            return true;
        }

        return false;
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    };

    public String getEmail(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean tokenNotExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date(System.currentTimeMillis()));
    }

}
