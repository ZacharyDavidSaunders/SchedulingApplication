
package schedulingApplication.Models;

import java.sql.SQLException;
import java.util.ArrayList;

public class LocalData {
    private static ArrayList<User> users;
    private static User currentUser = null;
    private static ArrayList<Customer> customers;
    private static ArrayList<Appointment> appointments;
    
    public static void refreshLocalUsers() throws SQLException{
        users = Database.getUsers();
        System.out.println("LocalUsers refreshed!");
    }
    
    public static void refreshLocalCustomers(){
        customers = Database.getCustomers();
        System.out.println("LocalCustomers refreshed!");
    }    
    
    public static void refreshLocalAppointments(){
        appointments = Database.getAppointments(Integer.valueOf(currentUser.getId()));
        System.out.println("LocalAppointments refreshed!");
    }    
    
    public static void setCurrentUser(User currentUser){
        LocalData.currentUser = currentUser;
    }
    
    public static User getCurrentUser(){
        return currentUser;
    }
    
    public static ArrayList<User> getUsers(){
        return users;
    }
    
    public static ArrayList<Customer> getCustomers(){
        return customers;
    }
    
    public static ArrayList<Appointment> getAppointments(){
        return appointments;
    }
}
