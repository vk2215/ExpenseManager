package com.example.moneymanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.moneymanager.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;


@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter{
        private final UserDetailsService userDetailsService;
        private final JwtTokenUtil jwtTokenUtil;
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                        FilterChain filterChain) throws ServletException, IOException {
                // TODO Auto-generated method stub

                final String authHeader = request.getHeader("Authorization");
                String email = null;
                String jwt = null;
               
                if(authHeader != null && authHeader.startsWith("Bearer")){
                      jwt = authHeader.substring(7);
                      email = jwtTokenUtil.extractUsername(jwt);
                }
                if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){
                        UserDetails userdetails =this.userDetailsService.loadUserByUsername(email);
                        if(jwtTokenUtil.validateToken(jwt, userdetails)){
                                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                }
                filterChain.doFilter(request, response);
        }
}
