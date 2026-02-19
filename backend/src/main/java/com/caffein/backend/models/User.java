package com.caffein.backend.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "USERS")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private LocalDate dateOfBirth;

    private boolean enabled;

    private boolean locked;

    private boolean credentialsExpired;

    private boolean emailVerified;

    private boolean phoneVerified;

    private LocalDateTime createdDated;

    private LocalDateTime lastModifiedDate;

    @ManyToMany
    @JoinTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USER_ID")
    }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")
    })
    private List<Role> role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (org.springframework.util.CollectionUtils.isEmpty(this.role)) {
            return List.of();
        }

        return this.role
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

}
