package schedulingApplication.Models;

public class Customer {
    private String name;
    private String phoneNumber;
    private String address;
    private int id;
    private int addressId;

    public Customer(int id, String name, String phoneNumber, String address, int addressId) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
    
    public int getId(){
        return id;
    }
    
    public int getAddressId(){
        return addressId;
    }
    
}
