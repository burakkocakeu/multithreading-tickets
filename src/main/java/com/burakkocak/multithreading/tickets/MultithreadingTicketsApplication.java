package com.burakkocak.multithreading.tickets;

import com.burakkocak.multithreading.tickets.book.TicketBookingThread;
import com.burakkocak.multithreading.tickets.book.TicketCounter;
import com.burakkocak.multithreading.tickets.entity.Ticket;
import com.burakkocak.multithreading.tickets.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication @Slf4j
public class MultithreadingTicketsApplication implements CommandLineRunner {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketCounter ticketCounter;

    private List<String> cityList = new ArrayList<>(Arrays.asList("istanbul", "ankara", "izmir", "bursa", "antalya", "rize", "batman"));

    public static void main(String[] args) {
        SpringApplication.run(MultithreadingTicketsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ticketRepository.deleteAll();

        // save a couple of tickets
        ticketRepository.save(new Ticket("19-10-2021", "istanbul", "22"));
        ticketRepository.save(new Ticket("05-12-2021", "istanbul", "2"));
        ticketRepository.save(new Ticket("13-02-2022", "istanbul", "14"));
        ticketRepository.save(new Ticket("13-02-2022", "ankara", "14"));
        ticketRepository.save(new Ticket("06-09-2021", "izmir", "20"));
        cityList.forEach(c -> ticketRepository.save(new Ticket("06-09-2021", c, "41")));
        log.info("******************************************************************");

        // fetch all tickets
        log.info("Tickets found with findAll():");
        for (Ticket ticket : ticketRepository.findAll()) {
            log.info(ticket.toString());
        }
        log.info("******************************************************************");

        // fetch an individual ticket
        String id = ticketRepository.findAll().get(0).getId();
        log.info("Ticket found with findById('" + id + "'):");
        ticketRepository.findById(id).ifPresent(ticket -> log.info(ticket.toString()));
        log.info("******************************************************************");

        log.info("Tickets found with findByDestination('istanbul'):");
        for (Ticket ticket : ticketRepository.findByDestination("istanbul")) {
            log.info(ticket.toString());
        }
        log.info("******************************************************************");

        TicketBookingThread t1 = new TicketBookingThread(ticketCounter, "Ali", "Bol", 2);
        TicketBookingThread t2 = new TicketBookingThread(ticketCounter, "Cansu", "Seçkin", 2);
        TicketBookingThread t3 = new TicketBookingThread(ticketCounter, "Burak", "Koçak", 22);
        TicketBookingThread t4 = new TicketBookingThread(ticketCounter, "Sena", "Baran", 22);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        log.info("******************************************************************");
        List<Ticket> ticketList = ticketRepository.findByDateAndDestination("06-09-2021", "izmir");
        log.info("Tickets remained: " + (42 - ticketList.size()));

        log.info("******************************************************************");
        log.info("Tickets found with findByDateAndDestination('06-09-2021', 'izmir'):");
        for (Ticket ticket : ticketRepository.findByDateAndDestination("06-09-2021", "izmir")) {
            log.info(ticket.toString());
        }
        log.info("******************************************************************");
    }
}
