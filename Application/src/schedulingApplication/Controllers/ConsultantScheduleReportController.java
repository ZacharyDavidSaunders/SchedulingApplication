
package schedulingApplication.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import schedulingApplication.Models.Appointment;
import schedulingApplication.Models.Database;
import schedulingApplication.Models.LocalData;

public class ConsultantScheduleReportController implements Initializable {
    
    private URL url;
    private ResourceBundle rb;
    private String username;
    private String[] usernameOptions = {"test","consultant"};

    @FXML private TableView<Appointment> appointmentsTable;
    @FXML private TableColumn<Appointment,String> dayColumn;
    @FXML private TableColumn<Appointment, String> startTimeColumn;
    @FXML private TableColumn<Appointment, String> endTimeColumn;
    @FXML private TableColumn<Appointment, String> titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> customerColumn;
    @FXML private ChoiceBox userNameChoiceBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userNameChoiceBox.setItems(getUsernameOptions());
        userNameChoiceBox.getSelectionModel()
                         .selectedItemProperty()
                         .addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            try {
                populateMonthlyAppointmentsTable(newValue);
                if(appointmentsTable.getItems().isEmpty()){
                    appointmentsTable.setPlaceholder(new Label("This consultant has an empty schedule."));
                }
            } catch (SQLException e) {
                System.out.println("Error populating consultant schedules: "+e);
            }
      }
    });
        
        this.url = url;
        this.rb = rb;
        LocalData.refreshLocalAppointments();
        appointmentsTable.setPlaceholder(new Label("Select a consultant's username (via the selection box above) to view their schedule."));
    }
    
    @FXML public void onReturnToDashboardButtonPress(ActionEvent event) throws IOException{
        Parent dashboardParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Dashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        dashboardScene.getRoot().requestFocus();
        window.show();
    }  
    
    private void populateMonthlyAppointmentsTable(String userName) throws SQLException{
        
        dayColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("day"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endTime"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customerName"));
        
        appointmentsTable.getItems().setAll(Database.getAppointments(Database.getUserIdByUsername(userName)));
    } 
    
    private ObservableList<String> getUsernameOptions(){
        ObservableList<String> usernameOptionsObservableList = FXCollections.observableArrayList();
        usernameOptionsObservableList.addAll(Arrays.asList(usernameOptions));
        return usernameOptionsObservableList;
    }
    
}
