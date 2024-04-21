package com.example.bookinglabor.mapper;

import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Report;
import com.example.bookinglabor.model.UserAccount;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserAccount mapToUserApi(UserAccount user) {

        UserAccount userDto = UserAccount.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .provider(user.getProvider())
                .roles(user.getRoles().stream()
                .map(RoleMapper::mapToRole)
                .collect(Collectors.toList()))
                .posts(user.getPosts().stream()
                .map(PostMapper::mapToPostApi)
                .collect(Collectors.toList()))
                .labors(user.getLabors().stream()
                .map(LaborMapper::mapToLaborApi)
                .collect(Collectors.toList()))
                .customers(user.getCustomers().stream()
                .map(CustomerMapper::mapToCustomerApi)
                .collect(Collectors.toList()))
                .applies(user.getApplies().stream()
                .map(ApplyMapper::mapToApplyApi)
                .collect(Collectors.toList()))
                .headers(user.getHeaders().stream()
                .map(HeaderMapper::mapToHeader)
                .collect(Collectors.toList()))
                .reports(new ArrayList<>(user.getReports()))
                .build();

        if (userDto != null) {

            return userDto;

        }else{

            System.out.println("" + null);

            return null;
        }
    }

    public static UserAccount mapToUser(UserAccount user) {

        UserAccount userDto = UserAccount.builder()
        .id(user.getId())
        .roles(user.getRoles())
        .labors(user.getLabors())
        .build();

        if (userDto != null) {

            return userDto;

        }else{

            System.out.println("" + null);

            return null;
        }
    }
}
