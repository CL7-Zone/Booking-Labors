package com.example.bookinglabor.service.work;

import com.example.bookinglabor.mapper.CustomerMapper;
import com.example.bookinglabor.mapper.LaborMapper;
import com.example.bookinglabor.model.Customer;
import com.example.bookinglabor.model.UserAccount;
import com.example.bookinglabor.repo.CustomerRepo;
import com.example.bookinglabor.repo.UserRepo;
import com.example.bookinglabor.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerWork implements CustomerService {

    private final CustomerRepo customerRepo;
    private final UserRepo userRepository;

    @Override
    public List<Customer> findAllCustomers() {

        List<Customer>  customers = customerRepo.findAll();

        return customers.stream()
                .map(CustomerMapper::mapToCustomer)
                .collect(Collectors
                .toList());
    }

    @Override
    public Customer findById(Long id) {

        Optional<Customer> customer = customerRepo.findById(id);

        return customer.map(CustomerMapper::mapToCustomer).orElse(null);
    }

    @Override
    public List<Customer> findByUserId(Long user_id) {

        List<Customer> customers = customerRepo.findByUserId(user_id);

        return customers.stream()
                .map(CustomerMapper::mapToCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public void createCustomerByUserId(Long user_id, Customer customer) {

        UserAccount user = userRepository.findById(user_id).get();
        Customer customer_create = CustomerMapper.mapToCustomer(customer);
        assert customer_create != null;
        customer_create.setUserAccount(user);
        customerRepo.save(customer_create);

    }

    @Override
    public void saveAllDataFromExcel(MultipartFile file) {

    }
}
