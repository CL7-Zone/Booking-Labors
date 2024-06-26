package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.model.*;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@AllArgsConstructor
public class PostApiController {

    private JobService jobService;
    CityService cityService;
    RoleService roleService;
    UserService userService;
    HeaderService headerService;

    @PostMapping("/admin/api/job/save")
    public List<Job> storeJob(@RequestParam("file") MultipartFile file, RedirectAttributes flashMessage){

        if (file.isEmpty()) {

            System.out.println("Upload file, Please!");
            return jobService.getAllJobsApi();
        }
        try{
            this.jobService.saveAllDataFromExcel(file);
            System.out.println("Upload file successfully");

            return jobService.getAllJobsApi();
        }catch (Exception error){

            System.out.println(error);
            return null;
        }
    }


    @PostMapping("/admin/api/city/save")
    public List<City> storeCity(@RequestParam("file") MultipartFile file, RedirectAttributes flashMessage){

        if (file.isEmpty()) {

            System.out.println("Upload file, Please!");
            return cityService.getAllCitiesApi();
        }
        try{
            this.cityService.saveAllDataFromExcel(file);
            System.out.println("Upload file successfully");

            return cityService.getAllCitiesApi();
        }catch (Exception error){

            System.out.println(error);
            return null;
        }
    }

    @PostMapping("/admin/api/role/save")
    public List<Role> storeRole(@RequestParam("file") MultipartFile file, RedirectAttributes flashMessage){

        if (file.isEmpty()) {

            System.out.println("Upload file, Please!");
            return roleService.findAllRoles();
        }
        try{
            this.roleService.saveAllDataFromExcel(file);
            System.out.println("Upload file successfully");

            return roleService.findAllRoles();
        }catch (Exception error){

            System.out.println(error);
            return null;
        }
    }

    @PostMapping("/admin/api/role-user/save")
    public List<UserAccount> storeRoleUser(@RequestParam("name") String name,
                                           @RequestParam("email") String email){
        try{
            userService.createUserRole(roleService.findByName(name), email);
            System.out.println("save successfully");

            return userService.getAllUsersApi();
        }catch (Exception error){

            System.out.println(error);
            return null;
        }
    }


    @PostMapping("/admin/api/header/save")
    public List<Header> storeHeader(@RequestParam("content") String content,
                                    @RequestParam("name") String name,
                                    @RequestParam("type") String type){
        try{
            System.out.println("save successfully");
            headerService.saveData(content, name, type);

            return headerService.findAllHeaders();
        }catch (Exception error){

            System.out.println(error);
            return null;
        }
    }





}
