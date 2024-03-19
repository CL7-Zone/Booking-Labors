package com.example.bookinglabor.service;

import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public interface UserService {


    void saveUser(UserDto userDto);

    UserAccount findByEmail(String email);

    void updateUserRole(Role role);

    void createUserRoleCustomer(Role role);
    void createUserRoleLabor(Role role);

}
