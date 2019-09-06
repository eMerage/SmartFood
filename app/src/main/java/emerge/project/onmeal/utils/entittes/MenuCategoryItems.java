package emerge.project.onmeal.utils.entittes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuCategoryItems implements Serializable {

    @SerializedName("menuCategoryID")
    String menuCategoryID;

    @SerializedName("menuCategory")
    String menuCategory;


    boolean isSelect;


    public MenuCategoryItems(String menuCategoryID, String menuCategory, boolean isselect) {
        this.menuCategoryID = menuCategoryID;
        this.menuCategory = menuCategory;
        this.isSelect = isselect;
    }


    public String getMenuCategoryID() {
        return menuCategoryID;
    }

    public void setMenuCategoryID(String menuCategoryID) {
        this.menuCategoryID = menuCategoryID;
    }

    public String getMenuCategory() {
        return menuCategory;
    }

    public void setMenuCategory(String menuCategory) {
        this.menuCategory = menuCategory;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
