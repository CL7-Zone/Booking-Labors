package com.example.bookinglabor.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil{

    public static String getSessionUser() {

        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            return authentication.getName();
        }

        return null;
    }
}
