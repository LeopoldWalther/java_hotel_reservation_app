package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService SINGLETON = new CustomerService();
    private final Map<String, Customer> customers = new HashMap<String, Customer>(); //Use map to be able to find objects rapidly

    private CustomerService(){}

    public static CustomerService getSingleton(){ //Singleton for Service Class
        return SINGLETON;
    }
    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(firstName, lastName, email);
        if (customers.containsKey(email)){
            throw new IllegalArgumentException("There is already a Customer registere with this email: " + email);
        }
        else {
            customers.put(email, customer);
        }
    }
    public Customer getCustomer(String customerEmail){
        if (customers.containsKey(customerEmail)) {
            return customers.get(customerEmail);
        } else {
            return null;
        }
    }
    public Collection<Customer> getAllCustomers(){
        return customers.values();
    }
}
