package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.RoleMapper;
import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.service.RoleService;
import com.example.bookinglabor.service.work.excel.UploadCity;
import com.example.bookinglabor.service.work.excel.UploadRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Override
    public Role findByName(String name) {
        return roleRepo.findByName(name);
    }

    @Override
    public void deleteById(Long id) {

        roleRepo.deleteById(id);
    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

        if(UploadRole.isValidExcelFile(file)){
            try {
                List<Role> roles = UploadRole.getRoleFromExcel(file.getInputStream());
                this.roleRepo.saveAll(roles);
                System.out.println("Insert data successfully");

            } catch (IOException e) {

                System.out.println("Error save !");
                throw new IllegalArgumentException(e);
            }
        }
    }
}
