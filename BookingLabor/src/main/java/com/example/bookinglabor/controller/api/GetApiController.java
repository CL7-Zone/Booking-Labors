package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.mapper.ApplyMapper;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class GetApiController {


    private JobService jobService;
    CategoryJobService categoryJobService;
    HeaderService headerService;
    BookingService bookingService;
    PostService postService;

    LaborService laborService;
    CustomerService customerService;
    ApplyService applyService;
    RoleService roleService;
    JobDetailService jobDetailService;
    UserService userService;
    CityService cityService;


    @GetMapping("/header-api")
    public List<Header> header() {

        try {
            HashMap<String, String> map = new HashMap<>();

            return headerService.findAllHeaders();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception.getMessage());

            return null;
        }
    }

    @GetMapping("/admin/api/user")
    public List<UserAccount> user() {

        try {
            return userService.getAllUsersApi();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/job")
    public List<Job> job() {

        try {
            return jobService.getAllJobsApi();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/city")
    public List<City> city() {

        try {
            return cityService.getAllCitiesApi();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/category")
    public List<CategoryJob> category() {

        try {
            return categoryJobService.findAllCategoryJobs();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/job-detail")
    public List<JobDetail> jobDetail() {

        try {
            return jobDetailService.getAllJobDetailsApi();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/labor")
    public List<Labor> labor() {

        try {
            return laborService.getAllLaborApi();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/customer")
    public List<Customer> customer() {

        try {
            return customerService.getAllCustomersApi();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/booking")
    public List<Booking> booking() {

        try {
            return bookingService.findAllBookings();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }


    @GetMapping("/admin/api/role")
    public List<Role> role() {

        try {
            HashMap<String, String> map = new HashMap<>();

            return roleService.findAllRoles();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/post")
    public List<Post> post() {

        try {
            HashMap<String, String> map = new HashMap<>();

            return postService.getApiPosts();

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }

    @GetMapping("/admin/api/apply")
    public List<Apply> apply() {

        try {
            HashMap<String, String> map = new HashMap<>();

            return applyService.findAll().stream()
                    .map(ApplyMapper::mapToApplyApi)
                    .collect(Collectors.toList());

        }catch (Exception exception){

            System.out.println("ERROR: "+exception);

            return null;
        }
    }


}
