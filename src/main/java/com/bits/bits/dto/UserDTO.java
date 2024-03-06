package com.bits.bits.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDTO {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private boolean admin;

    @NotNull
    private boolean isActive;
}