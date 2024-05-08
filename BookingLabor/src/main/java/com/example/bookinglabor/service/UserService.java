package com.example.bookinglabor.service;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.AuthResponseDto;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.AuthObject;
import com.example.bookinglabor.model.sessionObject.UserObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;


@Service
public interface UserService {


    List<UserAccount> getAllUsersApi();
    void saveUser(UserDto userDto);

    void saveOtherUser(String  email, EnumComponent provider);

    UserAccount findByEmail(String email);
    UserAccount findByEmailAndProvider(String email, EnumComponent provider);

    void updateUserRole(Role role);

    public void updateUser(List<UserObject> userObject ,HttpSession session);

    void createUserRoleCustomer(Role role);
    void createUserRoleLabor(Role role);

    void createUserRole(Role role, String email);

    void deleteUserRole(Role role, String email);

    void updateUserRole(Role role, String email);

    void saveDataToSessionStore(List<UserObject> userObject, UserDto user,
                                HttpServletRequest request, HttpSession session);

    void saveDataToSessionStore(HttpServletRequest request, AuthResponseDto auth);

    void saveDataToSessionStore(List<AuthObject> authObject, UserDto userDto
    ,HttpServletRequest request, HttpSession session, String NONE);


    Collection<? extends GrantedAuthority> getUpdatedAuthorities(Authentication authentication);

    String encryptPassword(String password, String key);

    boolean checkVerifyToken(List<UserObject> userObject,String inputToken);

    boolean checkAuthToken(List<AuthObject> authObject,String inputToken);

    long generateNumber();

    boolean checkActiveUser();

    void updateActiveUser(String active_name, String email);
}
