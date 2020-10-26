package Application;

import java.io.Serializable;

public class DataItem implements Serializable{
    private String productName;
    private String brand;
    private String productID;
    private int quantity;
    private double price;

    public void setDataItem(String productName,String brand,String productID,int quantity,double price){
        this.productName=productName;
        this.brand=brand;
        this.productID=productID;
        this.quantity=quantity;
        this.price=price;
    }

    public String getProductName(){
        return this.productName;
    }

    public String getBrand(){
        return this.brand;
    }

    public String getProductID(){
        return this.productID;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getPrice(){
        return this.price;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", productID='" + productID + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
