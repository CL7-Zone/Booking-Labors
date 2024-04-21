package com.example.bookinglabor.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface SessionService {


    HttpSession getSessionById(HttpServletRequest request, String sessionId);
    String createSession(HttpServletRequest request, String nameAttribute, int value, int timeOut);
}
