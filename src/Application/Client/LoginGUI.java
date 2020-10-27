package Application.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LoginGUI {

    public Label headLabel;
    public Label instructionLabel;
    public Label nameLabel;
    public Label passwordLabel;
    public TextField nameTextField;
    public PasswordField passwordField;
    public Button loginButton;

    public void loginListener(ActionEvent actionEvent) throws IOException {
        String s=new String("");
        File file=new File("C:\\Users\\tt\\AppData\\Roaming\\JetBrains\\IdeaIC2020.2\\scratches\\Password.txt");
        try {
            Scanner sc=new Scanner(file);
            while(sc.hasNextLine()){
                s=s+sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Parent root=null;
        if(nameTextField.getText().equals("Admin")&&passwordField.getText().equals(s)){
            Stage stage =(Stage) loginButton.getScene().getWindow();
            try{
                root = FXMLLoader.load(getClass().getResource("FunctionalityGUI.fxml"));
            }
            catch(IOException e){
                e.printStackTrace();
            }
            stage.setTitle("Food Pile Application");
            stage.setScene(new Scene(root,400,250));
            stage.show();
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
