package com.example.bookinglabor.service;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public interface UserService {


    void saveUser(UserDto userDto);

    void saveOtherUser(String  email, EnumComponent provider);

    UserAccount findByEmail(String email);
    UserAccount findByEmailAndProvider(String email, EnumComponent provider);

    void updateUserRole(Role role);

    void createUserRoleCustomer(Role role);
    void createUserRoleLabor(Role role);

    Collection<? extends GrantedAuthority> getUpdatedAuthorities(Authentication authentication);

}
