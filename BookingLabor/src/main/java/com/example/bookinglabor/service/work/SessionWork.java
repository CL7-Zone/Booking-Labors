package com.example.bookinglabor.service.work;

import com.example.bookinglabor.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@AllArgsConstructor
public class SessionWork implements SessionService  {


    @Override
    public HttpSession getSessionById(HttpServletRequest request, String sessionId) {
        return request.getSession(false);
    }

    @Override
    public String createSession(HttpServletRequest request, String nameAttribute, int value, int timeOut) {

        HttpSession session = request.getSession();
        session.setAttribute(nameAttribute, value);
        session.setMaxInactiveInterval(timeOut);
        return "Session created with expiration time set to 1 minute";
    }
}
