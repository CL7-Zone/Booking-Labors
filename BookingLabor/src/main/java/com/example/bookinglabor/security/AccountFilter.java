package com.example.bookinglabor.security;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Component
public class AccountFilter extends OncePerRequestFilter {


    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            if(userService.checkActiveUser()){
                if (!response.isCommitted()) {
                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, String> responseBody = new HashMap<>();
                    responseBody.put("error", "Account is locked");
                    objectMapper.writeValue(response.getWriter(), responseBody);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
