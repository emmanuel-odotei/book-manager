package com.demo.bookmanager.config;

import com.demo.bookmanager.entity.RoleEnum;
import com.demo.bookmanager.exception.NotFoundException;
import com.demo.bookmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    private final UserRepository userRepository;
    
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
         http.csrf( AbstractHttpConfigurer::disable )
                 .authorizeHttpRequests( request -> request
                         .requestMatchers( HttpMethod.POST,"/auth/signup" ).permitAll()
                         .requestMatchers( HttpMethod.POST,"/auth/signin" ).permitAll()
                         .requestMatchers( "/error" ).permitAll()
                         .requestMatchers( HttpMethod.GET, "/authors" ).hasAnyAuthority( RoleEnum.USER.name(), RoleEnum.ADMIN.name())
                         .requestMatchers( HttpMethod.GET, "/authors/{authorId}" ).hasAnyAuthority( RoleEnum.ADMIN.name(), RoleEnum.USER.name() )
                         .requestMatchers( HttpMethod.PUT, "/authors/{authorId}").hasAuthority( RoleEnum.ADMIN.name() )
                         .requestMatchers( HttpMethod.POST, "/authors/create" ).hasAuthority( RoleEnum.ADMIN.name() )
                         .requestMatchers( HttpMethod.DELETE, "/authors/{authorId}").hasAuthority( RoleEnum.ADMIN.name() )
                         .requestMatchers( HttpMethod.GET, "/books" ).hasAnyAuthority( RoleEnum.ADMIN.name(), RoleEnum.USER.name() )
                         .requestMatchers( HttpMethod.GET, "/books/{bookId}" ).hasAnyAuthority( RoleEnum.ADMIN.name(), RoleEnum.USER.name() )
                         .requestMatchers( HttpMethod.PUT, "/books/{bookId}" ).hasAnyAuthority( RoleEnum.ADMIN.name(), RoleEnum.USER.name() )
                         .requestMatchers( HttpMethod.POST, "/books/create" ).hasAnyAuthority( RoleEnum.ADMIN.name(), RoleEnum.USER.name() )
                         .requestMatchers( HttpMethod.DELETE, "/books/{bookId}" ).hasAnyAuthority( RoleEnum.ADMIN.name(), RoleEnum.USER.name() )
                         .anyRequest()
                         .authenticated()
                 )
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy( STATELESS )
                )
                .authenticationProvider( authenticationProvider( ) )
                .addFilterBefore( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider () {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( userDetailsService () );
        authProvider.setPasswordEncoder( passwordEncoder() );
        return authProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    
    @Bean
    public UserDetailsService userDetailsService () {
        return username -> userRepository.findByEmail( username ).orElseThrow(() -> new NotFoundException( "User not found" ) );
    }
}
