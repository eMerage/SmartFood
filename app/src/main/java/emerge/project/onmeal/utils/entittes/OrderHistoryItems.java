package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderHistoryItems implements Serializable {

    @SerializedName("orderID")
    int orderID;

    @SerializedName("orderDate")
    String orderDate;

    @SerializedName("orderTotal")
    Double orderTotal;

    @SerializedName("dispatchType")
    String dispatchType;

    @SerializedName("statusCode")
    String statusCode;


    @SerializedName("outletName")
    String outletName;



    String menuItems;


    public OrderHistoryItems(int orderID, String orderDate, Double orderTotal, String dispatchType, String statusCode, String menuItems, String outletname) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.dispatchType = dispatchType;
        this.statusCode = statusCode;
        this.menuItems = menuItems;
        this.outletName = outletname;
    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getDispatchType() {
        return dispatchType;
    }

    public void setDispatchType(String dispatchType) {
        this.dispatchType = dispatchType;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(String menuItems) {
        this.menuItems = menuItems;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
}
