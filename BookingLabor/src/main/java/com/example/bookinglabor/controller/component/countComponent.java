package com.example.bookinglabor.controller.component;

import com.example.bookinglabor.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@Primary
@Qualifier("load")
public class countComponent implements CountService {

    private int  loadCounter;

    @Bean(name = "load")
    @Override
    public int LoadCount() {

        return loadCounter++;
    }
}
