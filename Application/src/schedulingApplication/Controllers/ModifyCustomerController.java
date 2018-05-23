package schedulingApplication.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedulingApplication.Models.Customer;
import schedulingApplication.Models.Database;

public class ModifyCustomerController implements Initializable {

    private static Customer customerToBeModified = null;
    
    @FXML private TextField nameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField address2TextField;
    @FXML private TextField cityTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField countryTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("customerToBeModified.getName="+customerToBeModified.getName()+"customerToBeModified.getAddressId="+customerToBeModified.getAddressId());
        
        nameTextField.setText(customerToBeModified.getName());
        phoneNumberTextField.setText(customerToBeModified.getPhoneNumber());
        addressTextField.setText(Database.getAddress(customerToBeModified.getAddressId()));
        address2TextField.setText(Database.getAddress2(customerToBeModified.getAddressId()));
        
        cityTextField.setText((Database.getCity(Database.getCityId(customerToBeModified.getAddressId()))));
        postalCodeTextField.setText(Database.getPostalCode(customerToBeModified.getAddressId()));
        countryTextField.setText(Database.getCountry(Database.getCityId(customerToBeModified.getAddressId())));
    }    
    
    @FXML
    public void onCancelButtonPress(ActionEvent event) throws IOException{
        goToCustomers(event);
    }

    @FXML
    public void onSaveButtonPress(ActionEvent event) throws IOException{
        String newName = nameTextField.getText();
        String newPhoneNumber = phoneNumberTextField.getText();
        String newAddress = addressTextField.getText();
        String newAddress2 = address2TextField.getText();
        String newCity = cityTextField.getText();
        String newPostalCode = postalCodeTextField.getText();
        String newCountry = countryTextField.getText();
        Database.updateCustomer(customerToBeModified.getId(), customerToBeModified.getAddressId(), newName, newPhoneNumber, newAddress, newAddress2, newCity, newPostalCode, newCountry);
        goToCustomers(event);
    }    
    
    public static void setCustomerToModify(Customer customer){
        customerToBeModified = customer;
    }    
    
    private void goToCustomers(ActionEvent event) throws IOException{
        Parent customerParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Customers.fxml"));
        Scene customerScene = new Scene(customerParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(customerScene);
        customerScene.getRoot().requestFocus();
        window.show();
    }    
    
}
