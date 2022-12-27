import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Reservation;
import service.ReservationService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MainMenu {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static HotelResource hotelResource = HotelResource.getSingleton();

    private static final Scanner scanner = new Scanner(System.in);

    public static void MainMenu(){


        String userInput = "";
        printMainMenu();
        try{
            do{
                userInput = scanner.nextLine();

                if (userInput.length() == 1) {
                    switch (userInput.charAt(0)) {
                        case '1':
                            findAndReserveRoom();
                            break;
                        case '2':
                            seeMyReservation();
                            break;
                        case '3':
                            createAccount();
                            break;
                        case '4':
                            AdminMenu.adminMenu();
                            break;
                        case '5':
                            System.out.println("Exit");
                            break;
                        default:
                            System.out.println("Unknown action\n");
                            printMainMenu();
                            break;
                    }
                }

            } while (userInput.charAt(0) != '5' || userInput.length() != 1);

        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Empty Input received, exiting program.");
        }
    }

    public static void printMainMenu(){
        System.out.println("\nWelcome to the Hotel Reservation App\n" +
                "--------------------------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an Account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "--------------------------------------------------\n" +
                "Please select a number for the menu option:\n");
    }

    public static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);
        Date checkInDate = null;
        Date checkOutDate = null;

        System.out.println("Enter Check-In Date dd/mm/yyyy");
        try {
            checkInDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Invalid date" + ex);
        }
        System.out.println("Enter Check-Out Date dd/mm/yyyy");
        try {
            checkOutDate = dateFormat.parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Invalid date" + ex);
        }

        if (checkInDate != null && checkOutDate != null) {
            Collection<IRoom> freeRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            if (freeRooms.isEmpty()) {
                System.out.println("No rooms available for given dates.");

                checkInDate = moveDate(checkInDate);
                checkOutDate = moveDate(checkOutDate);

                freeRooms = hotelResource.findARoom(checkInDate, checkOutDate);

                System.out.println("You can reserve the following rooms from " + checkInDate + " to " + checkOutDate);
            }
            printRooms(freeRooms);

            
            System.out.println("Would you like to book a room? y/n");
            String bookARoom = scanner.nextLine();
            if (bookARoom.length() == 1 && bookARoom.charAt(0) == 'y') {
                System.out.println("Do you have an account? y/n");
                String existingAccount = scanner.nextLine();

                if (existingAccount.length() == 1 && existingAccount.charAt(0) == 'y') {
                    System.out.println("Enter your email account (name@domain.com)");
                    String customerEmail = scanner.nextLine();
                    String emailRegex = "^(.+)@(.+).(.+)$";
                    Pattern pattern = Pattern.compile(emailRegex);

                    if (pattern.matcher(customerEmail).matches() == true) {
                        if (hotelResource.getCustomer(customerEmail) == null) {
                            System.out.println("Customer not found, please create a new account.");
                            createAccount();
                        } else {
                            System.out.println("What room number would you like to reserve?");
                            String roomNumber = scanner.nextLine();
                            for (IRoom room : freeRooms) {
                                if (room.getRoomNumber() == roomNumber) {
                                } else {
                                    System.out.println("Room not available.");


                                }
                            }
                            IRoom desiredRoom = hotelResource.getRoom(roomNumber);
                            Reservation reservation = hotelResource.bookARoom(customerEmail, desiredRoom, checkInDate, checkOutDate);
                            System.out.println("Reservation successfully:");
                            System.out.println(reservation);
                        }
                        printMainMenu();
                    } else {
                        System.out.println("Not a valid Email format.");
                        printMainMenu();
                    }
                } else if (existingAccount.length() == 1 && existingAccount.charAt(0) == 'n') {
                    System.out.println("Create account:");
                    createAccount();
                } else {
                    System.out.println("Invalid Input A");
                    printMainMenu();
                }
            } else if (bookARoom.length() == 1 && bookARoom.charAt(0) == 'n') {
                printMainMenu();
            } else {
                System.out.println("Invalid Input B");
                printMainMenu();
            }
        } else {
            System.out.println("Invalid Input C");
            printMainMenu();
        }
    }

    public static void seeMyReservation() {
        System.out.println("Enter your email account (name@domain.com)");
        String customerEmail = scanner.nextLine();
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (pattern.matcher(customerEmail).matches() == true) {
            if (hotelResource.getCustomer(customerEmail) == null) {
                System.out.println("Customer not found, please create a new account.");
                createAccount();
            } else {
                Collection<Reservation> reservations = hotelResource.getCustomersReservations(customerEmail);
                if (reservations.isEmpty() || reservations == null){
                    System.out.println("No reservations for this customer.");
                } else {
                    for (Reservation reservation : reservations) {
                        System.out.println(reservation.toString());
                    }
                }
            }
        }
    }

    public static void createAccount() {
        System.out.println("Enter your email account (name@domain.com): ");
        String customerEmail = scanner.nextLine();

        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(customerEmail, firstName, lastName);
            System.out.println("Account created successfully.");
            printMainMenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }
    }

    public static Date moveDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);
        Date movedDate = calendar.getTime();

        return movedDate;
    }

    public static void printRooms(Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (IRoom room : rooms) {
                System.out.println(room.toString());
            }
        }
    }

}
