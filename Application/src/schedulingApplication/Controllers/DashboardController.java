package schedulingApplication.Controllers;

import java.awt.Desktop;
import java.io.File;
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
import javafx.stage.Stage;
import schedulingApplication.Models.FileManipulation;
import schedulingApplication.Models.LocalData;


public class DashboardController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    @FXML
    public void onViewAppointmentsButtonPress(ActionEvent event) throws IOException{
        Parent appointmentsParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Appointments.fxml"));
        Scene appointmentsScene = new Scene(appointmentsParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(appointmentsScene);
        appointmentsScene.getRoot().requestFocus();
        window.show();
    }    
    
    @FXML
    public void onLogoffButtonPress(ActionEvent event) throws IOException{
        LocalData.setCurrentUser(null);
        Parent loginParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Login.fxml"));
        Scene loginScene = new Scene(loginParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        loginScene.getRoot().requestFocus();
        window.show();
    }  
    
    @FXML 
    public void onViewCustomerButtonPress(ActionEvent event) throws IOException{
        Parent customerParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Customers.fxml"));
        Scene customerScene = new Scene(customerParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(customerScene);
        customerScene.getRoot().requestFocus();
        window.show();
    }    
    
    @FXML 
    public void onNumberOfCustomersReportButtonPress(ActionEvent event) throws IOException{
        Parent numberOfCustomersReportParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/NumberOfCustomersReport.fxml"));
        Scene numberOfCustomersScene = new Scene(numberOfCustomersReportParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(numberOfCustomersScene);
        numberOfCustomersScene.getRoot().requestFocus();
        window.show();
    }  
    
    @FXML 
    public void onMonthlyAppointmentTypesReportButtonPress(ActionEvent event) throws IOException{
        Parent monthlyAppointmentTypesReportParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/MonthlyAppointmentTypesReport.fxml"));
        Scene monthlyAppointmentTypesReport = new Scene(monthlyAppointmentTypesReportParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(monthlyAppointmentTypesReport);
        monthlyAppointmentTypesReport.getRoot().requestFocus();
        window.show();
    }  
    
    @FXML 
    public void onConsultantSchedulesButtonPress(ActionEvent event) throws IOException{
        Parent consultantScheduleReportParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/ConsultantScheduleReport.fxml"));
        Scene consultantScheduleReport = new Scene(consultantScheduleReportParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(consultantScheduleReport);
        consultantScheduleReport.getRoot().requestFocus();
        window.show();
    }   
    
    @FXML 
    public void onViewSystemLogButtonPress(ActionEvent event) throws IOException{
        Desktop desktop = Desktop.getDesktop();
        File file =  new File(FileManipulation.getFullFilePath());
        desktop.open(file);
    }
    
}
