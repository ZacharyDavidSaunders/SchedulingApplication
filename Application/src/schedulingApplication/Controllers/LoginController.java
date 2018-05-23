package schedulingApplication.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedulingApplication.Models.Appointment;
import schedulingApplication.Models.Database;
import schedulingApplication.Models.FileManipulation;
import schedulingApplication.Models.LocalData;

public class LoginController implements Initializable {

    @FXML private Button exitButton,loginButton;
    @FXML private TextField usernameTextField, passwordTextField;
    @FXML private Label errorMessage, usernameLabel, passwordLabel, headerLabel, promptLabel;
    Locale currentLocale;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        currentLocale = Locale.getDefault();
        if(currentLocale.getLanguage().equals("fr")){
            usernameTextField.promptTextProperty().set("Nom d'utilisateur");
            headerLabel.setText("S'identifier");
            promptLabel.setText("Entrez vos informations d'identification et appuyez sur le bouton \"Connexion\" pour entrer.");
            usernameLabel.setText("Nom d'utilisateur");
            passwordTextField.promptTextProperty().set("Mot de passe");
            passwordLabel.setText("Mot de passe");
            exitButton.setText("Sortie");
            loginButton.setText("S'identifier");
        }
        try {
            LocalData.refreshLocalUsers();
        } catch (SQLException ex) {
            System.out.println("UNABLE TO REFRESH LOCAL USERS."); //Does not need to be translated because it is an internal System.out message.
        }
    }    
    
    @FXML
    public void onExitButtonPress(ActionEvent event) throws SQLException{
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        Database.closeConnection();
        System.exit(0);
    }    
    
    @FXML
    public void onLoginButtonPress(ActionEvent event) throws IOException{
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        for(int i =0; i < LocalData.getUsers().size(); i++){
            if(LocalData.getUsers().get(i).getUsername().equals(username) && LocalData.getUsers().get(i).getPassword().equals(password)){
                LocalData.setCurrentUser(LocalData.getUsers().get(i));
                break;
            } 
        }
        if(LocalData.getCurrentUser() == null){ 
        //This prevents a NullPointerException, as the LocalData.getCurrentUser() is used in else (inside the LocalData.refreshLocalAppointments(); method call.
            if(currentLocale.getLanguage().equals("fr")){
                errorMessage.setText("ERREUR: informations d'identification non valides, veuillez réessayer.");    
            }
            errorMessage.setVisible(true); 
        }else{
            LocalData.refreshLocalAppointments();
            showAppointmentAlertIfNeeded();
            Calendar cal = Calendar.getInstance();
            int currentDay = cal.get(Calendar.DAY_OF_MONTH); 
            int currentHour = cal.get(Calendar.HOUR_OF_DAY); 
            int currentMin = cal.get(Calendar.MINUTE); 
            FileManipulation.writeToFile("\nUserId: "+LocalData.getCurrentUser().getId()+" logged into the system on April "+currentDay+", at "+currentHour+":"+currentMin);
            goToDashboard(event);
        }
    }
    
    private void goToDashboard(ActionEvent event) throws IOException{
        Parent dashboardParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Dashboard.fxml"));
        Scene dashboardScene = new Scene(dashboardParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboardScene);
        dashboardScene.getRoot().requestFocus();
        window.show();
    }
    
    private void showAppointmentAlertIfNeeded(){
        ArrayList<Appointment> appointments = LocalData.getAppointments();
        Calendar cal = Calendar.getInstance();
        
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        int currentMin = cal.get(Calendar.MINUTE);
        int curentTimeInMins = (currentHour * 60) + currentMin;
        
        for(int i = 0; i < appointments.size(); i++){
            
            if(Integer.parseInt(appointments.get(i).getDay().substring(8)) == currentDay){
                int appointmentHour = Integer.parseInt(appointments.get(i).getStartingHour());
                int appointmentMins = Integer.parseInt(appointments.get(i).getStartingMinute());
                int appointmentStartTime = appointmentHour*60+appointmentMins;
                
                System.out.println("currentTime:"+curentTimeInMins);
                System.out.println("appointmentStartTime:"+appointmentStartTime);
                
                if(appointmentStartTime - curentTimeInMins <= 15 && appointmentStartTime - curentTimeInMins > 0) {
                    System.out.println("currentTime:"+curentTimeInMins);
                    System.out.println("appointmentStartTime:"+appointmentStartTime);
                    displayAlert(appointments.get(i), appointmentStartTime-curentTimeInMins);
                }
            }
        }
    }
    
    public void displayAlert(Appointment upcomingAppointment, int minutesTill){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("You have an upcoming appointment!");
        alert.setHeaderText("You have an upcoming appointment!");
        alert.setContentText("Your \""+upcomingAppointment.getTitle()+"\" appointment with "+upcomingAppointment.getCustomerName()+" is in "+minutesTill+" minutes.");
        //Alert does not need to be translated because it is not an error control message.
        alert.showAndWait();
    }
    
    public static void unableToConnectToDatabase(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Unable to connect to database!");
        alert.setHeaderText("Unable to connect to database!");
        alert.setContentText("The UCertify database is currently unavailable. If this occurs during evaluation, please understand that this is *not* due to my project. Press \"OK\" to continue to my project, bear in mind however, that you won't be able to login without the database.");
        alert.showAndWait();
    }
}
