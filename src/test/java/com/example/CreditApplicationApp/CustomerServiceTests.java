package com.example.CreditApplicationApp;

import com.example.CreditApplicationApp.entity.Customer;
import com.example.CreditApplicationApp.repository.CustomerRepository;
import com.example.CreditApplicationApp.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void itShouldGetCustomer(){
        Customer savedCustomer = Customer.builder()
                .id(1l)
                .build();

        when(customerRepository.findById(1l)).thenReturn(Optional.of(savedCustomer));

        Customer customer = customerService.getCustomer(1l);

        assertEquals(customer.getId(), savedCustomer.getId());
    }

    @Test
    public void itShouldUpdateCustomer(){
        Customer customer = Customer.builder()
                .id(1l)
                .build();

        Customer updatedCustomer = Customer.builder()
                .id(2l)
                .build();

        when(customerRepository.findById(1l)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any())).thenReturn(updatedCustomer);

        Customer savedCustomer = customerService.updateCustomer(1l);

        assertEquals(savedCustomer.getId(), updatedCustomer.getId());
    }

    @Test
    public void itShouldDeleteCustomer(){
        Customer savedCustomer = Customer.builder()
                .id(1l)
                .build();

        doNothing().when(customerRepository).deleteById(1l);

        customerService.deleteCustomer(1l);
    }
}
