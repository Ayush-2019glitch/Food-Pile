package Application;

import javafx.application.Application;
import javafx.application.Platform;

public class Client {

    public static void main(String[] args) {

        Application.launch(LoginHandler.class,args);
    }

    public static void logout(){
        Platform.exit();
    }
}
