package schedulingApplication.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import schedulingApplication.Models.Customer;
import schedulingApplication.Models.Database;
import schedulingApplication.Models.LocalData;

public class CustomersController implements Initializable {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer,String> nameColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> phoneColumn;
    @FXML private Label errorLabel;
    
    private URL url;
    private ResourceBundle rb;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        LocalData.refreshLocalCustomers();
        
        this.url = url;
        this.rb = rb;
        nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        customerTable.getItems().setAll(populateCustomerTable());
    }  
    
    @FXML
    public void onReturnToYourDashboardButtonPress(ActionEvent event) throws IOException{
        Parent dashboardParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Dashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        dashboardScene.getRoot().requestFocus();
        window.show();
    }
    
    @FXML
    public void onDeleteButtonPress(ActionEvent event){
        if(customerTable.getSelectionModel().getSelectedItem() != null){
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            Database.deleteCustomer(customer);
            initialize(url, rb);
        }else{
            tellUserToSelectRow();
        }    
    }  
    
    @FXML 
    public void onAddButtonPress(ActionEvent event) throws IOException{
        Parent addCustomerParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/AddCustomer.fxml"));
        Scene addCustomerScene = new Scene(addCustomerParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addCustomerScene);
        addCustomerScene.getRoot().requestFocus();
        window.show();
    }
    
    @FXML 
    public void onModifyButtonPress(ActionEvent event) throws IOException{
        if(customerTable.getSelectionModel().getSelectedItem() != null){
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            ModifyCustomerController.setCustomerToModify(customer);
            
            Parent modifyCustomerParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/ModifyCustomer.fxml"));
            Scene modifyCustomerScene = new Scene(modifyCustomerParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyCustomerScene);
            modifyCustomerScene.getRoot().requestFocus();
            window.show();
        }else{
            tellUserToSelectRow();
        }   
    }    
    
    private List<Customer> populateCustomerTable(){
        ArrayList<Customer> list = new ArrayList<Customer>();
        for(int i = 0; i < LocalData.getCustomers().size(); i++){
            list.add(LocalData.getCustomers().get(i));
        }
        return list;
    }
    
    private void tellUserToSelectRow(){
        errorLabel.setVisible(true);
    }
     
}
