package com.burakkocak.multithreading.tickets.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    private String id;

    private String date;

    private String destination;

    private String seat;

    private String customer;

    public Ticket(String date, String destination, String seat) {
        this.date = date;
        this.destination = destination;
        this.seat = seat;
    }

    public Ticket(String date, String destination, String seat, String customer) {
        this.date = date;
        this.destination = destination;
        this.seat = seat;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", destination='" + destination + '\'' +
                ", seat='" + seat + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }

}
