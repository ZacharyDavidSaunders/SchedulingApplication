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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import schedulingApplication.Models.Database;

public class NumberOfCustomersReportController implements Initializable {

    @FXML private Label numberOfCustomers;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberOfCustomers.setText(String.valueOf(Database.getCustomers().size()));
    }    
    
    @FXML 
    public void onReturnToDashboardButtonPress(ActionEvent event) throws IOException{
        Parent dashboardParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Dashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        dashboardScene.getRoot().requestFocus();
        window.show();
    }    
}
