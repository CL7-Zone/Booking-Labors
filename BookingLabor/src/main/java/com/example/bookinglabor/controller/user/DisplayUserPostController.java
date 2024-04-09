package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.mapper.ApplyMapper;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.repo.PostRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.core.io.ResourceLoader;
@Controller
@AllArgsConstructor
public class DisplayUserPostController {

    private LaborService laborService;
    private CustomerService customerService;
    private JobService jobService;
    private CategoryJobService categoryJobService;
    private UserService userService;
    private UserRepo userRepo;
    private PostRepo postRepo;
    private JobDetailService jobDetailService;
    private CityService cityService;
    private LaborRepo laborRepo;
    private JobDetailRepo jobDetailRepo;
    private PostService postService;
    private ApplyService applyService;
    private SendMailService sendMailService;
    private VonageSendSmsService vonageSendSmsService;
    interface CountAppliesByUserAndPostFunc extends BiFunction<Long, Long, Integer> {

    }

    @GetMapping(value = {"/post-manager"})
    private String index(Model model){

        Long user_id = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE).getId();
        List<Post> userPosts = postService.findPostByUserAccountId(user_id);
        //Lambda function vế trước là tham số đầu vào
        // vế sau là tham số đầu ra

        for (Post post : userPosts){
            for(Apply apply : post.getApplies()){
                System.out.println(apply.getUserAccount().getId());
            }
        }
        Function <Long, List<Apply>> listApplyByPostFunc = (postId)
         -> applyService.findAppliesByPostId(postId);
        CountAppliesByUserAndPostFunc countAppliesByUserAndPostFunc = (userId, postId)
        -> applyService.countAppliesByUserAccountIdAndPostId(userId, postId);

        System.out.println("Function: "+listApplyByPostFunc);
        System.out.println("post size: "+userPosts.size());

