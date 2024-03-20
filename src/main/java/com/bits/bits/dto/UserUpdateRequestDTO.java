package com.bits.bits.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserUpdateRequestDTO {
    private String name;
    private String password;
    private String group;
}
