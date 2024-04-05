package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.mapper.CommentSkillMapper;
import com.example.bookinglabor.model.CommentSkill;
import com.example.bookinglabor.model.JobDetail;
import com.example.bookinglabor.repo.CommentSkillRepo;
import com.example.bookinglabor.repo.JobDetailRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserSkillController {


    private JobDetailRepo jobDetailRepo;
    private CommentSkillRepo commentSkillRepo;


    @PostMapping(value = {"/save/comment_skill/{job_detail_id}"})
    String save(@PathVariable Long job_detail_id, @RequestParam("content") String content){

        System.out.println(job_detail_id);
        CommentSkill commentSkill = new CommentSkill();
        commentSkill.setContent(content);
        Optional<JobDetail> jobDetail = jobDetailRepo.findById(job_detail_id);
        jobDetail.ifPresent(commentSkill::setJobDetail);
        commentSkillRepo.save(commentSkill);

        return "redirect:/your-job";
    }


}
