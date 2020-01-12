package com.vuchobe.auth.filter;


import com.vuchobe.api.model.v2.UserAuthEntity;
import com.vuchobe.auth.config.JwtConfig;
import com.vuchobe.auth.model.UserSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FilterToken extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    public FilterToken(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(jwtConfig.getHeader());

        if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
            chain.doFilter(request, response);        // If not valid, go to the next filter.
            return;
        }

        String token = header.replace(jwtConfig.getPrefix(), "");

        try {  
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            List<LinkedHashMap> authoritiesMap = (List<LinkedHashMap>) claims.get("authorities");
            if (username != null) {
                if (authoritiesMap != null) {
                    List<SimpleGrantedAuthority> authorities = authoritiesMap
                            .stream().map(val -> {
                                LinkedHashMap val1 = val;
                                return new SimpleGrantedAuthority((String) val1.get("authority"));
                            }).collect(Collectors.toList());
                    UserSecurity userSecurity = new UserSecurity();
                    userSecurity.setId( parseLong(claims.get("id")));
                    if (claims.get("manId") != null) userSecurity.setManId(parseLong(claims.get("manId")));
                    if (claims.get("studentId") != null) userSecurity.setStudentId(parseLong(claims.get("studentId")));
                    if (claims.get("organizationUserId") != null) userSecurity.setOrganizationUserId(parseLong(claims.get("organizationUserId")));
                    userSecurity.setEmail(username);
                   
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            userSecurity, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }

        } catch (Exception e) {
            // In case of failure. Make sure it's clear; so guarantee user won't be authenticated
            SecurityContextHolder.clearContext();
            throw e;
        }

        // go to the next filter in the filter chain
        chain.doFilter(request, response);
    }
    
    private Long parseLong (Object value) {
        return Long.parseLong(value.toString());
    }
}
