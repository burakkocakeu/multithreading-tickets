package com.burakkocak.multithreading.tickets.book;

public class TicketBookingThread extends Thread {

    private TicketCounter counter;
    private String passengerName;
    private String passengerSurname;
    private int noOfSeatsToBook;


    public TicketBookingThread(TicketCounter counter, String passengerName, String passengerSurname, int noOfSeatsToBook) {
        this.counter = counter;
        this.passengerName = passengerName;
        this.noOfSeatsToBook = noOfSeatsToBook;
        this.passengerSurname = passengerSurname;
    }

    @Override
    public void run() {
        counter.bookTicket(passengerName, passengerSurname, noOfSeatsToBook, "06-09-2021", "izmir");
    }
}
