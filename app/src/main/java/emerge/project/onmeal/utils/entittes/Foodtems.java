package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Foodtems implements Serializable {



    @SerializedName("menuTitleID")
    String foodId;

    @SerializedName("name")
    String foodName;

    @SerializedName("imageUrl")
    String foodCoverImage;

    @SerializedName("menuCategory")
    String menuCategory;





    public Foodtems(String foodName, String foodId, String foodCoverImage,String menucategory) {
        this.foodName = foodName;
        this.foodId = foodId;
        this.foodCoverImage = foodCoverImage;
        this.menuCategory = menucategory;
    }


    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodCoverImage() {
        return foodCoverImage;
    }

    public void setFoodCoverImage(String foodCoverImage) {
        this.foodCoverImage = foodCoverImage;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }
}
