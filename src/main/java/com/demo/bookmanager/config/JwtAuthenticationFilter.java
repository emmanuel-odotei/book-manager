package com.demo.bookmanager.config;

import com.demo.bookmanager.entity.User;
import com.demo.bookmanager.repository.UserRepository;
import com.demo.bookmanager.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JWTService jwtService;
    
    private final UserRepository userRepository;
    
    @Override
    protected void doFilterInternal (@NotNull HttpServletRequest request,
                                     @NotNull HttpServletResponse response,
                                     @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader( "Authorization" );
        final String jwt;
        final String userEmail;
        
        if ( authHeader == null || !authHeader.startsWith( "Bearer " ) ) {
            filterChain.doFilter( request, response );
            return;
        }
        
        jwt = authHeader.substring( 7 );
        
        userEmail = jwtService.extractUsername( jwt );
        
        if ( !userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null ) {
            User user = userRepository.findByEmail( userEmail ).get();
            
            if ( jwtService.isTokenValid( jwt, user ) ) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                
                authToken.setDetails( new WebAuthenticationDetailsSource().buildDetails( request ) );
                
                SecurityContextHolder.getContext().setAuthentication( authToken );
            }
            
        }
        filterChain.doFilter( request, response );
    }
    
    @Override
    protected boolean shouldNotFilter (HttpServletRequest request) {
        return request.getServletPath().equals( "/auth/**" );
    }
}
