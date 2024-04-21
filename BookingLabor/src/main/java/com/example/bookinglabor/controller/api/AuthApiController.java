package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.AuthResponseDto;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.mapper.RootMapper;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.AuthObject;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.CustomUserDetailsService;
import com.example.bookinglabor.security.JWTAuthenticationFilter;
import com.example.bookinglabor.security.JWTGeneratorToken;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
public class AuthApiController {


    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private UserService userService;
    private RoleRepo roleRepository;
    private PasswordEncoder passwordEncoder;
    private final JWTGeneratorToken JWTGeneratorToken;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthApiController(AuthenticationManager authenticationManager, UserRepo userRepository,
                             RoleRepo roleRepository, PasswordEncoder passwordEncoder,
                             JWTGeneratorToken JWTGeneratorToken,
                             UserService userService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.JWTGeneratorToken = JWTGeneratorToken;
        this.userService = userService;
    }

//    @GetMapping("/user")
//    public Map<String, Object> index(OAuth2AuthenticationToken oAuth2AuthenticationToken){
//
//        String email = (String) oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email");
//        UserAccount existingUserEmail = userRepository.findByEmail(email);
//
//        if(existingUserEmail == null || existingUserEmail.getEmail() == null
//                || existingUserEmail.getEmail().isEmpty()){
//
//            UserDto userDto = new UserDto();
//            userDto.setEmail(email);
//            userDto.setPassword(email);
//            userService.saveUser(userDto);
//        }
//
//        Map<String, Object> objectData = oAuth2AuthenticationToken.getPrincipal().getAttributes();
//
//        for(Map.Entry<String, Object> entry : objectData.entrySet()){
//
//            System.out.println(entry.getValue());
//        }
//
//        if (oAuth2AuthenticationToken.isAuthenticated()) {
//            return objectData;
//        } else {
//            return Collections.singletonMap("error", "OAuth2 authentication failed");
//        }
//    }

    @PostMapping("/logout")
    String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

        logoutHandler.logout(request, response, authentication);

        return "redirect:/login?logout";
    }

    @CrossOrigin
    @PostMapping("/api/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserDto userDto,
                                                 HttpServletRequest request,
                                                 HttpSession session){

        Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JWTGeneratorToken.generateToken(authentication);
        Object principal = authentication.getPrincipal();
        Long userId = userService.findByEmailAndProvider(authentication.getName(), EnumComponent.SIMPLE).getId();
        @SuppressWarnings("unchecked")
        List<AuthResponseDto> authResponse = (List<AuthResponseDto>) request.getSession().getAttribute("authResponse");
        AuthResponseDto auth = new AuthResponseDto(userId, token, principal);

        try{
//            userService.saveDataToSessionStore(request, auth);
            System.out.println(authentication);
            System.out.println("Principal: "+authentication.getPrincipal());
        }catch (Exception error){
            System.out.println("ERROR LOGIN API: "+error.getMessage());
        }
        return new ResponseEntity<>(auth, HttpStatus.OK);
    }

}
