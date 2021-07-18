package com.burakkocak.multithreading.tickets.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Customer {

    @Id
    private String id;

    private String firstName;

    private String secondName;

    public Customer(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }
}
