package com.example.bookinglabor.mapper;

import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.UserAccount;

public class UserMapper {


    public static UserAccount mapToUser(UserAccount user) {

        UserAccount userDto = UserAccount.builder()
        .id(user.getId())
        .roles(user.getRoles())
        .build();

        if (userDto != null) {

            return userDto;

        }else{

            System.out.println("" + null);

            return null;
        }
    }
}
