package schedulingApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import schedulingApplication.Models.Database;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Database.connectToDatabase();
        } catch (Exception e) {
            System.out.println("DATABASE IS UNAVAILIBLE!");
        }
        Parent root = FXMLLoader.load(getClass().getResource("Views/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}