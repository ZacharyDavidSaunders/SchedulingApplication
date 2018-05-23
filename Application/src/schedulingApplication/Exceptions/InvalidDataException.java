package schedulingApplication.Exceptions;

import javafx.scene.control.Label;

public class InvalidDataException extends RuntimeException {
    
    public InvalidDataException(String message, Label errorMessage) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }    
}
