package com.bits.bits.util;

public enum UserRoles {

    ADMIN("ROLE_ADMIN"),
    ESTOQUISTA("ROLE_ESTOQUISTA"),
    USER("ROLE_USER");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
