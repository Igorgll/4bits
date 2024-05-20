package com.bits.bits.security;

import java.util.Collection;
import java.util.List;

import com.bits.bits.util.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bits.bits.model.AdminModel;

public class AdminDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;

    private UserRoles userRoles;

    private List<GrantedAuthority> authorities;

    public AdminDetails(AdminModel user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
    }

    public AdminDetails() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userRoles == UserRoles.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
}
