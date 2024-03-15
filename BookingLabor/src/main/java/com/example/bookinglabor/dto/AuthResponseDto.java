package com.example.bookinglabor.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    String accessToken;
    String tokenType = "Bearer ";

    public AuthResponseDto(String accessToken){

        this.accessToken = accessToken;
    }

}
