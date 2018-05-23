package schedulingApplication.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import schedulingApplication.Models.Database;
import schedulingApplication.Models.LocalData;

public class AddCustomerController implements Initializable {

    @FXML private TextField nameTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField address2TextField;
    @FXML private TextField cityTextField;
    @FXML private TextField postalCodeTextField;
    @FXML private TextField countryTextField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    @FXML 
    public void onCancelButtonPress(ActionEvent event) throws IOException{
        goToCustomers(event);
    }  
    
    @FXML 
    public void onSaveButtonPress(ActionEvent event) throws IOException, SQLException{
        String name = nameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String address = addressTextField.getText();
        String address2 = address2TextField.getText();
        String city = cityTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String country = countryTextField.getText();
        int customerId = Database.getNextCustomerId();
        int addressId = Database.getNextAddressId();
        int cityId = Database.getNextCityId();
        int countryId = Database.getNextCountryId();
        
        Database.addCustomerToDatabase(customerId, name, address, address2, city, postalCode, country, addressId);
        Database.addAddressToDatabase(addressId, address, address2, cityId, postalCode, phoneNumber);
        Database.addCityToDatabase(cityId, city, countryId);
        Database.addCountryToDatabase(countryId, country);
        
        LocalData.refreshLocalCustomers();
        
        goToCustomers(event);
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
