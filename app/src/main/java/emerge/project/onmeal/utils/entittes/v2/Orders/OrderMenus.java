package emerge.project.onmeal.utils.entittes.v2.Orders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderMenus implements Serializable {

    @SerializedName("menuID")
    int menuID;

    @SerializedName("name")
    String name;

    @SerializedName("menuSizeCode")
    String menuSizeCode;

    @SerializedName("menuPrice")
    Double menuPrice;

    @SerializedName("menuQty")
    int menuQty;


    @SerializedName("orderMenuDetails")
    ArrayList<OrderMenuDetails> OrderMenuDetails = new ArrayList<>();


    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuSizeCode() {
        return menuSizeCode;
    }

    public void setMenuSizeCode(String menuSizeCode) {
        this.menuSizeCode = menuSizeCode;
    }

    public Double getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(Double menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getMenuQty() {
        return menuQty;
    }

    public void setMenuQty(int menuQty) {
        this.menuQty = menuQty;
    }

    public ArrayList<emerge.project.onmeal.utils.entittes.v2.Orders.OrderMenuDetails> getOrderMenuDetails() {
        return OrderMenuDetails;
    }

    public void setOrderMenuDetails(ArrayList<OrderMenuDetails> orderMenuDetails) {
        OrderMenuDetails = orderMenuDetails;
    }
}
