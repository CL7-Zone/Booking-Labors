package com.example.bookinglabor.service.work;

import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class UserWork implements UserService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepo;

    PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {

        UserAccount user = new UserAccount();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepo.findByName("USER");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    @Override
    public UserAccount findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public void createUserRoleCustomer(Role role) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userRepository.findByEmail(sessionEmail);

        if(user != null){

            user.getRoles().add(role);
            userRepository.save(user);
        }

    }

    @Override
    public void createUserRoleLabor(Role role) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userRepository.findByEmail(sessionEmail);

        if(user != null){

            user.getRoles().add(role);
            userRepository.save(user);
        }
    }


    @Override
    public void updateUserRole(Role role) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userRepository.findByEmail(sessionEmail);

        if(user != null){

            List<Role> newRoles = new ArrayList<>();
            newRoles.add(role);
            user.setRoles(newRoles);
            userRepository.save(user);
        }

    }



}
