package com.example.bookinglabor.service.work;

import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class UserWork implements UserService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepo;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserWork(UserRepo userRepository, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


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
}
