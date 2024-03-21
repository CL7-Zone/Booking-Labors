package com.example.bookinglabor.controller;

import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.JWTGeneratorToken;
import com.example.bookinglabor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class DisplayAuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private UserRepo userRepository;
    private RoleRepo roleRepository;
    private PasswordEncoder passwordEncoder;
    private final JWTGeneratorToken jwtGenerator;

    @Autowired
    public DisplayAuthController(UserService userService, AuthenticationManager authenticationManager, UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder, JWTGeneratorToken JWTGeneratorToken) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = JWTGeneratorToken;
    }


    @GetMapping("/login")
    public String index(){

        return "auth/login";
    }


    @PostMapping("/register/save")
    public String save(@Valid @ModelAttribute("user") UserDto user, BindingResult res, Model model){

        UserAccount existingUserEmail = userService.findByEmail(user.getEmail());

        if(existingUserEmail != null && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()){

            return "redirect:/register?fail";
        }

        if(existingUserEmail != null){

            return "redirect:/register?fail";
        }

        if(res.hasErrors()){

            model.addAttribute("user", user);

            return "auth/register";
        }

        userService.saveUser(user);

        return "redirect:/login";
    }

}
