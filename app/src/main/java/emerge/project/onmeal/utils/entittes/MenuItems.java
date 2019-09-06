package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuItems implements Serializable {

    @SerializedName("menuTitleID")
    int menuId;

    @SerializedName("outletMenuTitleID")
    int foodId;

    @SerializedName("menuTitle")
    String foodName;

    @SerializedName("imageUrl")
    String foodCoverImage;

    @SerializedName("outletID")
    int outletId;

    @SerializedName("outlet")
    String outletName;

    @SerializedName("menuCategoryID")
    int menuCategoryID;

    @SerializedName("menuCategory")
    String menuCategory;


    public MenuItems(int menuId, int foodId, String foodName, String foodCoverImage, int outletId, String outletName, int menuCategoryID, String menuCategory) {
        this.menuId = menuId;
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodCoverImage = foodCoverImage;
        this.outletId = outletId;
        this.outletName = outletName;
        this.menuCategoryID = menuCategoryID;
        this.menuCategory = menuCategory;
    }


    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodCoverImage() {
        return foodCoverImage;
    }

    public void setFoodCoverImage(String foodCoverImage) {
        this.foodCoverImage = foodCoverImage;
    }

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public int getMenuCategoryID() {
        return menuCategoryID;
    }

    public void setMenuCategoryID(int menuCategoryID) {
        this.menuCategoryID = menuCategoryID;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }
}
