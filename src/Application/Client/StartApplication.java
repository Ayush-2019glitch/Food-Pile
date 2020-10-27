package Application.Client;

import javafx.application.Application;
import javafx.application.Platform;

public class StartApplication {

    public static void main(String[] args) {

        Application.launch(Initiator.class,args);
    }
    public static void logout(){
        Platform.exit();
    }
}
