package Application.Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class InsertionGUI {
    public TextField nameTextField;
    public TextField idTextField;
    public TextField brandTextField;
    public TextField quantityTextField;
    public TextField priceTextField;
    public Button addButton;

    public void addListener(ActionEvent actionEvent) throws IOException {
        Socket socket = new Socket("localhost", 5436);
        System.out.println("Client created.");
        new InsertionGUI().sendMessage(socket,"Insertion",nameTextField.getText(),brandTextField.getText(),idTextField.getText(),quantityTextField.getText(),priceTextField.getText());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    String result=(String)objectInputStream.readObject();
                    if(result.equals("Success")){
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                Stage stage = (Stage) addButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SuccessGUI.fxml"));
                                try {
                                    root = (Parent) fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stage.setTitle("Success GUI");
                                stage.setScene(new Scene(root, 400, 200));
                                stage.show();
                            }
                        });
                    }
                    else {
                        System.out.println("Message Recieved");
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Parent root = null;
                                Stage stage = (Stage) addButton.getScene().getWindow();
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FailureMessage.fxml"));
                                try {
                                    root = (Parent) fxmlLoader.load();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stage.setTitle("Failure GUI");
                                stage.setScene(new Scene(root, 250, 380));
                                stage.show();
                            }
                        });
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    private void sendMessage(Socket socket,String function,String name,String brand,String id,String quantity,String price) throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(function);
        objectOutputStream.flush();
        objectOutputStream.writeObject(name);
        objectOutputStream.flush();
        objectOutputStream.writeObject(brand);
        objectOutputStream.flush();
        objectOutputStream.writeObject(id);
        objectOutputStream.flush();
        objectOutputStream.writeObject(quantity);
        objectOutputStream.flush();
        objectOutputStream.writeObject(price);
        objectOutputStream.flush();

    }
}
