
package schedulingApplication.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import schedulingApplication.Models.Appointment;
import schedulingApplication.Models.Database;
import schedulingApplication.Models.LocalData;

public class AppointmentsController implements Initializable {
    
    private URL url;
    private ResourceBundle rb;

    @FXML private RadioButton monthlyView;
    @FXML private Label errorLabel;
    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment,String> dayColumn;
    @FXML private TableColumn<Appointment, String> startTimeColumn;
    @FXML private TableColumn<Appointment, String> endTimeColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> customerColumn;
    @FXML private ToggleGroup view;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.url = url;
        this.rb = rb;
        LocalData.refreshLocalAppointments();
        
        //The following lines use several lambda expressions. Each experession has an inline comment that explains its use (thus satisfying Requirement G).
        //This first lambda expression is used to simplify the insertion of the appointment's day value into the table.
        dayColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDay()));
        //This second lambda expression is used to simplify the insertion of the appointment's start time value into the table.
        startTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStartTime()));
        //This third lambda expression is used to simplify the insertion of the appointment's end time value into the table.
        endTimeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEndTime()));
        //This fourth lambda expression is used to simplify the insertion of the appointment's title value into the table.
        titleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
        //This fifth lambda expression is used to simplify the insertion of the appointment's description value into the table.
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDescription()));
        //This sixth lambda expression is used to simplify the insertion of the appointment's customer name value into the table.
        customerColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomerName()));
        
        if(monthlyView.isSelected()){
            appointmentsTable.getItems().setAll(populateMonthlyAppointmentsTable());
        }else{
            appointmentsTable.getItems().setAll(populateWeeklyAppointmentsTable());
        }
    }    
    
    @FXML 
    public void changeView(ActionEvent event){
        initialize(url,rb);
    }
    
    @FXML public void onReturnToDashboardButtonPress(ActionEvent event) throws IOException{
        Parent dashboardParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Dashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        dashboardScene.getRoot().requestFocus();
        window.show();
    }  
    
    private List<Appointment> populateMonthlyAppointmentsTable(){
        ArrayList<Appointment> list = new ArrayList<Appointment>();
        for(int i = 0; i < LocalData.getAppointments().size(); i++){
            list.add(LocalData.getAppointments().get(i));
        }
        return list;
    }
    
    private List<Appointment> populateWeeklyAppointmentsTable(){
        ArrayList<Appointment> list = new ArrayList<Appointment>();
        Calendar cal = Calendar.getInstance();
        int currentDay = cal.get(Calendar.DAY_OF_MONTH); 
        for(int i = 0; i < LocalData.getAppointments().size(); i++){
            int appointmentDay;
            String appointmentDayString = LocalData.getAppointments().get(i).getDay().substring(8,10);
            if(appointmentDayString.charAt(0) == '0'){
                appointmentDay = Integer.valueOf(appointmentDayString.substring(1));
            }else{
                appointmentDay = Integer.valueOf(appointmentDayString);
            }
            if(appointmentDay > (currentDay-7) && appointmentDay < (currentDay+7)){
                
                list.add(LocalData.getAppointments().get(i));
            }
        }
        return list;
    }
    
    @FXML 
    public void onAddButtonPress(ActionEvent event) throws IOException{
        Parent addAppointmentParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/AddAppointment.fxml"));
        Scene addAppointmentScene = new Scene(addAppointmentParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addAppointmentScene);
        addAppointmentScene.getRoot().requestFocus();
        window.show();
    } 
    
    @FXML
    public void onModifyButtonPress(ActionEvent event) throws IOException{
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null){
            Appointment appointment = appointmentsTable.getSelectionModel().getSelectedItem();
            ModifyAppointmentController.setAppointmentToBeModified(appointment);
            System.out.println(appointment.getCustomerName());
            Parent modifyAppointmentParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/ModifyAppointment.fxml"));
            Scene modifyAppointmentScene = new Scene(modifyAppointmentParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyAppointmentScene);
            modifyAppointmentScene.getRoot().requestFocus();
            window.show();
        }else{
            tellUserToSelectRow();
        }
    }
    
    @FXML 
    public void onDeleteButtonPress(ActionEvent event){
        if(appointmentsTable.getSelectionModel().getSelectedItem() != null){
            Appointment appointment = appointmentsTable.getSelectionModel().getSelectedItem();
            Database.deleteAppointment(appointment.getId());
            initialize(url,rb);
        }else{
            tellUserToSelectRow();
        }
    }    
    
    private void tellUserToSelectRow(){
        errorLabel.setVisible(true);
    }    
    
}
