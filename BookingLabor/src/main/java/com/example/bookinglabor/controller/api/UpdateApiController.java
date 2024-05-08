package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.service.RoleService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UpdateApiController {

    RoleService roleService;
    UserService userService;

    @PostMapping("/admin/api/update/role-user")
    public List<UserAccount> updateRoleUser(@RequestParam("name") String name,
                                           @RequestParam("email") String email){
        try{
            userService.updateUserRole(roleService.findByName(name), email);
            System.out.println("update successfully");

            return userService.getAllUsersApi();
        }catch (Exception error){

            System.out.println(error);
            return null;
        }
    }


    @PostMapping("/admin/api/update/active-user")
    public List<UserAccount> updateActiveUser(@RequestParam("active_name") String active_name,
                                            @RequestParam("email") String email){
        try{

            userService.updateActiveUser(active_name, email);
            System.out.println("update successfully");

            return userService.getAllUsersApi();
        }catch (Exception error){

            System.out.println(error);
            return null;
        }
    }

}
