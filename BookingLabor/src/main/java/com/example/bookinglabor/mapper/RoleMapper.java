package com.example.bookinglabor.mapper;
import com.example.bookinglabor.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {


    public static Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {

        return roles.stream().map(
                role -> new SimpleGrantedAuthority("ROLE_" + role
                .getName()))
                .collect(Collectors
                .toList());
    }



}
