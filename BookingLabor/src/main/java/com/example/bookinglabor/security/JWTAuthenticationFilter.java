package com.example.bookinglabor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.access.AccessDeniedException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGeneratorToken tokenGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = getJWTFromRequest(request);

            if(StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {

                String email = tokenGenerator.getEmailFromJWT(token);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                System.out.println("authenticationToken: "+authenticationToken);
                System.out.println("Token: "+token);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    System.out.println("User authenticated successfully!");
                } else {
                    System.out.println("User authentication failed!");
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            sendErrorResponse(response, "Token expired!");
        } catch (MalformedJwtException | UnsupportedJwtException ex) {
            sendErrorResponse(response, "Invalid token!");
        }
        catch (AccessDeniedException ex) {
            sendErrorResponse(response, "Unauthorized!");
        }
        catch (BadCredentialsException ex) {
            sendErrorResponse(response, "Incorrect username or password!");
        }

    }


    private void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("error", errorMessage);
        objectMapper.writeValue(response.getWriter(), map);
    }

    private String getJWTFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            return bearerToken.substring(7);
        }
        return null;
    }
}
