package emerge.project.onmeal.ui.activity.personlaize;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.ui.activity.menu.MenuInteractor;
import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;
import emerge.project.onmeal.utils.entittes.MenuSize;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PersonlaizeInteractor {

    interface OnGetFoodCategoryFinishedListener {
        void foodCategory(ArrayList<FoodCategoryItems> foodCategoryItems);
        void foodCategoryFail(String msg, int outletID, int menuTitleID, int outletMenuTitleID);
    }
    void getFoodCategory(Context mContext, int outletID, int menuTitleID, int outletMenuTitleID, OnGetFoodCategoryFinishedListener onGetFoodCategoryFinishedListener);


    interface OnSelectedFoodCategoryListener {
        void selectedFoodCategor(int foodItemCategoryID,int position);
    }
    void geSelectedFoodCategory(int foodItemCategoryID, int position,OnSelectedFoodCategoryListener onSelectedFoodCategoryListener);


    interface OnSubFoodsListener {
        void subFoodsEmpty();
        void subFoods(ArrayList<MenuSubItems> menuSubItemsArrayList);
        void subFoodsFail(int menuId, int foodId, int outletId, int foodItemCategoryID, String msg);
    }

    void getSubFoods(Context mContext, int menuId, int foodId, int outletId, int foodItemCategoryID,ArrayList<FoodCategoryItems> foodCategoryItemsArrayList, OnSubFoodsListener onSubFoodsListener);


    interface OnGetMenuSizeFinishedListener {
        void menuSize(ArrayList<MenuSize> menuItemSizes);
        void menuSizeFail(String msg, int outletID, int menuTitleID, int outletMenuTitleID);
    }
    void getMenuSize(Context mContext, int outletID, int menuTitleID, int outletMenuTitleID, OnGetMenuSizeFinishedListener onGetMenuSizeFinishedListener);



    interface OnTotalPriceListener {
        void totalPrice(Double price);
    }
    void getTotalPrice(int qty,String size,OnTotalPriceListener onTotalPriceListener);


    interface OnSetMenuSizeListener {
        void selectedMenuSize(String sizeCode);
    }
    void setMenuSize(String sizeCode,OnSetMenuSizeListener onSetMenuSizeListener);


    interface OnAddToCartListener {
        void itemAddedToCart(int cartCount);
        void itemAddToCartFaild(String msg);
        void itemAddToCartSocketFail(SelectedMenuDetails selectedMenuDetail,String msg);
        void itemAddToCartOrderQtyExeed();
        void itemAddToCartNoItems();
    }
    void addToCart(SelectedMenuDetails selectedMenuDetails,int qty,String size,Double price,OnAddToCartListener onAddToCartListener);



    interface OnCheckCartAvailabilityListener {
        void cartAvailable();
        void cartNotAvailable();
    }
    void checkCartAvailability(OnCheckCartAvailabilityListener onCheckCartAvailabilityListener);



    interface OnCartCountListener {
        void cartCountNumber(int count);
    }
    void cartCount(OnCartCountListener onCartCountListener);




    interface OnClareMenusListener {
        void clareMenusFinsh(int cartcount);
    }
    void clareMenus(OnClareMenusListener onClareMenusListener);




}
