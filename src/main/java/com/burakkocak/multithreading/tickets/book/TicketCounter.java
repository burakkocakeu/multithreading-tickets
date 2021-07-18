package com.burakkocak.multithreading.tickets.book;

import com.burakkocak.multithreading.tickets.entity.Customer;
import com.burakkocak.multithreading.tickets.entity.Ticket;
import com.burakkocak.multithreading.tickets.repository.CustomerRepository;
import com.burakkocak.multithreading.tickets.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Component
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TicketCounter {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final int maxTicketsPerDay = 42;
    private int availableTickets;

    public synchronized void bookTicket(String passengerName, String passengerSurname,
                                        int noOfSeatsToBook, String date, String destination) {

        List<Ticket> ticketList = ticketRepository.findByDateAndDestination(date, destination);
        List<Integer> ticketsInUseList = ticketList.stream()
                .map(t -> Integer.valueOf(t.getSeat()))
                .collect(Collectors.toList());

        availableTickets = maxTicketsPerDay - ticketList.size();
        log.info("Tickets available: " + availableTickets);

        if (noOfSeatsToBook == 0) {
            log.info("No seat selected!");
            return;
        }

        if (availableTickets >= noOfSeatsToBook) {
            Customer customer = customerRepository.findByFirstNameAndSecondName(passengerName, passengerSurname);

            if (customer == null) {
                customerRepository.save(new Customer(passengerName, passengerSurname));
                customer = customerRepository.findByFirstNameAndSecondName(passengerName, passengerSurname);
            }

            String customerId = customer.getId();
            IntStream.range(0, noOfSeatsToBook).forEach(index -> {
                int seat = maxTicketsPerDay;
                while (ticketsInUseList.contains(seat)) {
                    seat--;
                }
                ticketsInUseList.add(seat);

                ticketRepository.save(new Ticket(date, destination, String.valueOf(seat), customerId));
            });

            log.info("Dear '" + passengerName + "', you have successfully booked " + noOfSeatsToBook + " seat(s).");
            return;
        }

        log.info("Dear '" + passengerName + "', you could not book " + noOfSeatsToBook + " seat(s) since there is only " + availableTickets + " seat(s) remained!");

    }

}
