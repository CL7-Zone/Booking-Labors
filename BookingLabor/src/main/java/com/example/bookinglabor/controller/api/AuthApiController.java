package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.dto.AuthResponseDto;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.JWTGeneratorToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class AuthApiController {


    private final AuthenticationManager authenticationManager;
    private UserRepo userRepository;
    private RoleRepo roleRepository;
    private PasswordEncoder passwordEncoder;
    private final JWTGeneratorToken JWTGeneratorToken;


    @Autowired
    public AuthApiController(AuthenticationManager authenticationManager, UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder, JWTGeneratorToken JWTGeneratorToken) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.JWTGeneratorToken = JWTGeneratorToken;
    }

    @PostMapping("/auth-login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserDto userDto){

        System.out.println(userDto.getEmail());

        Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
        userDto.getEmail(),
        userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = JWTGeneratorToken.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

}
