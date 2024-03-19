package com.example.bookinglabor.controller.component;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserControllerAdvice {


    @ModelAttribute
    public void addUserRoleToModel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            List<String> roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
            model.addAttribute("userRoles", roles);
        }
    }
}
