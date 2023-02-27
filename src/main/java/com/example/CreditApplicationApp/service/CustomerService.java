package com.example.CreditApplicationApp.service;

import com.example.CreditApplicationApp.entity.Customer;
import com.example.CreditApplicationApp.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        log.info("Customer: {} fetched from database", customer);
        return customer.get();
    }

    public Customer updateCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        Customer savedCustomer = customerRepository.save(customer.get());
        log.info("Customer: {} updated to updatedCustomer {}", customer.get(), savedCustomer);
        return savedCustomer;
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        log.info("Customer with id {} deleted from database", id);
    }
}
