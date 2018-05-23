package schedulingApplication.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import schedulingApplication.Exceptions.InvalidDataException;
import schedulingApplication.Models.Appointment;
import schedulingApplication.Models.Customer;
import schedulingApplication.Models.Database;
import schedulingApplication.Models.LocalData;

public class ModifyAppointmentController implements Initializable {

    @FXML ChoiceBox customersChoiceBox;
    @FXML ChoiceBox descriptionChoiceBox;
    @FXML Label errorLabel;
    @FXML TextField titleTextField, locationTextField, contactTextField, urlTextField, dayTextField, startingHourTextField, startingMinuteTextField, endingHourTextField, endingMinuteTextField; 
    private String[] descriptionOptions = {"Consultation","Status Update","Other"};
    private static Appointment appointmentToBeModified = null;
    
    private int pheonixArizonaTimeOffset = 0;
    private int newYorkNewYorkTimeOffset = 3;
    private int londonEnglandTimeOffset = 8;
    private float startOfBizHours = 9.0f;
    private float endOfBizHours = 17.0f;

    private ZoneId currentLocale = ZoneId.systemDefault();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LocalData.refreshLocalCustomers();
        customersChoiceBox.setItems(getCustomerNameList());
        descriptionChoiceBox.setItems(getDescriptionOptions());
        
        for(int i = 0; i < getCustomerNameList().size(); i++){
            if(appointmentToBeModified.getCustomerName().equals(getCustomerNameList().get(i))){
                customersChoiceBox.getSelectionModel().select(i);
            }
        }
        
        if(appointmentToBeModified.getDescription().equals(descriptionOptions[0])){
            descriptionChoiceBox.getSelectionModel().select(0);
        }else if(appointmentToBeModified.getDescription().equals(descriptionOptions[1])){
            descriptionChoiceBox.getSelectionModel().select(1);
        }else{
            descriptionChoiceBox.getSelectionModel().select(2);
        }
        
