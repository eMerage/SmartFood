package emerge.project.onmeal.utils.entittes.v2.Orders;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderMenuDetails implements Serializable {


    @SerializedName("name")
    String name;

    @SerializedName("foodQty")
    int foodQty;


    @SerializedName("isBase")
    Boolean isBase = false;


    @SerializedName("foodItemCategory")
    String foodItemCategory;


    @SerializedName("foodItemTypeCode")
    String foodItemTypeCode;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFoodQty() {
        return foodQty;
    }

    public void setFoodQty(int foodQty) {
        this.foodQty = foodQty;
    }

    public Boolean getBase() {
        return isBase;
    }

    public void setBase(Boolean base) {
        isBase = base;
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
}
