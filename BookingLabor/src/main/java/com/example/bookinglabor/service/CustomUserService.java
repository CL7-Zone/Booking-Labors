package com.example.bookinglabor.service;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface CustomUserService {

    OAuth2AuthenticationToken filterUser(OAuth2AuthenticationToken oAuth2AuthenticationToken);

}
