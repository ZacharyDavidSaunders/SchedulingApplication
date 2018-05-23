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

public class MonthlyAppointmentTypesReportController implements Initializable {
    
    @FXML private Label consultationValueLabel;
    @FXML private Label statusUpdateValueLabel;
    @FXML private Label otherValueLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            consultationValueLabel.setText(Database.getNumberOfAppointmentsByType("Consultation"));
            statusUpdateValueLabel.setText(Database.getNumberOfAppointmentsByType("Status Update"));
            otherValueLabel.setText(Database.getNumberOfAppointmentsByType("Other"));
        }catch (Exception e){
            System.out.println("ERROR when getting the number of report types from DB. "+e);
        }
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
