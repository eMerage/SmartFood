package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MenuSubItemsEntiti  implements Serializable {




    //Lunch
    @SerializedName("menuCategory")
    String menuCategory;
    @SerializedName("menuCategoryID")
    int menuCategoryID;


    //Rice and Curry
    @SerializedName("menuTitle")
    String menuTitle;
    @SerializedName("menuTitleID")
    int menuTitleID;


    //Hot Rice & Curry
    @SerializedName("outletMenuName")
    String outletMenuName;
    @SerializedName("outletMenuTitleID")
    int outletMenuTitleID;



    //Rice
    @SerializedName("foodItemCategoryID")
    int foodItemCategoryID;
    @SerializedName("foodItemCategory")
    String foodItemCategory;



    @SerializedName("foodItemID")
    int foodItemID;




    //White Rice
    @SerializedName("outletFoodItemID")
    int foodId;
    @SerializedName("name")
    String foodname;


    //Plate
    @SerializedName("foodItemTypeID")
    int foodItemTypeID;
    @SerializedName("foodItemType")
    String foodItemType;



    //Plate
    @SerializedName("foodItemSizeID")
    int foodItemSizeID;
    @SerializedName("foodItemSize")
    String foodItemSize;

    @SerializedName("foodItemSizeCode")
    String foodItemSizeCode;



    //Plate
    @SerializedName("outletID")
    int outletID;
    @SerializedName("outlet")
    String outlet;


    @SerializedName("isBaseFood")
    boolean isBaseFood;


    @SerializedName("foodItemPrice")
    Double foodItemPrice;

    @SerializedName("foodImageUrl")
    String imageUrl;

    @SerializedName("maxOrderQty")
    int maxOrderQty;
    @SerializedName("maxCurryCount")
    int maxCurryCount;
    @SerializedName("maxExtrasQty")
    int maxExtrasQty;
    @SerializedName("maxFreeItemQty")
    int maxFreeItemQty;


    @SerializedName("isFoodItemCategoryCompulsory")
    int isFoodItemCategoryCompulsory;




    int quantity;

    boolean isSelect;


    public MenuSubItemsEntiti() {
    }


    public MenuSubItemsEntiti(String menuCategory, int menuCategoryID, String menuTitle, int menuTitleID,
                              String outletMenuName, int outletMenuTitleID, int foodItemCategoryID, String foodItemCategory, int foodItemID,
                              int foodId, String foodname, int foodItemTypeID, String foodItemType, int foodItemSizeID,
                              String foodItemSize, String foodItemSizeCode, int outletID, String outlet, Double foodItemPrice, String imageUrl,
                              int maxOrderQty, int maxCurryCount, int maxExtrasQty, int maxFreeItemQty,
                              boolean isSelect, boolean isbasefood, int qty) {
        this.menuCategory = menuCategory;
        this.menuCategoryID = menuCategoryID;
        this.menuTitle = menuTitle;
        this.menuTitleID = menuTitleID;
        this.outletMenuName = outletMenuName;
        this.outletMenuTitleID = outletMenuTitleID;
        this.foodItemCategoryID = foodItemCategoryID;
        this.foodItemCategory = foodItemCategory;
        this.foodItemID = foodItemID;
        this.foodId = foodId;
        this.foodname = foodname;
        this.foodItemTypeID = foodItemTypeID;
        this.foodItemType = foodItemType;
        this.foodItemSizeID = foodItemSizeID;
        this.foodItemSize = foodItemSize;
        this.outletID = outletID;
        this.outlet = outlet;
        this.foodItemPrice = foodItemPrice;
        this.imageUrl = imageUrl;
        this.maxOrderQty = maxOrderQty;
        this.maxCurryCount = maxCurryCount;
        this.maxExtrasQty = maxExtrasQty;
        this.maxFreeItemQty = maxFreeItemQty;
        this.isSelect = isSelect;
        this.isBaseFood = isbasefood;
        this.foodItemSizeCode=foodItemSizeCode;
        this.quantity=qty;
    }




    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }

    public int getMenuCategoryID() {
        return menuCategoryID;
    }

    public void setMenuCategoryID(int menuCategoryID) {
        this.menuCategoryID = menuCategoryID;
    }


    public int getFoodItemID() {
        return foodItemID;
    }

    public void setFoodItemID(int foodItemID) {
        this.foodItemID = foodItemID;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public int getMenuTitleID() {
        return menuTitleID;
    }

    public void setMenuTitleID(int menuTitleID) {
        this.menuTitleID = menuTitleID;
    }

    public String getOutletMenuName() {
        return outletMenuName;
    }

    public void setOutletMenuName(String outletMenuName) {
        this.outletMenuName = outletMenuName;
    }

    public int getOutletMenuTitleID() {
        return outletMenuTitleID;
    }

    public void setOutletMenuTitleID(int outletMenuTitleID) {
        this.outletMenuTitleID = outletMenuTitleID;
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

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getFoodItemTypeID() {
        return foodItemTypeID;
    }

    public void setFoodItemTypeID(int foodItemTypeID) {
        this.foodItemTypeID = foodItemTypeID;
    }

    public String getFoodItemType() {
        return foodItemType;
    }

    public void setFoodItemType(String foodItemType) {
        this.foodItemType = foodItemType;
    }

    public int getFoodItemSizeID() {
        return foodItemSizeID;
    }

    public void setFoodItemSizeID(int foodItemSizeID) {
        this.foodItemSizeID = foodItemSizeID;
    }

    public String getFoodItemSize() {
        return foodItemSize;
    }

    public void setFoodItemSize(String foodItemSize) {
        this.foodItemSize = foodItemSize;
    }

    public int getOutletID() {
        return outletID;
    }

    public void setOutletID(int outletID) {
        this.outletID = outletID;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public Double getFoodItemPrice() {
        return foodItemPrice;
    }

    public void setFoodItemPrice(Double foodItemPrice) {
        this.foodItemPrice = foodItemPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getMaxOrderQty() {
        return maxOrderQty;
    }

    public void setMaxOrderQty(int maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public int getMaxCurryCount() {
        return maxCurryCount;
    }

    public void setMaxCurryCount(int maxCurryCount) {
        this.maxCurryCount = maxCurryCount;
    }

    public int getMaxExtrasQty() {
        return maxExtrasQty;
    }

    public void setMaxExtrasQty(int maxExtrasQty) {
        this.maxExtrasQty = maxExtrasQty;
    }

    public int getMaxFreeItemQty() {
        return maxFreeItemQty;
    }

    public void setMaxFreeItemQty(int maxFreeItemQty) {
        this.maxFreeItemQty = maxFreeItemQty;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getIsFoodItemCategoryCompulsory() {
        return isFoodItemCategoryCompulsory;
    }

    public void setIsFoodItemCategoryCompulsory(int isFoodItemCategoryCompulsory) {
        this.isFoodItemCategoryCompulsory = isFoodItemCategoryCompulsory;
    }


    public boolean getBaseFood() {
        return isBaseFood;
    }

    public void setBaseFood(boolean baseFood) {
        isBaseFood = baseFood;
    }


    public String getFoodItemSizeCode() {
        return foodItemSizeCode;
    }

    public void setFoodItemSizeCode(String foodItemSizeCode) {
        this.foodItemSizeCode = foodItemSizeCode;
    }


}
