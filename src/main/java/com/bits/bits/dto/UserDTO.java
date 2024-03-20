package com.bits.bits.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
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
