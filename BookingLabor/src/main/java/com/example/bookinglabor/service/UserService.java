package com.example.bookinglabor.service;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.AuthObject;
import com.example.bookinglabor.model.sessionObject.UserObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;


@Service
public interface UserService {


    void saveUser(UserDto userDto);

    void saveOtherUser(String  email, EnumComponent provider);

    UserAccount findByEmail(String email);
    UserAccount findByEmailAndProvider(String email, EnumComponent provider);

    void updateUserRole(Role role);

    void createUserRoleCustomer(Role role);
    void createUserRoleLabor(Role role);

    void saveDataToSessionStore(List<UserObject> userObject, UserDto user, HttpServletRequest request, HttpSession session);

    void saveDataToSessionStore(List<AuthObject> authObject,
         UserDto userDto
        , HttpServletRequest request, HttpSession session, String NONE);


    Collection<? extends GrantedAuthority> getUpdatedAuthorities(Authentication authentication);

    String encryptPassword(String password, String key);

    boolean checkVerifyToken(List<UserObject> userObject,String inputToken);

    boolean checkAuthToken(List<AuthObject> authObject,String inputToken);

    long generateNumber();
}
