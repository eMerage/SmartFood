package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuSize implements Serializable {


    @SerializedName("foodItemSizeCode")
    String foodItemSizeCode;

    @SerializedName("foodItemSize")
    String foodItemSize;

    boolean isSelect;


    public MenuSize(String foodItemSizeCode, String foodItemSize,boolean isselact) {
        this.foodItemSizeCode = foodItemSizeCode;
        this.foodItemSize = foodItemSize;
        this.isSelect = isselact;
    }


    public String getFoodItemSizeCode() {
        return foodItemSizeCode;
    }

    public void setFoodItemSizeCode(String foodItemSizeCode) {
        this.foodItemSizeCode = foodItemSizeCode;
    }

    public String getFoodItemSize() {
        return foodItemSize;
    }

    public void setFoodItemSize(String foodItemSize) {
        this.foodItemSize = foodItemSize;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
