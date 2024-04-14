package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.RoleMapper;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RoleWork implements RoleService {

    RoleRepo roleRepo;

    @Override
    public List<Role> findAllRoles() {

        return roleRepo.findAll().stream()
                .map(RoleMapper::mapToRole)
                .collect(Collectors.toList());
    }
}
