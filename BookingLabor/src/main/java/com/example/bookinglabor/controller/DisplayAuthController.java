package com.example.bookinglabor.controller;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.UserObject;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.JWTGeneratorToken;
import com.example.bookinglabor.service.SendMailService;
import com.example.bookinglabor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/logout")
    String logoutGet(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

        logoutHandler.logout(request, response, authentication);

        return "redirect:/login?logout";
    }

    @GetMapping("/verify")
    public String verify(Model model){

        return "/auth/verify";
    }

    @GetMapping("/forgot-password")
    public String forgot(Model model, HttpSession session){

        List<UserObject> forgotPass = (List<UserObject>) session.getAttribute("userObject");

        model.addAttribute("forgotPass",forgotPass);
        return "/auth/forgot";
    }

    @PostMapping("/send/token")
    public String send(@Valid @ModelAttribute("user") UserDto user,
                         HttpServletRequest request, HttpSession session,
                         RedirectAttributes flashMessage){
        @SuppressWarnings("unchecked")
        List<UserObject> userObject = (List<UserObject>) request.getSession().getAttribute("userObject");

        try{
            UserAccount userAccount = userService.findByEmailAndProvider(user.getEmail() , EnumComponent.SIMPLE);
            if(userAccount == null){
                flashMessage.addFlashAttribute("failed",
    "KHÔNG CÓ TÀI KHOẢN NÀY!");
                return "redirect:/forgot-password";
            }
            userService.saveDataToSessionStore(userObject, user, request, session);
            List<UserObject> forgotSend = (List<UserObject>) session.getAttribute("userObject");
            for (UserObject forgotPass : forgotSend){
                sendMailService.setMailSender(forgotPass.getEmail(),
         "XÁC MINH TÀI KHOẢN QUÊN MẬT KHẨU",
           "Xin chào "+ forgotPass.getEmail()+ "," +
                "\n\nMã xác minh quên mật khẩu tài khoản của bạn là: " +
                forgotPass.getToken() +
                "\n\nNếu có bất kỳ thắc mắc nào vui lòng liên hệ với chúng tôi." +
                "\n\nBest regards,\nBookingLabor Website");
                break;
            }
            flashMessage.addFlashAttribute("success",
 "CHÚNG TÔI ĐÃ GỬI MÃ XÁC MINH VÀO GMAIL CỦA BẠN HÃY\n" +
            "KIỂM TRA GMAIL CỦA BẠN VÀ NHẬP VÀO MÃ XÁC MINH!");
        }catch (Exception exception){
            flashMessage.addFlashAttribute("failed",exception);
        }
        return "redirect:/forgot-password";
    }


    @PostMapping("/update/password")
    public String update(HttpSession session,
                         RedirectAttributes flashMessage,
                         @RequestParam("verify_token") String verify_token){
        try{
            if(verify_token != null){
                List<UserObject> forgotSend = (List<UserObject>) session.getAttribute("userObject");
                if(!userService.checkVerifyToken(forgotSend,verify_token)){
                    flashMessage.addFlashAttribute("failed","SAI MÃ XÁC MINH!");
                    return "redirect:/forgot-password";
                }
                userService.updateUser(forgotSend ,session);
                session.removeAttribute("userObject");
                flashMessage.addFlashAttribute("success", "CẬP NHẬT MẬT KHẨU THÀNH CÔNG");
                return "redirect:/forgot-password";
            }
            flashMessage.addFlashAttribute("failed", "VUI LÒNG NHẬP VÀO MÃ XÁC MINH!");

        }catch (Exception exception){
            flashMessage.addFlashAttribute("failed",exception);

            throw exception;
        }
        return "redirect:/forgot-password";
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
"CHÚNG TÔI ĐÃ GỬI MÃ XÁC MINH VÀO GMAIL CỦA BẠN HÃY\n" +
            "KIỂM TRA GMAIL CỦA BẠN VÀ NHẬP VÀO MÃ XÁC MINH!");
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
