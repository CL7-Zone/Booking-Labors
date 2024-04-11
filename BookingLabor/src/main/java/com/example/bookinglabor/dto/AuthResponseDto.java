package com.example.bookinglabor.dto;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class AuthResponseDto {

    Long id;
    String accessToken;
    String tokenType = "Bearer ";
    Object object;

    public AuthResponseDto(Long id, String accessToken, Object object) {
        this.id = id;
        this.accessToken = accessToken;
        this.object = object;
    }

    //    public AuthResponseDto(Long id, String accessToken, UserDetails userDetails) {
//        this.id = id;
//        this.accessToken = accessToken;
//        this.userDetails = userDetails;
//    }
}