        titleTextField.setText(appointmentToBeModified.getTitle());
        locationTextField.setText(appointmentToBeModified.getLocation());
        contactTextField.setText(appointmentToBeModified.getContact());
        urlTextField.setText(appointmentToBeModified.getUrl());
        dayTextField.setText(appointmentToBeModified.getDay().substring(8, 10));
        startingHourTextField.setText(appointmentToBeModified.getStartingHour());
        startingMinuteTextField.setText(appointmentToBeModified.getStartingMinute());
        endingHourTextField.setText(appointmentToBeModified.getEndingHour());
        endingMinuteTextField.setText(appointmentToBeModified.getEndingMinute());
    }  
    
    @FXML
    public void onCancelButtonPress(ActionEvent event) throws IOException{
        goToAppointments(event);
    }     
    
    @FXML
    public void onSaveButtonPress(ActionEvent event) throws InvalidDataException, SQLException, IOException{
        
        boolean overlapping = false;
        boolean outOfBizHours = false;
        
        if(customersChoiceBox.getSelectionModel().getSelectedItem() == null){
            try{
                throw new InvalidDataException("ERROR: You must select a customer to Save!",errorLabel);
            }catch(InvalidDataException e){
                System.out.println("An InvalidDataException has been thrown");
            }
        }else if(descriptionChoiceBox.getSelectionModel().getSelectedItem() == null){
            try{
                throw new InvalidDataException("ERROR: You must select a description to Save!",errorLabel);
            }catch(InvalidDataException e){
                System.out.println("An InvalidDataException has been thrown");
            }    
        }else{
            String newAppointmentStartTimeConstructor = startingHourTextField.getText()+"."+startingMinuteTextField.getText();
                Float newAppointmentStartTime = Float.valueOf(newAppointmentStartTimeConstructor);
                String newAppointmentEndTimeConstructor = endingHourTextField.getText()+"."+endingMinuteTextField.getText();
                Float newAppointmentEndTime = Float.valueOf(newAppointmentEndTimeConstructor);
                
                if((newAppointmentStartTime < startOfBizHours || newAppointmentStartTime > endOfBizHours) || newAppointmentEndTime > endOfBizHours || newAppointmentStartTime < startOfBizHours){
                    outOfBizHours = true;
                    try{
                        throw new InvalidDataException("ERROR: This appointment is outside business hours! ",errorLabel);
                    }catch(InvalidDataException e){
                     System.out.println("An InvalidDataException has been thrown");
                    }
                }
                
            for(int i = 0; i < LocalData.getAppointments().size(); i++){
                String currentAppointmentStartTimeConstructor = LocalData.getAppointments().get(i).getStartingHour()+"."+LocalData.getAppointments().get(i).getStartingMinute();
                Float currentAppointmentStartTime = Float.valueOf(currentAppointmentStartTimeConstructor);
                String currentAppointmentEndTimeConstructor = LocalData.getAppointments().get(i).getEndingHour()+"."+LocalData.getAppointments().get(i).getEndingMinute();
                Float currentAppointmentEndTime = Float.valueOf(currentAppointmentEndTimeConstructor);
                
                if((newAppointmentStartTime >= currentAppointmentStartTime && newAppointmentStartTime <= currentAppointmentEndTime)||(newAppointmentEndTime >= currentAppointmentStartTime && newAppointmentEndTime <= currentAppointmentEndTime) || newAppointmentStartTime <= currentAppointmentStartTime && newAppointmentEndTime >= currentAppointmentEndTime){
                     if(Integer.parseInt(LocalData.getAppointments().get(i).getDay().substring(8)) == Integer.parseInt(dayTextField.getText())){
                        overlapping = true;
                        try{
                            throw new InvalidDataException("ERROR: This appointment overlaps with an existing appointment! ",errorLabel);
                        }catch(InvalidDataException e){
                            System.out.println("An InvalidDataException has been thrown");
                        }    
                    }
                }
            }
            if(!overlapping && !outOfBizHours){
                try{
                    int startHour = Integer.parseInt(startingHourTextField.getText());
                    int startMinute = Integer.parseInt(startingMinuteTextField.getText());
                    int endHour = Integer.parseInt(endingHourTextField.getText());
                    int endMinute = Integer.parseInt(endingMinuteTextField.getText());
                    int day = Integer.parseInt(dayTextField.getText());
                    String title = titleTextField.getText();
                    String description = descriptionChoiceBox.getSelectionModel().getSelectedItem().toString();
                    String customer = customersChoiceBox.getSelectionModel().getSelectedItem().toString();
                    String location = locationTextField.getText();
                    String contact = contactTextField.getText();
                    String url = urlTextField.getText();

                    Database.modifyAppointment(appointmentToBeModified.getId(), title,customer,description,location,contact,url,day,adjustTime(startHour),startMinute,adjustTime(endHour),endMinute);
                    goToAppointments(event);

                }catch (NumberFormatException e){
                    try{
                        throw new InvalidDataException("ERROR: The hour, day, and minute fields must contain only contain numeric characters!",errorLabel);
                    }catch(InvalidDataException ex){
                     System.out.println("An InvalidDataException has been thrown");
                    }    
                }
            }
        }
    }
    
    private ObservableList<String> getCustomerNameList(){
        ArrayList<Customer> customers = LocalData.getCustomers();
        ArrayList<String> names = new ArrayList<String>();
        for(int i = 0; i < customers.size(); i++){
            names.add(customers.get(i).getName());
        }
        ObservableList<String> namesObvList = FXCollections.observableArrayList(names);
        return namesObvList;
    }
    
    private ObservableList<String> getDescriptionOptions(){
        ObservableList<String> descriptionOptionsObservableList = FXCollections.observableArrayList();
        descriptionOptionsObservableList.addAll(Arrays.asList(descriptionOptions));
        return descriptionOptionsObservableList;
    }
    
    private void goToAppointments(ActionEvent event) throws IOException{
        Parent appointmentsParent = FXMLLoader.load(getClass().getResource("/schedulingApplication/Views/Appointments.fxml"));
        Scene appointmentsScene = new Scene(appointmentsParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(appointmentsScene);
        appointmentsScene.getRoot().requestFocus();
        window.show();
    }
    
    public static void setAppointmentToBeModified(Appointment appointment){
        appointmentToBeModified = appointment;
    }
    
     private int adjustTime (int integerTime){
        
        if(currentLocale.equals(ZoneId.of("America/New_York"))){
            integerTime -= newYorkNewYorkTimeOffset;
        }else if(currentLocale.equals(ZoneId.of("Europe/London"))){
            integerTime -= londonEnglandTimeOffset;
        }
        return integerTime;
    }
}

