package model;

import java.util.regex.Pattern;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String emailRegEx = "^(.+)@(.+).(.+)$";

    public Customer(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        Pattern pattern = Pattern.compile(emailRegEx);

        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("The email has to be of valid format!");
        }
    }

   @Override
   public String toString(){
        return "First Name: " + firstName + ", Last Name: " + lastName + ", E-Mail: " + email;
   }
}
