package Application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

public class HandleSearch implements Runnable {
    private DataItem[] data;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public HandleSearch(Socket socket) {
        this.socket = socket;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void storeData() throws ClassNotFoundException, SQLException, IOException {

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/foodpile";
        Connection connection = DriverManager.getConnection(url, "root", "TetraM0s");
        String query1 = "Select count(*) FROM INVENTORY";
        PreparedStatement preStat = connection.prepareStatement(query1);
        String query2 = "SELECT * FROM INVENTORY;";
        ResultSet result = preStat.executeQuery();
        int i = 0;
        while (result.next()) {
            i = result.getInt("count(*)");
        }
        data = new DataItem[i];
        i = 0;
        preStat = connection.prepareStatement(query2);
        result = preStat.executeQuery();

        while (result.next()) {
            data[i] = new DataItem();
            data[i].setDataItem(result.getString("productName"), result.getString("brand"), result.getString("productID"), result.getInt("quantity"), result.getDouble("price"));
            i++;
        }
    }

    @Override
    public void run() {
        String function="";
        String type="";
        String input="";
        while (true) {
            try {
                function=(String)objectInputStream.readObject();
                if(!function.equals("Insertion")) {
                    type = (String) objectInputStream.readObject();
                    if (!function.equals("Sort")) {
                        input = (String) objectInputStream.readObject();
                    }
                }
                System.out.println("Message received");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            try {
                this.storeData();
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
            }
            int i=0;
            switch(function) {
                case "Search-Window": {
                    switch (type) {
                        case "ID": {
                            while (i < data.length) {
                                if (!data[i].getProductID().equals(input)) i++;
                                else break;
                            }
                            break;
                        }
                        case "name": {
                            while (i < data.length) {
                                if (!data[i].getProductName().equals(input)) i++;
                                else break;
                            }
                            break;
                        }
                        default:
                            throw new IllegalStateException("Unexpected value: " + type);
                    }
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        if (i < data.length) objectOutputStream.writeObject(data[i]);
                        else throw new IllegalStateException("Unexpected value " + input);
                        objectOutputStream.flush();
                        System.out.println("Message sent");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "Deletion-Window":{
                    switch (type) {
                        case "ID": {
                            while (i < data.length) {
                                if (!data[i].getProductID().equals(input)) i++;
                                else break;
                            }
                            break;
                        }
                        case "name": {
                            while (i < data.length) {
                                if (!data[i].getProductName().equals(input)) i++;
                                else break;
                            }
                            break;
                        }
                        default:
                            throw new IllegalStateException("Unexpected value: " + type);
                    }
                    if(i<data.length) {
                        try {
                            deleteData(i);
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream.writeObject("Success");
                            objectOutputStream.flush();
                            System.out.println("Message sent");
                        } catch (ClassNotFoundException | SQLException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        System.out.println("Entry not found");
                    }
                    break;
                }
                case "Sort":{
                    sort(type);
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(data);
                        objectOutputStream.flush();
                        System.out.println("Message sent");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "Insertion":{
                    
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + function);
            }
        }
    }

    private void sort(String type) {
        int i,j;
        DataItem temp;
        switch(type){
            case "Sort Using Price":{
                for (i=1;i< data.length-1;i++){
                    temp=data[i];
                    for(j=i-1;j>=0;j--){
                        if(data[i].getPrice()<data[j].getPrice())data[j+1]=data[j];
                        else break;
                    }
                    data[j+1]=temp;
                }
                break;
            }
            case "Sort Using Quantity":{
                for (i=1;i< data.length-1;i++){
                    temp=data[i];
                    for(j=i-1;j>=0;j--){
                        if(data[i].getQuantity()<data[j].getQuantity())data[j+1]=data[j];
                        else break;
                    }
                    data[j+1]=temp;
                }
                break;
            }
        }
    }

    private void deleteData(int pos) throws ClassNotFoundException, SQLException, IOException{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/foodpile";
        Connection connection = DriverManager.getConnection(url, "root", "TetraM0s");
        String query1 = "Delete FROM INVENTORY WHERE productID='"+data[pos].getProductID()+"';";
        PreparedStatement preStat = connection.prepareStatement(query1);
        System.out.println(""+preStat.executeUpdate());
        int size=data.length;
        DataItem[] temp=new DataItem[size-1];
        int i=0;
        for (i=0;i< temp.length;i++){
            if(i==pos){
                i--;
                continue;
            }
            temp[i]=data[i];
        }
        data=temp;
    }
}
