package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.model.Job;
import com.example.bookinglabor.service.JobService;
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

}
