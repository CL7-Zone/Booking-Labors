package com.example.bookinglabor.controller.component;

import com.example.bookinglabor.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class countComponent implements CountService {

    private int  loadCounter;

    @Bean
    @Override
    public int LoadCount() {

        return loadCounter++;
    }
}
