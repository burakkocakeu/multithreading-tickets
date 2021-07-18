package com.burakkocak.multithreading.tickets.repository;

import com.burakkocak.multithreading.tickets.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {

    Optional<Ticket> findById(String id);
    List<Ticket> findByDestination(String destination);
    List<Ticket> findByDateAndDestination(String date, String destination);

}
