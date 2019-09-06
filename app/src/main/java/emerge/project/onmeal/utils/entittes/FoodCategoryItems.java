package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodCategoryItems implements Serializable {

    @SerializedName("foodItemCategoryID")
    int foodItemCategoryID;

    @SerializedName("foodItemCategory")
    String foodItemCategory;


    boolean isSelect;


    public FoodCategoryItems(int foodItemCategoryID, String foodItemCategory, boolean isSelect) {
        this.foodItemCategoryID = foodItemCategoryID;
        this.foodItemCategory = foodItemCategory;
        this.isSelect = isSelect;
    }


    public int getFoodItemCategoryID() {
        return foodItemCategoryID;
    }

    public void setFoodItemCategoryID(int foodItemCategoryID) {
        this.foodItemCategoryID = foodItemCategoryID;
    }

    public String getFoodItemCategory() {
        return foodItemCategory;
    }

    public void setFoodItemCategory(String foodItemCategory) {
        this.foodItemCategory = foodItemCategory;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
