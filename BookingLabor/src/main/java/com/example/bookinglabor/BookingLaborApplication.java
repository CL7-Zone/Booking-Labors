package com.example.bookinglabor;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingLaborApplication {

    public static void main(String[] args) {


        SpringApplication.run(BookingLaborApplication.class, args);

        System.out.println("run");
    }

}
