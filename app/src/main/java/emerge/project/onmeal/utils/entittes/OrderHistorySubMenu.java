package emerge.project.onmeal.utils.entittes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderHistorySubMenu implements Parcelable {

    String cartID;
    String outletMenuName;
    int qty;
    boolean isBaseFood;
    String foodItemCategory;
    String foodItemTypeCode;


    public OrderHistorySubMenu(String cartID, String outletMenuName, int qty, boolean isBaseFood, String foodItemCategory, String foodItemTypeCode) {
        this.cartID = cartID;
        this.outletMenuName = outletMenuName;
        this.qty = qty;
        this.isBaseFood = isBaseFood;
        this.foodItemCategory = foodItemCategory;
        this.foodItemTypeCode = foodItemTypeCode;
    }


    protected OrderHistorySubMenu(Parcel in) {
        cartID = in.readString();
        outletMenuName = in.readString();
        qty = in.readInt();
        isBaseFood = in.readByte() != 0;
        foodItemCategory = in.readString();
        foodItemTypeCode = in.readString();
    }

    public static final Creator<OrderHistorySubMenu> CREATOR = new Creator<OrderHistorySubMenu>() {
        @Override
        public OrderHistorySubMenu createFromParcel(Parcel in) {
            return new OrderHistorySubMenu(in);
        }

        @Override
        public OrderHistorySubMenu[] newArray(int size) {
            return new OrderHistorySubMenu[size];
        }
    };

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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public boolean isBaseFood() {
        return isBaseFood;
    }

    public void setBaseFood(boolean baseFood) {
        isBaseFood = baseFood;
    }

    public String getFoodItemCategory() {
        return foodItemCategory;
    }

    public void setFoodItemCategory(String foodItemCategory) {
        this.foodItemCategory = foodItemCategory;
    }

    public String getFoodItemTypeCode() {
        return foodItemTypeCode;
    }

    public void setFoodItemTypeCode(String foodItemTypeCode) {
        this.foodItemTypeCode = foodItemTypeCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cartID);
        parcel.writeString(outletMenuName);
        parcel.writeInt(qty);
        parcel.writeByte((byte) (isBaseFood ? 1 : 0));
        parcel.writeString(foodItemCategory);
        parcel.writeString(foodItemTypeCode);
    }
}
