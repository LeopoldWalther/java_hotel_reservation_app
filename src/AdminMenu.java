import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getSingleton();
    private static final Scanner scanner = new Scanner(System.in);

    public static void adminMenu() {
        String userInput = "";
        printAdminMenu();

        try{
            do{
                userInput = scanner.nextLine();

                if (userInput.length() == 1) {
                    switch (userInput.charAt(0)) {
                        case '1':
                            printAllCustomers();
                            break;
                        case '2':
                            printAllRooms();
                            break;
                        case '3':
                            printAllReservations();
                            break;
                        case '4':
                            addARoom();
                            break;
                        case '5':
                            MainMenu.MainMenu();
                            break;
                        default:
                            System.out.println("Unknown action\n");
                            printAdminMenu();
                            break;
                    }
                }

            } while (userInput.charAt(0) != '5' || userInput.length() != 1);

        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println("Empty Input received, exiting program.");
        }

    }

    private static void printAdminMenu() {
        System.out.print("\nAdmin Menu\n" +
                "--------------------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------------------------\n" +
                "Please select a number for the menu option:\n");
    }


    private static void printAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers.");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer.toString());
            }
        }
    }

    private static void printAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if(rooms.isEmpty()) {
            System.out.println("No rooms.");
        } else {
            for (IRoom room : rooms) {
                System.out.println(room.toString());
            }
        }
    }

    private static void printAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addARoom() {
        String userInput = "y";
        List<IRoom> rooms = new ArrayList<IRoom>();

        while (userInput.charAt(0) == 'y') {

            System.out.println("Enter room number: ");
            String roomNumber = scanner.nextLine();

            Double pricePerNight = null;
            System.out.println("Enter price per night: ");
            try {
                pricePerNight = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid format for room price, decimals have to be separated by a point (.)");
            }

            RoomType roomType = null;
            System.out.println("Enter room type, 1 = single bed, 2 = double bed: ");
            try {
                roomType = RoomType.valueOfLabel(scanner.nextLine());
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid room type, type in 1 for single bed and 2 for double bed.");
            }

            Room room = new Room(roomNumber, pricePerNight, roomType, true);
            rooms.add(room);

            System.out.println("Do you want to add another room?");
            userInput = scanner.nextLine();
        }
        adminResource.addRoom(rooms);
        printAdminMenu();
    }
}
