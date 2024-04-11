package com.example.bookinglabor.controller;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.JobDetailObject;
import com.example.bookinglabor.model.sessionObject.UserObject;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.JWTGeneratorToken;
import com.example.bookinglabor.service.SendMailService;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class DisplayAuthController {

    private final UserService userService;
    private final SendMailService sendMailService;

    private final AuthenticationManager authenticationManager;
    private UserRepo userRepository;
    private RoleRepo roleRepository;
    private PasswordEncoder passwordEncoder;
    private final JWTGeneratorToken jwtGenerator;

    @Autowired
    public DisplayAuthController(UserService userService, SendMailService sendMailService, AuthenticationManager authenticationManager, UserRepo userRepository, RoleRepo roleRepository, PasswordEncoder passwordEncoder, JWTGeneratorToken jwtGenerator) {
        this.userService = userService;
        this.sendMailService = sendMailService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @GetMapping("/login")
    public String index(){

        return "auth/login";
    }

    @GetMapping("/verify")
    public String verify(Model model){

        return "/auth/verify";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") UserDto user,
                           BindingResult res, Model model,
                           HttpServletRequest request, HttpSession session,
                           RedirectAttributes flashMessage){

        UserAccount existingUserEmail = userService.findByEmail(user.getEmail());
        @SuppressWarnings("unchecked")
        List<UserObject> userObject = (List<UserObject>) request.getSession().getAttribute("userObject");

        if(existingUserEmail != null && existingUserEmail.getEmail() != null
                && !existingUserEmail.getEmail().isEmpty()
        ){
            return "redirect:/login?registerFailed=true";
        }
        if(existingUserEmail != null){
            return "redirect:/login?registerFailed=true";
        }
        if(res.hasErrors()){
            model.addAttribute("user", user);
            return "redirect:/login?registerFailed=true";
        }
        try{
            userService.saveDataToSessionStore(userObject, user, request, session);
            List<UserObject> userObj = (List<UserObject>) session.getAttribute("userObject");

            for(UserObject userSend : userObj){
                sendMailService.setMailSender(userSend.getEmail(), "XÁC MINH TÀI KHOẢN",
           "Xin chào "+ userSend.getEmail()+ "," +
                "\n\nMã xác minh tài khoản của bạn là: " + userSend.getToken()+
                "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi." +
                "\n\nBest regards,\nBookingLabor Website");
                break;
            }
            flashMessage.addFlashAttribute("verify",
"CHÚNG TÔI ĐÃ GỬI MÃ XÁC MÌNH VÀO GMAIL CỦA BẠN HÃY\n" +
            "KIỂM TRA GMAIL CỦA BẠN VÀ NHẬP VÀO MÃ XÁC MÌNH!");
            return "redirect:/verify";

        }catch (Exception exception){
            flashMessage.addFlashAttribute("failed",exception);
            return "redirect:/verify";
        }

    }

    @PostMapping("/verify/account")
    public String save(@RequestParam("verify_token") String verifyToken,
                       HttpSession session, RedirectAttributes flashMessage){

        UserDto user = new UserDto();
        List<UserObject> userObject = (List<UserObject>) session.getAttribute("userObject");

        try{
            if(verifyToken == null){
                flashMessage.addFlashAttribute("failed","VUI LÒNG NHẬP VÀO MÃ XÁC MINH!");
                return "redirect:/verify";
            }
            if(userObject == null){
                flashMessage.addFlashAttribute("failed","VUI LÒNG ĐĂNG KÝ TÀI KHOẢN!");
                return "redirect:/verify";
            }
            for(UserObject userObj : userObject){
                user.setEmail(userObj.getEmail());
                user.setPassword(userObj.getPassword());
            }
            if(!userService.checkVerifyToken(userObject,verifyToken)){
                flashMessage.addFlashAttribute("failed","SAI MÃ XÁC MINH!");
                return "redirect:/verify";
            }
            session.removeAttribute("userObject");
            userService.saveUser(user);
            flashMessage.addFlashAttribute("verifySuccess","Xác minh tài khoản thành công bây giờ bạn có thể đăng nhập vào!");
            return "redirect:/login";

        }catch (Exception exception){

            flashMessage.addFlashAttribute("failed",exception);
            return "redirect:/login";
        }
    }

}
