package com.burakkocak.multithreading.tickets.repository;

import com.burakkocak.multithreading.tickets.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    Optional<Customer> findById(String id);
    Customer findByFirstNameAndSecondName(String firstName, String secondName);

}
