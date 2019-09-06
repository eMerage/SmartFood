package emerge.project.onmeal.ui.activity.personlaize;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.ui.activity.menu.MenuInteractor;
import emerge.project.onmeal.ui.activity.menu.MenuInteractorImpil;
import emerge.project.onmeal.ui.activity.menu.MenuPresenter;
import emerge.project.onmeal.ui.activity.menu.MenuView;
import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;
import emerge.project.onmeal.utils.entittes.MenuSize;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class PersonlaizePresenterImpli implements PersonlaizePresenter,
        PersonlaizeInteractor.OnGetFoodCategoryFinishedListener,
        PersonlaizeInteractor.OnSelectedFoodCategoryListener,
        PersonlaizeInteractor.OnSubFoodsListener,
        PersonlaizeInteractor.OnGetMenuSizeFinishedListener,
        PersonlaizeInteractor.OnTotalPriceListener,
        PersonlaizeInteractor.OnSetMenuSizeListener,
        PersonlaizeInteractor.OnAddToCartListener,
PersonlaizeInteractor.OnCheckCartAvailabilityListener,
        PersonlaizeInteractor.OnCartCountListener,PersonlaizeInteractor.OnClareMenusListener{


    private PersonlaizeView personlaizeView;
    PersonlaizeInteractor personlaizeInteractor;


    public PersonlaizePresenterImpli(PersonlaizeView personlaizeview) {


        this.personlaizeView = personlaizeview;
        this.personlaizeInteractor = new PersonlaizeInteractorImpil();

    }


    @Override
    public void getFoodCategory(Context mContext, int outletID, int menuTitleID, int outletMenuTitleID) {
        personlaizeInteractor.getFoodCategory(mContext, outletID, menuTitleID, outletMenuTitleID, this);
    }


    @Override
    public void foodCategory(ArrayList<FoodCategoryItems> foodCategoryItems) {
        personlaizeView.foodCategory(foodCategoryItems);
    }

    @Override
    public void foodCategoryFail(String msg, int outletID, int menuTitleID, int outletMenuTitleID) {
        personlaizeView.foodCategoryFail(msg, outletID, menuTitleID, outletMenuTitleID);
    }


    @Override
    public void geSelectedFoodCategory(int foodItemCategoryID,int position) {
        personlaizeInteractor.geSelectedFoodCategory(foodItemCategoryID, position,this);
    }


    @Override
    public void selectedFoodCategor(int foodItemCategoryID,int position) {
        personlaizeView.selectedFoodCategor(foodItemCategoryID,position);

    }


    @Override
    public void getSubFoods(Context mContext, int menuId, int foodId, int outletId, int foodItemCategoryID,ArrayList<FoodCategoryItems> foodCategoryItemsArrayList) {
        personlaizeInteractor.getSubFoods(mContext, menuId, foodId, outletId, foodItemCategoryID, foodCategoryItemsArrayList,this);
    }


    @Override
    public void subFoodsEmpty() {
        personlaizeView.subFoodsEmpty();
    }

    @Override
    public void subFoods(ArrayList<MenuSubItems> menuSubItemsArrayList) {
        personlaizeView.subFoods(menuSubItemsArrayList);
    }

    @Override
    public void subFoodsFail(int menuId, int foodId, int outletId, int foodItemCategoryID, String msg) {
        personlaizeView.subFoodsFail(menuId, foodId, outletId, foodItemCategoryID, msg);
    }


    @Override
    public void getMenuSize(Context mContext, int outletID, int menuTitleID, int outletMenuTitleID) {
        personlaizeInteractor.getMenuSize(mContext, outletID, menuTitleID, outletMenuTitleID, this);
    }


    @Override
    public void menuSize(ArrayList<MenuSize> menuItemSizes) {
        personlaizeView.menuSize(menuItemSizes);
    }

    @Override
    public void menuSizeFail(String msg, int outletID, int menuTitleID, int outletMenuTitleID) {
        personlaizeView.menuSizeFail(msg, outletID, menuTitleID, outletMenuTitleID);
    }


    @Override
    public void getTotalPrice(int qty, String size) {
        personlaizeInteractor.getTotalPrice(qty, size, this);
    }


    @Override
    public void totalPrice(Double price) {
        personlaizeView.totalPrice(price);
    }


    @Override
    public void setMenuSize(String sizeCode) {
        personlaizeInteractor.setMenuSize(sizeCode, this);
    }


    @Override
    public void selectedMenuSize(String sizeCode) {
        personlaizeView.selectedMenuSize(sizeCode);
    }


    @Override
    public void addToCart(SelectedMenuDetails selectedMenuDetails,int qty,String size,Double price) {
        personlaizeInteractor.addToCart(selectedMenuDetails, qty,size,price,this);
    }


    @Override
    public void itemAddedToCart(int cartCount) {
        personlaizeView.itemAddedToCart(cartCount);
    }

    @Override
    public void itemAddToCartFaild(String msg) {
        personlaizeView.itemAddToCartFaild(msg);
    }

    @Override
    public void itemAddToCartSocketFail(SelectedMenuDetails selectedMenuDetail, String msg) {
        personlaizeView.itemAddToCartSocketFail(selectedMenuDetail,msg);
    }


    @Override
    public void itemAddToCartOrderQtyExeed() {
        personlaizeView.itemAddToCartOrderQtyExeed();
    }

    @Override
    public void itemAddToCartNoItems() {
        personlaizeView.itemAddToCartNoItems();
    }










    @Override
    public void checkCartAvailability() {
        personlaizeInteractor.checkCartAvailability(this);
    }




    @Override
    public void cartAvailable() {
        personlaizeView.cartAvailable();
    }

    @Override
    public void cartNotAvailable() {
        personlaizeView.cartNotAvailable();
    }



    @Override
    public void cartCount() {
        personlaizeInteractor.cartCount(this);
    }


    @Override
    public void cartCountNumber(int count) {
        personlaizeView.cartCountNumber(count);
    }




    @Override
    public void clareMenus() {
        personlaizeInteractor.clareMenus(this);
    }



    @Override
    public void clareMenusFinsh(int cartcount) {
        personlaizeView.clareMenusFinsh(cartcount);
    }
}
