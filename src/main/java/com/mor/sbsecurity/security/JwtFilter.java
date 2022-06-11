package com.mor.sbsecurity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
        String authorizationHeader = request.getHeader("Authorization");
        String userName = null;
        String token = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);

            try {
                userName = Jwts.parser()
                        .setSigningKey("secret")
                        .parseClaimsJws(token)
                        .getBody().getSubject();

                UsernamePasswordAuthenticationToken authentication = null;

                if(userName != null) {
                    authentication = new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>());
                }
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch(Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(403);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            filterChain.doFilter(request, response);
        }
    }
}
