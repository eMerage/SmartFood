package emerge.project.onmeal.utils.entittes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderHistoryMenu implements Parcelable {


    int orderId;
    String cartID;
    String outletMenuName;
    String size;
    Double price;
    int qty;
    ArrayList<OrderHistorySubMenu> foods;


    public OrderHistoryMenu(int orderId, String cartID, String outletMenuName, String size,Double price, int qty, ArrayList<OrderHistorySubMenu> foods) {
        this.orderId = orderId;
        this.cartID = cartID;
        this.outletMenuName = outletMenuName;
        this.size = size;
        this.price = price;
        this.qty = qty;
        this.foods = foods;
    }


    protected OrderHistoryMenu(Parcel in) {
        orderId = in.readInt();
        cartID = in.readString();
        outletMenuName = in.readString();
        size = in.readString();


        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        qty = in.readInt();
    }

    public static final Creator<OrderHistoryMenu> CREATOR = new Creator<OrderHistoryMenu>() {
        @Override
        public OrderHistoryMenu createFromParcel(Parcel in) {
            return new OrderHistoryMenu(in);
        }

        @Override
        public OrderHistoryMenu[] newArray(int size) {
            return new OrderHistoryMenu[size];
        }
    };

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getOutletMenuName() {
        return outletMenuName;
    }

    public void setOutletMenuName(String outletMenuName) {
        this.outletMenuName = outletMenuName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }



    public static Creator<OrderHistoryMenu> getCREATOR() {
        return CREATOR;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public ArrayList<OrderHistorySubMenu> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<OrderHistorySubMenu> foods) {
        this.foods = foods;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderId);
        parcel.writeString(cartID);
        parcel.writeString(outletMenuName);
        parcel.writeString(size);

        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
        parcel.writeInt(qty);
    }
}
