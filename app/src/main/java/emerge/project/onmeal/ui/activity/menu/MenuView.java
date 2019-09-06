package emerge.project.onmeal.ui.activity.menu;


import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface MenuView {


    void menuCategory(ArrayList<MenuCategoryItems> menuCategoryItems);

    void menuCategoryFail(String msg);


    void getMainFoodByOutletSuccess(ArrayList<MenuItems> menuItems);

    void getMainFoodByOutletLoadFail(String msg, String outletId, String menuCategoryID);

    void getMainFoodByOutletLoadEmpty();


    void getMainFoodByFoodSuccess(ArrayList<MenuItems> menuItems);

    void getMainFoodByFoodLoadFail(String msg);

    void getMainFoodByFoodLoadEmpty();


    void selectedMenuCategor(String menuCategoryID);

    void selectedMenuDetails(int menuId, int foodId, int outletId, final String menuName, String menuImg, String outlet);


    void cartAvailable();

    void cartNotAvailable();


    void cartCountNumber(int count);


    void cartAvailableForCart();

    void cartNotAvailableForCart();


}
