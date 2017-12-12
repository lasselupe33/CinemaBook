package com.cinemaBook.data;

import com.cinemaBook.model.Customer;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * This singleton data handler has the responsibility to manage all connection between the database in regards to customers
 */
public class CustomerDataHandler extends DataHandler {
    // Store a reference to the dataHandler
    private static final CustomerDataHandler instance = new CustomerDataHandler();

    /**
     * Call dataHandler constructor to create a connection to the database
     */
    public CustomerDataHandler() {
        super();
    }

    // Returns a reference to the screeningsDataHandler
    public static final CustomerDataHandler getInstance() { return instance; }

    /**
     * Internal helper that returns the id of a customer
     * @param customer
     * @return The id of the customer. Will return "-1" if the customer doesn't exist in the database
     */
    public int getCustomerId(Customer customer) {
        try {
            String query = "SELECT customer_id FROM Customers WHERE " +
                    "customer_name = '" + customer.getName() + "' AND " +
                    "customer_phone = '" + customer.getPhone() + "' AND " +
                    "customer_email = '" + customer.getEmail() + "';";

            ResultSet rs = this.createStatement().executeQuery(query);

            int customer_id;

            // If a user haven't been found, then set the customer id to "-1", i.e. not existing
            if (!rs.next()) {
                customer_id = -1;
            } else {
                // Else, retrieve the customerID
                customer_id = rs.getInt("customer_id");
            }

            return customer_id;
        } catch (Exception e) {
            throw new Error("Failed to retrieve customer id! " + e.getMessage());
        }
    }

    /**
     * Internal helper that inserts a customer to a database (if the customer doesn't exist yet), and then returns the
     * customer id
     *
     * @param customer The customer object
     * @return The id of the inserted customer
     */
    public int insertCustomer(Customer customer) {
        try {
            // Get customer id
            int customer_id = getCustomerId(customer);

            // Only insert customer if he/she doens't exist already in the database
            if (customer_id == -1) {
                HashMap<String, String> customerMap = new HashMap<>();
                customerMap.put("customer_name", customer.getName());
                customerMap.put("customer_phone", customer.getPhone());
                customerMap.put("customer_email", customer.getEmail());
                this.insertData("Customers", customerMap);

                // Create a new statement to get the id of the newly inserted customer
                ResultSet idResultSet = this.createStatement().executeQuery("SELECT LAST_INSERT_ID() id");
                idResultSet.next();

                // Return the id of the newly inserted customer
                return idResultSet.getInt("id");
            } else {
                // Else just return the customer id
                return customer_id;
            }

        } catch (Exception e) {
            throw new Error("Error while inserting user to database... " + e.getMessage());
        }
    }
}
