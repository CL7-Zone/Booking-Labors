package com.example.bookinglabor.dto;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AuthResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    Long id;
    String accessToken;
    String tokenType = "Bearer ";
    Object object;

    public AuthResponseDto(Long id, String accessToken, Object object) {
        this.id = id;
        this.accessToken = accessToken;
        this.object = object;
    }


}
