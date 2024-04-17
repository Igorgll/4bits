package com.bits.bits.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private long userId;

    private String name;

    private String email;

    private String cpf;

    private String password;

    private String group;

    private boolean isActive;
}
