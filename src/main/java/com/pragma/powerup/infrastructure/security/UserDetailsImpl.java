package com.pragma.powerup.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {

    private final Map<String, String> userData;

    public UserDetailsImpl(Map<String, String> userDate) {
        this.userData = userDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userData.get("role").toUpperCase()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userData.get("password");
    }

    @Override
    public String getUsername() {
        return this.userData.get("email");
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName(){
        return this.userData.get("name");
    }
}
