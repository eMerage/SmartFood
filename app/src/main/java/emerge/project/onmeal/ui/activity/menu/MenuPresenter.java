package emerge.project.onmeal.ui.activity.menu;


import com.google.android.gms.maps.model.LatLng;

import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface MenuPresenter {
    void getMenuCategory(String outletID,String menuTitleID,String foodCat);

    void getMainFoodByOutlet(String outletId,String menuCategoryID);

    void geSelectedMenuCategory(String menuCategoryID);

    void getMainFoodByFood(String foodId);

    void geSelectedMenuDetails( int menuId,  int foodId,  int outletId, final String menuName,String menuImg,String outlet);

    void checkCartAvailability();

    void cartCount();


    void checkCartAvailabilityForCart();



}
