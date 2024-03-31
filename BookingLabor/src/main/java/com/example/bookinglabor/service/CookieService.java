package com.example.bookinglabor.service;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CookieService {

    void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds);

    String getCookieValue(HttpServletRequest request, String name);

    void deleteCookie(HttpServletResponse response, String name);
}
