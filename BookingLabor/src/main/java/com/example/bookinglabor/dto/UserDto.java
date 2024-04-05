package com.example.bookinglabor.dto;


import com.example.bookinglabor.controller.component.EnumComponent;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    private Long id;
    @NotEmpty(message = "Email should not be empty!")
    private String email;
    @NotEmpty(message = "Password should not be empty!")
    private String password;
    private EnumComponent provider;
}
