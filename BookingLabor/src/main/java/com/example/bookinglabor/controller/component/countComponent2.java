package com.example.bookinglabor.controller.component;

import com.example.bookinglabor.service.CountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Qualifier("load2")
public class countComponent2 implements CountService {

    private int  loadCounter;
    @Bean(name = "load2")
    @Override
    public int LoadCount() {

        loadCounter++;

        return loadCounter;
    }
}
