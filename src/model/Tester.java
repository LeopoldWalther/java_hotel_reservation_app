package model;

public class Tester {

    public static void main(String[] args) {

        Customer validCustomer = new Customer("Leopold", "Walther", "Leopold.Walther@gmail.com");
        System.out.println(validCustomer);

        try {
            Customer invalidCustomer = new Customer("Paula", "Marin Villar", "PaulaMarinVillar");
        } catch (IllegalArgumentException ex){
            System.out.println(ex);
        }
    }
}
