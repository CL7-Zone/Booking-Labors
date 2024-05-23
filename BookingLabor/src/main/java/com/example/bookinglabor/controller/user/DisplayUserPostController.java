package com.example.bookinglabor.controller.user;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.model.*;
import com.example.bookinglabor.model.FileUpload;
import com.example.bookinglabor.repo.JobDetailRepo;
import com.example.bookinglabor.repo.LaborRepo;
import com.example.bookinglabor.repo.PostRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.*;
import lombok.AllArgsConstructor;
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

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    private FileUploadService fileUploadService;

    interface CountAppliesByUserAndPostFunc extends BiFunction<Long, Long, Integer> {

    }

    @GetMapping(value = {"/post-manager"})
    private String index(Model model){

        try{
            Long user_id = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE).getId();
            List<Post> userPosts = postService.findPostByUserAccountId(user_id);
            //Lambda function vế trước là tham số đầu vào
            // vế sau là tham số đầu ra
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
        }catch (Exception exception){

            System.out.println(exception.getMessage());
            throw  exception;
        }
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

    @PostMapping(value = {"/delete/apply/{id}"})
    String delete(@PathVariable Long id){

        Long userId = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE).getId();
        Optional<Apply> applyOptional = applyService.findById(id);

        if (applyOptional.isPresent()) {
            Apply apply = applyOptional.get();
            Post post = apply.getPost();
            List<Post> postOwners = postService.findPostByUserAccountId(userId);
            boolean isOwner = postOwners.stream().anyMatch(p -> p.getId().equals(post.getId()));

            if (isOwner) {

                String applyEmail = apply.getUserAccount().getEmail();
                String laborName = apply.getUserAccount().getLabors().get(0).getFull_name();
                try {
                    sendMailService.setMailSender(applyEmail, "Đơn tuyển dụng số: " + id,
                "Xin chào " + laborName + ",\n\nNgười tuyển dụng đã từ chối đơn ứng tuyển của bạn!," +
                    "\n\nXem chi tiết tin tuyển dụng đã bị từ chối: "
                    + "http://localhost:8080/post/show/"+ apply.getPost().getId() +
                    "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi." +
                    "\n\nBest regards,\nBookingLabor Website");
                    fileUploadService.deleteByApplyId(id);
                    applyService.deleteById(id);
                    System.out.println("delete successfully");
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            } else {
                System.out.println("Không được phép xóa: " + id);
                return  "redirect:/post-manager?notAllow";
            }
        } else {
            System.out.println("Không tìm thấy: " + id);
            return  "redirect:/post-manager?notFound";
        }
        return  "redirect:/post-manager?successfully";
    }

    @PostMapping("/delete/post/{id}")
    String delete(@PathVariable Long id,
                  RedirectAttributes flashMessage){

        Long userId = userService.findByEmailAndProvider(SecurityUtil.getSessionUser(), EnumComponent.SIMPLE).getId();
        try{
            if(postService.countPostsByUserAccountIdAndId(userId, id) < 1){
                return  "redirect:/post-manager?notAllow";
            }
            if(applyService.countAppliesByPostId(id) > 0){
                flashMessage.addFlashAttribute("failed", "Bạn không thể xóa tin tuyển dụng này!");
                return "redirect:/post-manager";
            }
            postService.deleteById(id);

            flashMessage.addFlashAttribute("success", "Xóa thành công");
        }catch (Exception exception){
            flashMessage.addFlashAttribute("success", "Xóa thất bại!!!");
        }
        return "redirect:/post-manager";
    }

    @PostMapping(value = {"/apply/post/{id}"})
    String apply(@PathVariable Long id,
                 @ModelAttribute("apply") Apply apply,
                 @RequestParam("image") MultipartFile file,
                 @RequestParam("file_apply") MultipartFile file_apply,
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
        }else{
            flashMessage.addFlashAttribute("failed", "VUI LÒNG ĐÍNH KÈM ẢNH!");
            return "redirect:/post/show/" + id;
        }
        try{
            System.out.println(apply);
            Optional<UserAccount> userAccount = userRepo.findById(uID);

            apply.setUserAccount(userAccount.get());
            apply.setPost(post.get());
            Apply savedApply = applyService.save(apply);

            if(!file_apply.isEmpty()){
                FileUpload attchment = fileUploadService.saveFile(file_apply, savedApply);
                String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/").path(attchment.getId()).toUriString();
                System.out.println("Link download: " + downloadUrl);
            }
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
            flashMessage.addFlashAttribute("success", "ỨNG TUYỂN THÀNH CÔNG");
            return "redirect:/post/show/" + id;

        }catch (Exception exception){

            System.out.println(exception + "");
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
                RedirectAttributes flashMessage
    ) throws IOException {
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
            return "redirect:/post/create";

        }
    }



}
