package com.AutoHub.autohub_backend.SecurityConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtServiceObj;

    @Autowired
    ApplicationContext context;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String api=request.getRequestURI();
        String token=null;
       // String userEmailId=null;
        Long userId=null;
        System.out.println("JWT Filter is Hitted\nAuthHeaders:"+authHeader+"\nAPI:"+api);
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token=authHeader.substring(7);
            jwtServiceObj.extractBegin(token);
            userId=jwtServiceObj.extractUserId(token);
            
            System.out.println("JwtFilter Token Extract:"+userId);
        }
        if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
        	UserPrincipleDetailsImpl userDetails = (UserPrincipleDetailsImpl) context.getBean(UserDetailsServiceImpl.class).loadUserByUserId(userId);

            if(jwtServiceObj.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        String token=null;
//        String userEmailId=null;
//
//        if(authHeader!=null && authHeader.startsWith("Bearer "))
//        {
//            token=authHeader.substring(7);
//            userEmailId=jwtServiceObj.extractUserName(token);
//        }
//        if(userEmailId!=null && SecurityContextHolder.getContext().getAuthentication()==null)
//        {
//            UserDetails userDetails = context.getBean(UserDetailsServiceImpl.class).loadUserByUsername(userEmailId);
//
//            if(jwtServiceObj.validateToken(token, userDetails)){
//                UsernamePasswordAuthenticationToken authToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request,response);
//    }
}
