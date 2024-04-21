package com.example.bookinglabor.security.oauth2;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.security.CustomUserDetailsService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Autowired
    private UserService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Optional<Object> emailOptional = Optional.ofNullable(oAuth2User.getAttribute("email"));
        String email = emailOptional.map(Object::toString).orElse("No email available!!!");
        System.out.println("Google email: " + email);
        UserAccount userAccount  = userService.findByEmailAndProvider(email, EnumComponent.GOOGLE);
        if(userAccount == null){
            userService.saveOtherUser(email, EnumComponent.GOOGLE);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
