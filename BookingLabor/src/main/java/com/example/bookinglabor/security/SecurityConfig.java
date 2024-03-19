package com.example.bookinglabor.security;


import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
        this.userService = userService;
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception{

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){

        return new JWTAuthenticationFilter();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//
//        System.out.println("run login!!!");
//
//        http.csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint(authEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login", "/css/**", "/js/**")
//                .permitAll()
//                .and()
//                .formLogin(form -> form
//                .loginPage("/login")
//                .defaultSuccessUrl("/your-menu")
//                .loginProcessingUrl("/login")
//                .failureUrl("/login?error=true")
//                .permitAll()
//                ).logout(logout -> logout
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .permitAll()
//                );
//
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.csrf().disable() // Vô hiệu hóa CSRF protection để cho phép đăng nhập qua form POST mà không cần token CSRF
                .authorizeRequests() // Bắt đầu cấu hình cho việc xác thực yêu cầu
                .antMatchers("/","/error","/login", "/admin/work/creates",
                "/labors-detail", "/category-job/**","/jobs/show/**", "/assets/**", "/vendor/**")
                .permitAll() // Cho phép mọi người truy cập các đường dẫn này mà không cần xác thực
                .antMatchers(HttpMethod.GET,"/your-menu", "/labor-update-info", "/customer-update-info")
                .hasAnyRole("USER", "LABOR", "CUSTOMER")// Cho phép người dùng có role là USER truy cập vào các route trên
                .antMatchers(HttpMethod.POST, "/labor/info/save", "/customer/info/save")
                .hasAnyRole("USER", "LABOR", "CUSTOMER")
                .antMatchers(HttpMethod.GET, "/your-info-labor").hasAnyRole("LABOR")
                .antMatchers(HttpMethod.GET, "/your-job-detail").hasAnyRole("LABOR")
                .antMatchers(HttpMethod.POST, "/save/job").hasAnyRole("LABOR")
                .antMatchers(HttpMethod.GET, "/your-info-customer").hasAnyRole("CUSTOMER")
//                .antMatchers(HttpMethod.GET , "/admin/home")
//                .hasAnyRole("ADMIN")
//                .antMatchers(HttpMethod.GET,"/admin/work/creates")
//                .hasAnyRole("ADMIN")
                .antMatchers()
                .authenticated()// Yêu cầu xác thực (đăng nhập) để truy cập các đường dẫn này
                .anyRequest().authenticated() // Bất kỳ yêu cầu nào khác cũng yêu cầu xác thực
                .and() // Kết thúc phần cấu hình cho authorizeRequests(), bắt đầu một cấu hình mới
                .formLogin(form -> form // Cấu hình đăng nhập thông qua form
                .loginPage("/login") // Đường dẫn đến trang đăng nhập
                .defaultSuccessUrl(menuRedirect()) // Đường dẫn mặc định sau khi đăng nhập thành công
                .loginProcessingUrl("/login") // Đường dẫn xử lý quá trình đăng nhập
                .failureUrl(failedLogin()) // Đường dẫn sau khi đăng nhập thất bại
                .permitAll() // Cho phép mọi người truy cập trang đăng nhập
                ).logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))// Gửi yêu cầu logout
                .permitAll()// Cho phép mọi người truy cập trang logout
                ).exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response
                .sendRedirect("/login?login=false"))
                .accessDeniedPage("/your-menu?unauthorized");// block user ra nếu k có quyền

        return http.build();
    }

    String menuRedirect(){

        return "/your-menu";
    }

    String failedLogin(){

        return "/login?error=true";
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception {

        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }



}
