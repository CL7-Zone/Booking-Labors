package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.model.City;
import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.service.CityService;
import com.example.bookinglabor.service.JobService;
import com.example.bookinglabor.service.RoleService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class DeleteApiController {

    RoleService roleService;
    JobService jobService;
    CityService cityService;
    UserService userService;

    @PostMapping("/admin/api/delete/role")
    private List<Role> deleteRole(@RequestParam("role_id") Long role_id){

        try{
            roleService.deleteById(role_id);
            System.out.println("Delete " + role_id);
            System.out.println("Delete role successfully");

            return roleService.findAllRoles();
        }catch (Exception exception){
            System.out.println("Delete error: " + exception);
            return null;
        }
    }

    @PostMapping("/admin/api/delete/job")
    private List<Job> deleteJob(@RequestParam("job_id") Long job_id){

        try{
            jobService.deleteById(job_id);
            System.out.println("Delete " + job_id);
            System.out.println("Delete job successfully");

            return jobService.getAllJobsApi();
        }catch (Exception exception){
            System.out.println("Delete error: " + exception);
            return null;
        }
    }


    @PostMapping("/admin/api/delete/city")
    private List<City> deleteCity(@RequestParam("city_id") Long city_id){

        try{
            cityService.deleteById(city_id);
            System.out.println("Delete " + city_id);
            System.out.println("Delete job successfully");

            return cityService.getAllCitiesApi();
        }catch (Exception exception){
            System.out.println("Delete error: " + exception);
            return null;
        }
    }

    @PostMapping("/admin/api/delete/user-role")
    private List<UserAccount> deleteUserRole(@RequestParam("email") String email,
                                             @RequestParam("name") String name){

        System.out.println("role name: "+name);

        try{
            userService.deleteUserRole(roleService.findByName(name), email);
            System.out.println("Delete role user " + email);
            System.out.println("Delete role user successfully");

            return userService.getAllUsersApi();
        }catch (Exception exception){
            System.out.println("Delete error: " + exception);
            return null;
        }
    }


}
