package emerge.project.onmeal.ui.activity.personlaize;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;
import emerge.project.onmeal.utils.entittes.MenuSize;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PersonlaizeView {


    void foodCategory(ArrayList<FoodCategoryItems> foodCategoryItems);

    void foodCategoryFail(String msg, int outletID, int menuTitleID, int outletMenuTitleID);

    void selectedFoodCategor(int foodItemCategoryID,int position);

    void subFoodsEmpty();

    void subFoods(ArrayList<MenuSubItems> menuSubItemsArrayList);

    void subFoodsFail(int menuId, int foodId, int outletId, int foodItemCategoryID, String msg);


    void menuSize(ArrayList<MenuSize> menuItemSizes);

    void menuSizeFail(String msg, int outletID, int menuTitleID, int outletMenuTitleID);


    void totalPrice(Double price);


    void selectedMenuSize(String sizeCode);


    void itemAddedToCart(int cartCount);
    void itemAddToCartFaild(String msg);
    void itemAddToCartSocketFail(SelectedMenuDetails selectedMenuDetail,String msg);
    void itemAddToCartOrderQtyExeed();
    void itemAddToCartNoItems();



    void cartAvailable();
    void cartNotAvailable();


    void cartCountNumber(int count);





        void clareMenusFinsh(int cartcount);






}
