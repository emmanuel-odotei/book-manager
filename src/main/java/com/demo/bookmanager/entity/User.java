package com.demo.bookmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @SequenceGenerator( name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    
    @NotNull
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    
    @NotNull
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    
    @NotNull
    @NotBlank(message = "Email cannot be blank")
    @Column(unique = true)
    private String email;
    
    @NotNull
    @NotBlank(message = "Password cannot be blank")
    @Column(length = 60)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities () {
        return List.of(new SimpleGrantedAuthority( role.name() ) );
    }
    
    @Override
    public String getUsername () {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired () {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked () {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired () {
        return true;
    }
    
    @Override
    public boolean isEnabled () {
        return true;
    }
}
