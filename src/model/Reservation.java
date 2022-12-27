package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckInDate(){
        return checkInDate;
    }

    public  Date getCheckOutDate(){
        return checkOutDate;
    }

    public IRoom getRoom(){
        return room;
    }

    public Customer getCustomer(){
        return customer;
    }

    @Override
    public String toString(){
        return "Customer: "+ customer + ", Room: " + room + ", Check In: " + checkInDate + "Check Out: " + checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return room.equals(reservation.room) && checkInDate.equals(reservation.checkInDate) && checkOutDate.equals(reservation.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room);
    }
}
