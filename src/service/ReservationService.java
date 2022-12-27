package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static final ReservationService SINGLETON = new ReservationService();

    private final Set<Reservation> reservations = new HashSet<Reservation>(); //Set to make duplicate reservations impossible;
    private final Map<String, IRoom> rooms = new HashMap<String, IRoom>(); //Map to find rooms rapidly;
    private ReservationService(){}
    public static ReservationService getSingleton() {
        return SINGLETON;
    }

    public void addRoom(IRoom room){
        if (rooms.containsKey(room.getRoomNumber())){
            throw new IllegalArgumentException("Room number " + room.getRoomNumber() + " already exists.");
        } else {
            rooms.put(room.getRoomNumber(), room);
        }
    }

    public IRoom getARoom(String roomID){
        if (rooms.containsKey(roomID)) {
            return rooms.get(roomID);
        } else {
            throw new IllegalArgumentException("There is no room with room number " + roomID);
        }
    }

    public Map<String, IRoom> getAllRooms(){
        return rooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        if (reservations.contains(reservation)) {
            throw new IllegalArgumentException("This room is already reserved during these dates.");
        }
        reservations.add(reservation);
        return reservation;
    }
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){

        Map<String, IRoom> freeRooms = new HashMap<String, IRoom>(rooms);

        for (Reservation reservation : reservations) {

            if (checkOutDate.before(reservation.getCheckInDate()) ||
                checkInDate.after(reservation.getCheckOutDate())){
                //Do nothing if reservation has no collusion with dates

            } else {
                freeRooms.remove(reservation.getRoom().getRoomNumber());
            }
        }
        return new ArrayList<IRoom>(freeRooms.values());
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){

        List<Reservation> customersReservation = new ArrayList<Reservation>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)){
                customersReservation.add(reservation);
            }
        }
        return customersReservation;
    }
    public void printAllReservation(){

        for (Reservation reservation : reservations){
            System.out.println(reservation.toString());
        }
    }
}
