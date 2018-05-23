package schedulingApplication.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import schedulingApplication.Controllers.LoginController;

public class Database {
    
    private static Connection conn = null;
    private static String databaseConnector = "com.mysql.jdbc.Driver";
    private static String databaseName = "U04J79";
    private static String url = "jdbc:mysql://52.206.157.109/"+databaseName;
    private static String username = "U04J79";
    private static String password = "53688254921";
    private static String defaultUser = "\""+"admin"+"\"";
    
    public static void connectToDatabase() throws ClassNotFoundException{
        try {
            Class.forName(databaseConnector);
            conn = DriverManager.getConnection(url,username,password); 
            System.out.println("Connected to database : " + databaseName);
        } catch (SQLException e) { 
            LoginController.unableToConnectToDatabase();
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState()); 
            System.out.println("VendorError: "+e.getErrorCode());
        } 
    }    

    public static ArrayList<User> getUsers() throws SQLException{
        ArrayList<User> users = new ArrayList<User>();
        try{
           Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");
            while(rs.next()){
                String userId = rs.getString("userId");
                String username = rs.getString("userName");
                String password = rs.getString("password");
                String active = rs.getString("active");
                String createdDate = rs.getString("createDate");
                String createdBy = rs.getString("createBy");
                String lastUpdate = rs.getString("lastUpdate");
                String lastUpdatedBy = rs.getString("lastUpdatedBy");
                User user = new User(userId, username, password, active, createdDate, createdBy, lastUpdate, lastUpdatedBy);
                users.add(user);
            }       
        }catch (Exception e){
            System.out.println("getUsers Exception: "+e);
        }
        return users;
    }
    
    public static ArrayList<Customer> getCustomers(){
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try{
           Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer");
            while(rs.next()){
                String name = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                String address = getAddress(addressId).concat(", "+getAddress2(addressId)+", "+getCity(getCityId(addressId))+", "+getPostalCode(addressId)+", "
                        +getCountry(getCountryId((getCityId(addressId))))+".");
                String phoneNumber = getPhone(addressId);
                int id = rs.getInt("customerId");
                Customer customer = new Customer(id, name, phoneNumber, address, addressId);
                customers.add(customer);
            }       
        }catch (Exception e){
            System.out.println("getCustomers Exception: "+e);
        }
        return customers;
    }
    
    public static int getCityId(int addressId){
        int cityId = 0;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE addressId = "+addressId+";");
            while(rs.next()){
                cityId = rs.getInt("cityId");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return cityId;
    }
    
    public static String getCity(int cityId){
        String city = null;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM city WHERE cityId = "+cityId+";");
            while(rs.next()){
                city = rs.getString("city");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return city;
    }
    
    public static String getAddress(int addressId){
        String address = null;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE addressId = "+addressId+";");
            while(rs.next()){
                address = rs.getString("address");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return address;
    }
    
    public static String getAddress2(int addressId){
        String address2 = null;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE addressId = "+addressId+";");
            while(rs.next()){
                address2 = rs.getString("address2");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return address2;
    }
    
    public static String getPostalCode(int addressId){
        String postalCode = null;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE addressId = "+addressId+";");
            while(rs.next()){
                postalCode = rs.getString("postalCode");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return postalCode;
    }
    
    public static String getCountry (int countryId){
        String country = null;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM country WHERE countryId = "+countryId+";");
            while(rs.next()){
                country = rs.getString("country");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return country;
    }    
    
    public static int getCountryId (int cityId){
        int countryId = 0;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM city WHERE cityId = "+cityId+";");
            while(rs.next()){
                countryId = rs.getInt("countryId");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return countryId;
    }    
    
    public static String getPhone (int addressId){
        String phone = null;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM address WHERE addressId = "+addressId+";");
            while(rs.next()){
                phone = rs.getString("phone");
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return phone;
    }    
     
    public static ArrayList<Appointment> getAppointments(int userId){
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointment WHERE createdBy = "+userId+ " ;");
            while(rs.next()){
                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String contact = rs.getString("contact");
                String url = rs.getString("url");
                String startTime = rs.getString("start");
                String day = rs.getString("createDate");
                String endTime = rs.getString("end");
                String customerName = getCustomerName(customerId);
                
                String startingHour = getStartingHour(startTime);
                String startingMinute = getStartingMinute(startTime);
                String endingHour = getEndingHour(endTime);
                String endingMinute = getEndingMinute(endTime);
                Appointment appointment = new Appointment(appointmentId, customerName, customerId, userId, title, location, contact, description, url, startTime, endTime, day, startingHour, startingMinute, endingHour, endingMinute);
                appointments.add(appointment);
            }
            
        }catch (Exception e){
            System.out.println("getAppointments Exception: "+e);
        }   
        return appointments;
    }
    
    public static void deleteCustomer(Customer customer){
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM customer WHERE customerId = "+customer.getId()+";");
            LocalData.refreshLocalCustomers();
        }catch (Exception e){
            System.out.println("deleteCustomer Exception: "+e);
        }         
    }
    
    public static int getNextCustomerId() throws SQLException{
        int nextCustId = 0;
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(customerId) FROM customer;");
        while(rs.next()){
            nextCustId = rs.getInt(1);
        }
        return nextCustId++;
    }
    
    public static int getNextAddressId() throws SQLException{
        int nextAddId = 0;
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(addressId) FROM address;");
        while(rs.next()){
            nextAddId = rs.getInt(1);
        }
        return nextAddId++;
    }
    
    public static int getNextCityId() throws SQLException{
        int nextCityId = 0;
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(cityId) FROM city;");
        while(rs.next()){
            nextCityId = rs.getInt(1);
        }
        return nextCityId++;
    }
    
    public static int getNextCountryId() throws SQLException{
        int nextCountryId = 0;
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(countryId) FROM country;");
        while(rs.next()){
            nextCountryId = rs.getInt(1);
        }
        return nextCountryId++;
    }
    
    public static int getNextAppointmentId() throws SQLException{
        int nextAppointmentId = 0;
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM appointment;");
        while(rs.next()){
            if(rs.getInt("appointmentId") > nextAppointmentId){
                nextAppointmentId = rs.getInt("appointmentId");
            }
            System.out.println("rs.getInt(\"appointmentId\"): "+rs.getInt("appointmentId")+" nextAppointmentId: "+nextAppointmentId);
        }
        nextAppointmentId++;
        return nextAppointmentId;
    }
    
    public static void addCustomerToDatabase(int customerId, String name, String address, String address2, String city, String postalCode, String country, int addressId){
        name = "\""+name+"\"";
        address = "\""+address+"\"";
        address2 = "\""+address2+"\"";
        city = "\""+city+"\"";
        postalCode = "\""+postalCode+"\"";
        country = "\""+country+"\"";
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO customer VALUES("
                    +(customerId)+", "
                    +(name)+", " 
                    +(addressId)+", "
                    +(1)+", "
                    +("CURRENT_DATE")+", "
                    +(defaultUser)+", " 
                    +("CURRENT_TIMESTAMP")+", " 
                    +(defaultUser)        
                    +");");
        }catch (Exception e){
            System.out.println("AddCustomer Exception:"+e);
        }
    }
    
    public static void addAddressToDatabase(int addressId, String address, String address2, int cityId, String postalCode, String phoneNumber){
        
        address = "\""+address+"\"";
        address2 = "\""+address2+"\"";
        postalCode = "\""+postalCode+"\"";
        phoneNumber = "\""+phoneNumber+"\"";
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO address VALUES("
                    +(addressId)+", "
                    +(address)+", "
                    +(address2)+", " 
                    +(cityId)+", "
                    +(postalCode)+", "
                    +(phoneNumber)+", "
                    +("CURRENT_DATE")+", "
                    +(defaultUser)+", "  
                    +("CURRENT_TIMESTAMP")+", "
                    +(defaultUser)       
                    +");");
        }catch (Exception e){
            System.out.println("AddAddress Exception:"+e);
        }
    }
    
    public static void addCityToDatabase(int cityId, String city, int countryId){
        
        city = "\""+city+"\"";
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO city VALUES("
                    +(cityId)+", "
                    +(city)+", " 
                    +(countryId)+", "
                    +("CURRENT_DATE")+", "
                    +(defaultUser)+", "
                    +("CURRENT_TIMESTAMP")+", "
                    +(defaultUser)       
                    +");");
        }catch (Exception e){
            System.out.println("AddCity Exception:"+e);
        }
    }
    
    public static void addCountryToDatabase(int countryId, String country){
        
        country = "\""+country+"\"";
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO country VALUES("
                    +(countryId)+", "
                    +(country)+", " 
                    +("CURRENT_DATE")+", "
                    +(defaultUser)+", " 
                    +("CURRENT_TIMESTAMP")+", "
                    +(defaultUser)        
                    +");");
        }catch (Exception e){
            System.out.println("AddCountry Exception:"+e);
        }
    }
    
    public static void updateCustomer(int CustomerId, int addressId, String newName, String newPhoneNumber, String newAddress, String newAddress2, String newCity, String newPostalCode, String newCountry){
        
        newName = "\""+newName+"\"";
        newPhoneNumber = "\""+newPhoneNumber+"\"";
        newAddress = "\""+newAddress+"\"";
        newAddress2 = "\""+newAddress2+"\"";
        newCity = "\""+newCity+"\"";
        newPostalCode = "\""+newPostalCode+"\"";
        newCountry = "\""+newCountry+"\"";
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE customer SET "+
                    "customerName = "+newName+ ", "+
                    "lastUpdate = CURRENT_TIMESTAMP " +
                        "WHERE customerId = "+CustomerId); 

        }catch (Exception e){
            System.out.println("updateCustomer method, updating customer table.\n  Exception:"+e);
        }
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE address SET "+
                    "address = "+newAddress+ ", "+
                    "address2 = "+newAddress2+ ", "+ 
                    "postalCode = "+newPostalCode+ ", "+    
                    "phone = "+newPhoneNumber+ ", "+        
                    "lastUpdate = CURRENT_TIMESTAMP " +
                        "WHERE addressId = "+addressId); 

        }catch (Exception e){
            System.out.println("updateCustomer method while updating address table.\n Exception:"+e);
        }
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE city SET "+
                    "city = "+newCity+ ", "+
                    "lastUpdate = CURRENT_TIMESTAMP " +
                        "WHERE cityId = "+getCityId(addressId)); 

        }catch (Exception e){
            System.out.println("updateCustomer method while updating city table.\n Exception:"+e);
        }
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE country SET "+
                    "country = "+newCountry+ ", "+
                    "lastUpdate = CURRENT_TIMESTAMP " +
                        "WHERE countryId = "+getCountryId(getCityId(addressId))); 

        }catch (Exception e){
            System.out.println("updateCustomer method while updating country table.\n Exception:"+e);
        }
        
        LocalData.refreshLocalCustomers();
    }
    
    public static String getCustomerName (int customerId) throws SQLException{
        String customerName = "";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE customerId = "+customerId+";");
        while(rs.next()){
            customerName = rs.getString("customerName");
        }
        return customerName;
    }
    
    public static int getCustomerId (String name) throws SQLException{
        
        //name = "\""+name+"\"";
        System.out.println("customerName= "+name);
        
        int customerId = 0;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE customerName = "+name+";");
        while(rs.next()){
            customerId = rs.getInt("customerId");
        }
        return customerId;
    }
    
    public static void addAppointment(String title, String customerName, String description, String location, String contact, String url, int day, int startHour, int startMin, int endHour, int endMin) throws SQLException{
        title = "\""+title+"\"";
        customerName = "\""+customerName+"\"";
        description = "\""+description+"\"";
        location = "\""+location+"\"";
        contact = "\""+contact+"\"";
        url = "\""+url+"\"";
        
        
        int userId = Integer.parseInt(LocalData.getCurrentUser().getId());
        int customerId = getCustomerId(customerName);
        int appointmentId = getNextAppointmentId();
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO appointment VALUES("
                    +(appointmentId)+", "
                    +(customerId)+", " 
                    +(title)+", "
                    +(description)+", " 
                    +(location)+", "
                    +(contact)+", "
                    +(url)+", "
                    +"\""+("2018-04-00 "+startHour+":"+startMin+":00")+"\""+", "
                    +"\""+("2018-04-00 "+endHour+":"+endMin+":00")+"\""+", "
                    +"\""+("2018-04-"+day+" "+00+":"+00+":00")+"\""+", "
                    +(userId)+", "
                    +("CURRENT_TIMESTAMP")+", "
                    +(defaultUser)        
                    +");");
        }catch (Exception e){
            System.out.println("addAppointment method.\n Exception:"+e);
        }
    }
    
    public static void deleteAppointment(int appointmentId){
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM appointment WHERE appointmentId = "+appointmentId+";");
            LocalData.refreshLocalAppointments();
        }catch (Exception e){
            System.out.println("deleteCustomer Exception: "+e);
        }   
    }
    
    public static void modifyAppointment(int appointmentId, String title, String customerName, String description, String location, String contact, String url, int day, int startHour, int startMin, int endHour, int endMin) throws SQLException{
        title = "\""+title+"\"";
        customerName = "\""+customerName+"\"";
        description = "\""+description+"\"";
        location = "\""+location+"\"";
        contact = "\""+contact+"\"";
        url = "\""+url+"\"";
        
        int customerId = getCustomerId(customerName);
        
        try{
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE appointment SET "+
                    "customerId = "+customerId+ ", "+
                    "title = "+title+ ", "+
                    "description = "+description+ ", "+
                    "location = "+location+ ", "+
                    "contact = "+contact+ ", "+
                    "url = "+url+ ", "+
                    "start = "+"\""+("2018-04-00 "+startHour+":"+startMin+":00")+"\""+ ", "+
                    "end = "+"\""+("2018-04-00 "+endHour+":"+endMin+":00")+"\""+ ", "+
                    "createDate = "+"\""+("2018-04-"+day+" "+00+":"+00+":00")+"\""
                        +"WHERE appointmentId = "+appointmentId);
        }catch (Exception e){
            System.out.println("modifyCustomer Exception: "+e);
        }   
    }
    
    private static String getStartingHour(String start){
        return start.substring(11, 13);
    }
    
    private static String getStartingMinute(String start){
        
        return start.substring(14, 16);
    }
    
    private static String getEndingHour(String end){
        return end.substring(11, 13);
    }
    
    private static String getEndingMinute(String end){
        
        return end.substring(14, 16);
    }
    
    public static String getNumberOfAppointmentsByType(String type) throws SQLException{
        
        type = "\""+type+"\"";
        
        int numberOfAppointments = 0;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(appointmentId) FROM appointment WHERE description= "+type+";");
        while(rs.next()){
            numberOfAppointments = rs.getInt(1);
        }
        return String.valueOf(numberOfAppointments);
    }
    
    public static int getUserIdByUsername(String userName) throws SQLException{
        userName = "\""+userName+"\"";
        int userId = 0;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM user WHERE userName= "+userName+";");
        while(rs.next()){
            userId = rs.getInt("userId");
        }
        return userId;
    }
    
    public static void closeConnection() throws SQLException{
        if(conn != null){
            conn.close();
        }
        System.out.println("DATABASE CONNECTION HAS BEEN CLOSED!");
    }
}    
