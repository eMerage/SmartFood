package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuItemsError implements Serializable {

    @SerializedName("menuTitleID")
    int menuId;

    @SerializedName("outletMenuTitleID")
    int outletMenuTitleID;


    @SerializedName("menuTitle")
    String menuTitle;

    @SerializedName("imageUrl")
    String imageUrl;


    public MenuItemsError() {
    }

    public MenuItemsError(int menuId, int outletMenuTitleID, String menuTitle, String imageUrl) {
        this.menuId = menuId;
        this.outletMenuTitleID = outletMenuTitleID;
        this.menuTitle = menuTitle;
        this.imageUrl = imageUrl;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getOutletMenuTitleID() {
        return outletMenuTitleID;
    }

    public void setOutletMenuTitleID(int outletMenuTitleID) {
        this.outletMenuTitleID = outletMenuTitleID;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