        model.addAttribute("userPosts", userPosts);
        model.addAttribute("listApplyByPostFunc", listApplyByPostFunc);
        model.addAttribute("countApply", countAppliesByUserAndPostFunc);
        return "user/post/index";
    }


    @GetMapping(value = {"/post/show/{id}"})
    private String show(Model model, @PathVariable Long id,
                        @AuthenticationPrincipal UserDetails userDetails){

        Optional<Post> post = postService.findById(id);

        if(userDetails !=  null){
            String sessionEmail = SecurityUtil.getSessionUser();
            Long simple_user_id = userService.findByEmailAndProvider(sessionEmail, EnumComponent.SIMPLE).getId();
            Labor simple_user_labor = laborService.findByUserId(simple_user_id);
            int countApplyByUser = applyService
            .countAppliesByUserAccountIdAndPostId(simple_user_id, id);

            if(simple_user_labor != null){
                System.out.println(simple_user_labor.getFull_name());
                model.addAttribute("countApplyByUser", countApplyByUser);
                model.addAttribute("simple_user_labor", simple_user_labor);
                model.addAttribute("age_labor", convertAge(simple_user_labor.getBirthday()));
            }
        }
        model.addAttribute("post", post.get());
        model.addAttribute("countApply",applyService.countAppliesByPostId(id));

        return "user/post/show";
    }

    @PostMapping(value = {"/apply/post/{id}"})
    String apply(@PathVariable Long id,
                 @ModelAttribute("apply") Apply apply,
                 @RequestParam("image") MultipartFile file,
                 RedirectAttributes flashMessage) throws IOException {

        Long uID = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE).getId();
        Optional<Post> post = postService.findById(id);

        if(Objects.equals(post.get().getUserAccount().getId(), uID)){
            flashMessage.addFlashAttribute("failed", "ĐÂY LÀ TIN TUYỂN DỤNG CỦA BẠN VÌ THẾ NÊN BẠN KHÔNG THỂ ỨNG TUYỂN!");
            return "redirect:/post/show/" + id;
        }
        if(applyService.countAppliesByPostId(id) >= post.get().getQuantity()){
            flashMessage.addFlashAttribute("failed", "BẠN KHÔNG THỂ ỨNG TUYỂN ĐƠN TUYỂN DỤNG NÀY ĐƯỢC NỮA VÌ ĐÃ HẾT SLOT!");
            return "redirect:/post/show/" + id;
        }
        if(applyService.countAppliesByUserAccountIdAndPostId(uID, id) > 0){
            flashMessage.addFlashAttribute("failed", "BẠN ĐÃ ỨNG TUYỂN ĐƠN TUYỂN DỤNG NÀY!");
            return "redirect:/post/show/" + id;
        }
        if(!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            Path staticPath = Paths.get("src/main/resources/static/assets/images/");
            assert fileName != null;
            FileCopyUtils.copy(file.getBytes(), new File(String.valueOf(staticPath),fileName));
            apply.setImage_apply(fileName);
        }
        try{
            System.out.println(apply);
            Optional<UserAccount> userAccount = userRepo.findById(uID);
            apply.setUserAccount(userAccount.get());
            apply.setPost(post.get());
            applyService.save(apply);

            vonageSendSmsService.sendSms(post.get().getPhone_number(),
    "APPLY FOR " + post.get().getTitle()+
            "\n\nDear "+ post.get().getBusiness_name() + "." +
            "\n\nMY NAME IS " + userAccount.get().getLabors().get(0).getFull_name()+
            ", and I was born on " +  userAccount.get().getLabors().get(0).getBirthday() +
            "\n\n" + apply.getAbout()+
            "\n\n Best regards," +
            "\n\n"+ userAccount.get().getLabors().get(0).getFull_name()+
            "\n\nBY BOOKINGLABOR WEBSITE\n\nBookingLabor.com.vn");

            sendMailService.setMailSender(post.get().getUserAccount().getEmail(),
      "APPLY FOR " + post.get().getTitle(),
       "Dear "+ post.get().getBusiness_name() + "." +
            "\n\nMY NAME IS " + userAccount.get().getLabors().get(0).getFull_name()+
            ", and I was born on " +  userAccount.get().getLabors().get(0).getBirthday() +
            "\n\n" + apply.getAbout()+
            "\n\n Best regards," +
            "\n\n"+ userAccount.get().getLabors().get(0).getFull_name()+
            "\n\nBY BOOKINGLABOR WEBSITE\n\nBookingLabor.com.vn");
            flashMessage.addFlashAttribute("success", "ỨNG TUYỂN THANH CÔNG");
            return "redirect:/post/show/" + id;

        }catch (Exception exception){
            flashMessage.addFlashAttribute("failed", "ỨNG TUYỂN THẤT BẠI!");
            return "redirect:/post/show/" + id;
        }
    }

    public static int convertAge(Date birthday) {
        LocalDate birthDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears();
    }

    @GetMapping(value = {"/post/create"})
    private String create(Model model){

        String email = SecurityUtil.getSessionUser();
        List<City> cities = cityService.findAllCities();
        List<Job> jobs = jobService.findAllJobs();
        List<CategoryJob> categoryJobs = categoryJobService.findAllCategoryJobs();

        List<String> currentRoleUser = new ArrayList<>();

        model.addAttribute("cities", cities);
        model.addAttribute("jobs", jobs);
        model.addAttribute("categoryJobs", categoryJobs);
        model.addAttribute("roleUser", currentRoleUser);

        return "/user/post/create";
    }

    @PostMapping(value = {"/save/post"})
    String save(Model model, @ModelAttribute("post") Post post,
                @RequestParam("city_id") Long city_id,
                @RequestParam("category_id") Long category_id,
                @RequestParam("job_id") Long job_id,
                @RequestParam("image") MultipartFile file,
                RedirectAttributes flashMessage) throws IOException {
        try{

            String fileName = file.getOriginalFilename();
            Path staticPath = Paths.get("src/main/resources/static/assets/images/");
            assert fileName != null;
            FileCopyUtils.copy(file.getBytes(),
            new File(String.valueOf(staticPath),fileName));
            System.out.println(staticPath);
            System.out.println("Name file: "+fileName);
            post.setImage_workplace(fileName);
            postService.saveData(post, city_id, category_id, job_id);

            flashMessage.addFlashAttribute("success", "Tạo thành công");
            return "redirect:/post/create";

        }catch (Exception exception){

            System.out.println("Error: "+exception);
            flashMessage.addFlashAttribute("failed", "Tạo thất bại!!!");
//            return "redirect:/post/create";
            throw exception;

        }
    }

    @PostMapping("/delete/post/{id}")
    String delete(@PathVariable Long id, RedirectAttributes flashMessage){

        if(applyService.countAppliesByPostId(id) > 0){
            flashMessage.addFlashAttribute("failed", "Bạn không thể xóa tin tuyển dụng này!");
            return "redirect:/post-manager";
        }
        postService.deleteById(id);
        flashMessage.addFlashAttribute("success", "Xóa thành công");
        return "redirect:/post-manager";
    }



}
