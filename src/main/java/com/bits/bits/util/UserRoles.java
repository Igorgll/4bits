package com.bits.bits.util;

public enum UserRoles {

    ADMIN("admin"),
    ESTOQUISTA("estoquista"),
    USER("user");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
