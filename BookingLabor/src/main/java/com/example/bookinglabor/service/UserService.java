package com.example.bookinglabor.service;

import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.UserAccount;

public interface UserService {


    void saveUser(UserDto userDto);

    UserAccount findByEmail(String email);
}
