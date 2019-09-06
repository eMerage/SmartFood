package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SelectedMenuDetails implements Serializable {

    int menuId;
    int foodId;
    int outletId;
    String menuName;
    String menuImg;
    String outletName;

    public SelectedMenuDetails() {
    }

    public SelectedMenuDetails(int menuId, int foodId, int outletId, String menuName,String menuImg,String outletName) {
        this.menuId = menuId;
        this.foodId = foodId;
        this.outletId = outletId;
        this.menuName = menuName;
        this.menuImg = menuImg;
        this.outletName = outletName;
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

    public int getOutletId() {
        return outletId;
    }

    public void setOutletId(int outletId) {
        this.outletId = outletId;
    }


    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }


    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
}
