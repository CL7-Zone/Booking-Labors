package com.example.bookinglabor.service.work;

import com.example.bookinglabor.controller.component.EnumComponent;
import com.example.bookinglabor.dto.AuthResponseDto;
import com.example.bookinglabor.dto.UserDto;
import com.example.bookinglabor.mapper.UserMapper;
import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.model.sessionObject.AuthObject;
import com.example.bookinglabor.model.sessionObject.UserObject;
import com.example.bookinglabor.repo.RoleRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.security.SecurityConstants;
import com.example.bookinglabor.security.SecurityUtil;
import com.example.bookinglabor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;


@Service
@AllArgsConstructor
public class UserWork implements UserService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepo;

    PasswordEncoder passwordEncoder;

    @Override
    public List<UserAccount> getAllUsersApi() {
        return userRepository
                .findAll().stream()
                .map(UserMapper::mapToUserApi)
                .toList();
    }

    @Override
    public void saveUser(UserDto userDto) {

        UserAccount user = new UserAccount();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setProvider(EnumComponent.SIMPLE);
        Role role = roleRepo.findByName("USER");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveOtherUser(String email, EnumComponent provider) {

        UserAccount user = new UserAccount();
        Role role = roleRepo.findByName("USER");
        user.setEmail(email);
        user.setProvider(provider);

        if (role != null) {
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public UserAccount findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public UserAccount findByEmailAndProvider(String email, EnumComponent provider) {

        return userRepository.findByEmailAndProvider(email, provider);
    }

    @Override
    public void createUserRoleCustomer(Role role) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userRepository.findByEmail(sessionEmail);
        if(user != null){
            user.getRoles().add(role);
            userRepository.save(user);
        }

    }

    @Override
    public void createUserRoleLabor(Role role) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userRepository.findByEmail(sessionEmail);
        if(user != null){
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void saveDataToSessionStore(List<UserObject> userObject, UserDto user,
                                       HttpServletRequest request, HttpSession session) {
        if(userObject == null){
            userObject = new ArrayList<>();
            request.getSession().setAttribute("userObject", userObject);
        }
        UserObject userObjectSave = new UserObject(
            user.getEmail(),
            user.getPassword(),
            encryptPassword(user.getPassword(), SecurityConstants.keyEncrypt)
        );
        userObject.add(userObjectSave);
        request.getSession().setAttribute("userObject",userObject);
    }

    @Override
    public void saveDataToSessionStore(HttpServletRequest request, AuthResponseDto auth) {
        HttpSession session = request.getSession(true);
        session.setAttribute("auth", auth);
    }


    @Override
    public void saveDataToSessionStore(List<AuthObject> authObject, UserDto user,
                                       HttpServletRequest request, HttpSession session,
                                       String NONE) {
        if(authObject == null){
            authObject = new ArrayList<>();
            request.getSession().setAttribute("authObject", authObject);
        }
        AuthObject authObjectSave = new AuthObject(
                user.getEmail(),
                user.getPassword(),
                encryptPassword(user.getPassword(), SecurityConstants.keyEncrypt),
                false
        );
        authObject.add(authObjectSave);
        request.getSession().setAttribute("authObject",authObject);
    }


    @Override
    public String encryptPassword(String password, String key) {
        try {
            byte[] iv = new byte[16];
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes) + generateNumber();
        } catch (Exception exception) {

            System.out.println("ERROR: "+exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkVerifyToken(List<UserObject> userObject,String inputToken) {
        for(UserObject userObj : userObject)
            if(!Objects.equals(inputToken, userObj.getToken()))
                return false;
        return true;
    }

    @Override
    public boolean checkAuthToken(List<AuthObject> authObject, String inputToken) {
        for(AuthObject authObj : authObject){
            if(!Objects.equals(inputToken, authObj.getToken())){
                return false;
            }
            authObj.setAuth(true);
        }
        return true;
    }


    @Override
    public long generateNumber() {
        return ThreadLocalRandom.current().nextLong(10000L, 100000L);
    }

    @Override
    public Collection<? extends GrantedAuthority> getUpdatedAuthorities(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserAccount user = userRepository.findByEmail(userDetails.getUsername());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }


    @Override
    public void updateUserRole(Role role) {

        String sessionEmail = SecurityUtil.getSessionUser();
        UserAccount user = userRepository.findByEmail(sessionEmail);
        if(user != null){
            List<Role> newRoles = new ArrayList<>();
            newRoles.add(role);
            user.setRoles(newRoles);
            userRepository.save(user);
        }

    }

    @Override
    public void updateUser(List<UserObject> userObject ,HttpSession session) {

        for (UserObject forgotPassword : userObject){
            UserAccount user = userRepository.findByEmailAndProvider(forgotPassword.getEmail(), EnumComponent.SIMPLE);
            if(user != null){
                user.setPassword(passwordEncoder.encode(forgotPassword.getPassword()));
                userRepository.save(user);
            }
        }

    }



}
