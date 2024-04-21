package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoleService {

    List<Role> findAllRoles();

    Role findByName(String name);

    void deleteById(Long id);

    void saveAllDataFromExcel(MultipartFile file);
}
