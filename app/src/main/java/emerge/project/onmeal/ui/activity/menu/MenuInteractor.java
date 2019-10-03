package emerge.project.onmeal.ui.activity.menu;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.ui.activity.personlaize.PersonlaizeInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface MenuInteractor {

    interface OnGetMenuCategoryFinishedListener {
        void menuCategory(ArrayList<MenuCategoryItems> menuCategoryItems);
        void menuCategoryFail(String msg);
    }

    void getMenuCategory(String outletID, String menuTitleID, String foodCat, OnGetMenuCategoryFinishedListener onGetMenuCategoryFinishedListener);


    interface OnMainFoodByOutletListener {
        void getMainFoodByOutletSuccess(ArrayList<MenuItems> menuItems);
        void getMainFoodByOutletLoadFail(String msg,String outletId, String menuCategoryID);
        void getMainFoodByOutletLoadEmpty();
    }

    void getMainFoodByOutlet(String outletId, String menuCategoryID, OnMainFoodByOutletListener otMainFoodByOutletListener);



    interface OnMainFoodByFoodListener {
        void getMainFoodByFoodSuccess(ArrayList<MenuItems> menuItems);
        void getMainFoodByFoodLoadFail(String msg);
        void getMainFoodByFoodLoadEmpty();

    }
    void getMainFoodByFood(String foodId, OnMainFoodByFoodListener onMainFoodByFoodListener);


    interface OnSelectedMenuCategoryListener {
        void selectedMenuCategor(String menuCategoryID);
    }
    void geSelectedMenuCategory(String menuCategoryID,OnSelectedMenuCategoryListener onSelectedMenuCategoryListener);


    interface OnSelectedMenuDetailsListener {
        void selectedMenuDetails(int menuId,  int foodId,  int outletId, final String menuName,String menuImg,String outlet,int menucat);
    }
    void geSelectedMenuDetails( int menuId,  int foodId,  int outletId, final String menuName,String menuImg,String outlet,int menucat,OnSelectedMenuDetailsListener onSelectedMenuDetailsListener );


    interface OnCheckCartAvailabilityListener {
        void cartAvailable();
        void cartNotAvailable();
    }
    void checkCartAvailability(OnCheckCartAvailabilityListener onCheckCartAvailabilityListener);


    interface OnCartCountListener {
        void cartCountNumber(int count);
    }
    void cartCount(OnCartCountListener onCartCountListener);



    interface OnCheckCartAvailabilityForCartListener {
        void cartAvailableForCart();
        void cartNotAvailableForCart();
    }
    void checkCartAvailabilityForCart(OnCheckCartAvailabilityForCartListener onCheckCartAvailabilityForCartListener);


}
