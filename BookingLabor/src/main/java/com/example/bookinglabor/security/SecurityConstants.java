package com.example.bookinglabor.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SecurityConstants {


    public static final  long JWT_EXPIRATION = 300000;
    public static final  String JWT_SECRET = "secret";
    public static final int max_record = 5;
    public static final String keyEncrypt = "0123456789abcdef";

}
