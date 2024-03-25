package com.example.bookinglabor.service;

import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.Customer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomers();
    Customer findById(Long id);

    List <Customer> findByUserId(Long user_id);

    void createCustomerByUserId(Long user_id, Customer customer);

    void saveAllDataFromExcel(MultipartFile file);
}
