package com.example.bookinglabor.model.sessionObject;


import com.example.bookinglabor.controller.component.EnumComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class AuthObject implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    String email;
    String password;
    String token;
    boolean auth;



}
