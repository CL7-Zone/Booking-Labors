package com.example.bookinglabor.controller.component;

import com.example.bookinglabor.service.CountService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
@Qualifier("load3")
public class countComponent3 implements CountService {


    @Bean(name = "load3")
    @Override
    public int LoadCount() {
        return 3;
    }
}
