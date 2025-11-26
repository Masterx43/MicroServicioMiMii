package com.mimi.ReservaService.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collections;

public class JwtUserAuthentication extends AbstractAuthenticationToken {

    private final String email;
    private final Integer rol;

    public JwtUserAuthentication(String email, Integer rol) {
        super(Collections.emptyList());
        this.email = email;
        this.rol = rol;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return rol;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}
