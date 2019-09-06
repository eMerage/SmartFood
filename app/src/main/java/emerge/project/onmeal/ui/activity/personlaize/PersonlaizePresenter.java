package emerge.project.onmeal.ui.activity.personlaize;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PersonlaizePresenter {


    void getFoodCategory(Context mContext,int outletID, int menuTitleID, int outletMenuTitleID );

    void geSelectedFoodCategory(int foodItemCategoryID,int position);

    void getSubFoods(Context mContext,int menuId, int foodId, int outletId,int foodItemCategoryID,ArrayList<FoodCategoryItems> foodCategoryItemsArrayList);

    void getMenuSize(Context mContext, int outletID, int menuTitleID, int outletMenuTitleID);


    void getTotalPrice(int qty,String size );


    void setMenuSize(String sizeCode);


    void addToCart(SelectedMenuDetails selectedMenuDetails,int qty,String size,Double price);


    void checkCartAvailability();

    void cartCount();



    void clareMenus();




}
