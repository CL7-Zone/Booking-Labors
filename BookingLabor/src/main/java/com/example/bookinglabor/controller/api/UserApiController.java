package com.example.bookinglabor.controller.api;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.AuthResponseDto;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.UserObject;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserApiController {

    private UserService userService;

    @GetMapping("/admin/profile")
    public JSONObject index(@AuthenticationPrincipal UserDetails userDetails,
                                 HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        try {

            Long userId = userService.findByEmailAndProvider(userDetails.getUsername(), EnumComponent.SIMPLE).getId();
            jsonObject.put("account", userDetails);
            jsonObject.put("userId", userId);
            System.out.println("User login: "+jsonObject);

            return jsonObject;
        } catch (Exception exception) {
            System.out.println("ERROR admin profile: " + exception.getMessage());
            jsonObject.put("message", exception);
            return jsonObject;
        }
    }


}
