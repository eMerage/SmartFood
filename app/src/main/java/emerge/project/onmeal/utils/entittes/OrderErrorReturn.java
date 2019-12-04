package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderErrorReturn implements Serializable {

    @SerializedName("errorCode")
    String errorCode;

    @SerializedName("outletID")
    int outletID;

    @SerializedName("orderCode")
    String orderCode;

    @SerializedName("outletMenu")
    MenuItemsError menuItemsError = new MenuItemsError();



    public OrderErrorReturn(String errorCode, int outletID, String orderCode, MenuItemsError menuItemsError) {
        this.errorCode = errorCode;
        this.outletID = outletID;
        this.orderCode = orderCode;
        this.menuItemsError = menuItemsError;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getOutletID() {
        return outletID;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public MenuItemsError getMenuItemsError() {
        return menuItemsError;
    }

    public void setMenuItemsError(MenuItemsError menuItemsError) {
        this.menuItemsError = menuItemsError;
    }
}
