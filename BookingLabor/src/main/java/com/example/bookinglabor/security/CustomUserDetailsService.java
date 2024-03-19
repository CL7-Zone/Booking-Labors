package com.example.bookinglabor.security;

import com.example.bookinglabor.mapper.RoleMapper;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private final UserRepo userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepository) {

        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserAccount user = userRepository.findByEmail(email);

        if(user != null) {

            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    RoleMapper.mapRolesToAuthorities(user.getRoles())
            );

        } else {

            throw new UsernameNotFoundException("Invalid username or password!");
        }
    }
}
