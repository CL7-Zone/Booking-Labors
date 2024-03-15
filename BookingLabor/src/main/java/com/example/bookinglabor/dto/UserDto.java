package com.example.bookinglabor.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {

    private Long id;
    @NotEmpty(message = "Email should not be empty!")
    private String email;
    @NotEmpty(message = "Password should not be empty!")
    private String password;
}
