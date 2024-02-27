package com.bits.bits.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_platform")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlatformModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int platformID;

    @NotBlank(message = "Attribute platformname cannot be null")
    private String platformName;

}
