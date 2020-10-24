package Application;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;

public class LoginGUI {

    public Label headLabel;
    public Label instructionLabel;
    public Label nameLabel;
    public Label passwordLabel;
    public TextField nameTextField;
    public PasswordField passwordField;
    public Button loginButton;

    public void loginListener(ActionEvent actionEvent) {
        Parent root=null;
        if(nameTextField.getText().equals("Admin")&&passwordField.getText().equals("King")){
            Client.loginCheckin();
        }
        else {
            Stage stage =(Stage) loginButton.getScene().getWindow();
            try{
                root = FXMLLoader.load(getClass().getResource("LoginFailure.fxml"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
            stage.setTitle("Login Failure");
            stage.setScene(new Scene(root,400,100));
            stage.show();
        }
    }
}
