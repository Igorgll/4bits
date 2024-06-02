package com.bits.bits.security;

import com.bits.bits.model.EstoquistaModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EstoquistaDetails implements UserDetails {

    private final EstoquistaModel estoquista;

    public EstoquistaDetails(EstoquistaModel estoquista) { this.estoquista = estoquista; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(estoquista.getGroup()));
    }

    @Override
    public String getPassword() {
        return estoquista.getPassword();
    }

    @Override
    public String getUsername() {
        return estoquista.getEmail();
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
        return estoquista.isActive();
    }
}
