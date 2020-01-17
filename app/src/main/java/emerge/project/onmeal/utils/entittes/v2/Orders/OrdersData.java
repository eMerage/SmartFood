package emerge.project.onmeal.utils.entittes.v2.Orders;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;


public class OrdersData  implements Serializable {

    @SerializedName("status")
    Boolean status = false;


    @SerializedName("error")
    ErrorObject error = new ErrorObject();

    @SerializedName("orders")
    ArrayList<OrdersList> ordersList = new ArrayList<OrdersList>();


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ErrorObject getError() {
        return error;
    }

    public void setError(ErrorObject error) {
        this.error = error;
    }

    public ArrayList<OrdersList> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<OrdersList> ordersList) {
        this.ordersList = ordersList;
    }
}
