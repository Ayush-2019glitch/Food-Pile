package Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FunctionalityGUI {

    public Button logoutButton;
    public Button deletionButton;
    public Button insertionButton;
    public Button updateButton;
    public Button displayButton;
    public Button searchButton;
    public Label funtionLabel;

    public void logoutListener(ActionEvent actionEvent) {
        Client.logout();
    }

    public void deletionListener(ActionEvent actionEvent) {

    }

    public void insertionListener(ActionEvent actionEvent) {
    }

    public void updateListener(ActionEvent actionEvent) {
    }

    public void displayListener(ActionEvent actionEvent) {
    }

    public void searchListener(ActionEvent actionEvent) {
    }
}
