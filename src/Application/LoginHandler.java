package Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginHandler extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
            primaryStage.setTitle("Login GUI");
            primaryStage.setScene(new Scene(root, 300, 200));
            Platform.isImplicitExit();
            primaryStage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

}
