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
    int menuCat;


    public SelectedMenuDetails() {
    }

    public SelectedMenuDetails(int menuId, int foodId, int outletId, String menuName,String menuImg,String outletName,int menucat) {
        this.menuId = menuId;
        this.foodId = foodId;
        this.outletId = outletId;
        this.menuName = menuName;
        this.menuImg = menuImg;
        this.outletName = outletName;
        this.menuCat = menucat;
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

    public int getMenuCat() {
        return menuCat;
    }

    public void setMenuCat(int menuCat) {
        this.menuCat = menuCat;
    }
}
