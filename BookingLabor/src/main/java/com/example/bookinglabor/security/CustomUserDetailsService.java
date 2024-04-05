package com.example.bookinglabor.security;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.mapper.RoleMapper;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService, CustomUserService {

    private final UserRepo userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepository) {

        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("Detail email: "+email);

        UserAccount user = userRepository.findByEmailAndProvider(email, EnumComponent.SIMPLE);

        if(user != null) {

            return new User(
                        user.getEmail(),
                        user.getPassword(),
                        RoleMapper.mapRolesToAuthorities(user.getRoles())
            );

        } else {

            throw new UsernameNotFoundException("Invalid username or password!!!");
        }
    }

    @Override
    public OAuth2AuthenticationToken filterUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) {

        if(oAuth2AuthenticationToken != null){
            System.out.println(oAuth2AuthenticationToken.getPrincipal().getAttributes());
        }
        assert oAuth2AuthenticationToken != null;
        System.out.println("user google " + oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email"));

        return oAuth2AuthenticationToken;
    }
}
