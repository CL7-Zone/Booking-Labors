package com.example.bookinglabor.service.work;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;


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
        user.setProvider(EnumComponent.SIMPLE);
        Role role = roleRepo.findByName("USER");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveOtherUser(String email, EnumComponent provider) {
        UserAccount user = new UserAccount();
        user.setEmail(email);
        user.setProvider(provider);

        Role role = roleRepo.findByName("USER");

        if (role != null) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }


    @Override
    public UserAccount findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserAccount findByEmailAndProvider(String email, EnumComponent provider) {

        return userRepository.findByEmailAndProvider(email, provider);
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
    public Collection<? extends GrantedAuthority> getUpdatedAuthorities(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserAccount user = userRepository.findByEmail(userDetails.getUsername());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
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
