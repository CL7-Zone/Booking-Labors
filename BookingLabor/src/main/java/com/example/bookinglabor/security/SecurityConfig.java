package com.example.bookinglabor.security;


import com.example.bookinglabor.model.Role;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.security.oauth2.Oauth2LoginSuccessHandler;
import com.example.bookinglabor.service.UserService;
import com.example.bookinglabor.service.oAuth2.CustomerOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthEntryPoint authEntryPoint;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;
    private ClientRegistrationRepository clientRegistrationRepository;

    private CustomerOauth2UserService oauth2UserService;
    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    private  CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private AccountFilter accountFilter;


    @Autowired
    public SecurityConfig(JwtAuthEntryPoint authEntryPoint, CustomUserDetailsService userDetailsService, UserService userService, ClientRegistrationRepository clientRegistrationRepository, CustomerOauth2UserService oauth2UserService, Oauth2LoginSuccessHandler oauth2LoginSuccessHandler, CustomAccessDeniedHandler customAccessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, AccountFilter accountFilter) {
        this.authEntryPoint = authEntryPoint;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.oauth2UserService = oauth2UserService;
        this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.accountFilter = accountFilter;
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


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable() // Vô hiệu hóa CSRF protection để cho phép đăng nhập qua form POST mà không cần token CSRF
        .authorizeRequests() // Bắt đầu cấu hình cho việc xác thực yêu cầu
        .antMatchers(HttpMethod.GET,"/","/error","/login",
        "/labors/**", "/category-job/**","/jobs/show/**","/blog",
        "/assets/**", "/vendor/**", "/send-mail","/contact",
        "/verify", "/post/show/{id}", "/guest/**", "/send-sms",
        "/auth", "/header-api", "/logout", "/forgot-password")
        .permitAll()

        .antMatchers(HttpMethod.POST, "/register/save","/verify/account",
        "/auth/save", "/auth/account","/guest/**", "/api/login",
        "/logout", "/update/password", "/send/token")
        .permitAll()

        // Cho phép mọi người truy cập các đường dẫn này mà không cần xác thực
        .antMatchers(HttpMethod.GET,"/your-menu/**", "/labor-create-info",
        "/contact/report", "/customer-create-info", "/jobs",
        "/post/create", "/post-manager")
        .hasAnyRole("USER", "LABOR", "CUSTOMER", "ADMIN", "STAFF_CUSTOMER")// Cho phép người dùng có role là USER truy cập vào các route trên

        .antMatchers(HttpMethod.POST, "/labor/info/save", "/customer/info/save",
        "/save/post", "/user/search", "/apply/post/{id}", "/delete/post/{id}",
        "/send/report", "/delete/apply/{id}")
        .hasAnyRole("USER", "LABOR", "CUSTOMER", "ADMIN")

        .antMatchers(HttpMethod.GET, "/your-info-labor",  "/your-cart", "/your-job"
        , "/booking-manager-by-labor", "booking-manager-by-labor/{id}").hasAnyRole("LABOR")

        .antMatchers(HttpMethod.POST, "/save/cart/job/**", "/save/your-job",
        "/update/job-detail/**","/accept-booking/{id}","/save/comment_skill/{id}",
        "/delete/job-cart/**", "/delete/job-detail/**").hasAnyRole("LABOR")

        .antMatchers(HttpMethod.GET, "/your-info-customer", "/your-booking-cart",
        "/your-booking", "/delete/booking-cart/**").hasAnyRole("CUSTOMER")

        .antMatchers(HttpMethod.POST, "/save/cart/job-detail/**", "/save/cart/booking",
        "/save/booking", "/update/booking/**").hasAnyRole("CUSTOMER")

        .antMatchers(HttpMethod.GET , "/admin/**", "/swagger-ui/**")
        .hasAnyRole("ADMIN", "STAFF_CUSTOMER")

        .antMatchers(HttpMethod.POST , "/admin/**")
        .hasAnyRole("ADMIN")

        .antMatchers()
        .authenticated()// Yêu cầu xác thực (đăng nhập) để truy cập các đường dẫn này
        .anyRequest().authenticated() // Bất kỳ yêu cầu nào khác cũng yêu cầu xác thực
        .and() // Kết thúc phần cấu hình cho authorizeRequests()
        // bắt đầu một cấu hình mới
//      .oauth2Login()//cấu hình oAuth2 google login
//      .loginPage("/login")
//      .userInfoEndpoint().userService(oauth2UserService)
//      .and()
//      .successHandler(oauth2LoginSuccessHandler)
//      .and()
        .formLogin(form -> form
        .loginPage("/login")
        .defaultSuccessUrl("/your-menu")//login thành công
        .loginProcessingUrl("/login")
        .failureUrl("/login?error=true")//sai mật khẩu
        .permitAll())
        .exceptionHandling()
        .authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                response.sendRedirect(request.getContextPath() + "/login?unauthorized");
            }
        })//Unauthorized khi chưa login
        .accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.sendRedirect(request.getContextPath() + "/your-menu?unauthorized");
            }
        });//Unauthorized khi k có quyền;

        // hàm accountFilter sẽ đc thực thi trước UsernamePasswordAuthenticationFilter
        http.addFilterBefore(accountFilter,
        UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(authenticationProvider());// lưu user login vào UserDetail
        http.addFilterBefore(jwtAuthenticationFilter(),//Xác thực khi đăng nhập bằng API
        UsernamePasswordAuthenticationFilter.class)//Xác thực username và password
        .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
        .accessDeniedHandler(customAccessDeniedHandler)
        ;

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer :: disable)
//                .exceptionHandling(
//                        exception -> exception.authenticationEntryPoint(authEntryPoint))
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
    @Bean
    public OAuth2AuthorizationRequestRedirectFilter oauth2AuthorizationRequestRedirectFilter() {
        return new OAuth2AuthorizationRequestRedirectFilter(clientRegistrationRepository, "/oauth2/authorization");
    }

//    public void configure(AuthenticationManagerBuilder builder) throws Exception {
//
//        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }


}