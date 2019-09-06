package emerge.project.onmeal.ui.activity.menu;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.ui.activity.landing.LandingInteractorImpil;
import emerge.project.onmeal.ui.activity.landing.LandingPresenter;
import emerge.project.onmeal.ui.activity.landing.LandingView;
import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class MenuPresenterImpli implements MenuPresenter,
        MenuInteractor.OnGetMenuCategoryFinishedListener,
        MenuInteractor.OnMainFoodByOutletListener,
        MenuInteractor.OnSelectedMenuCategoryListener,
        MenuInteractor.OnMainFoodByFoodListener,
        MenuInteractor.OnSelectedMenuDetailsListener,
        MenuInteractor.OnCheckCartAvailabilityListener,
        MenuInteractor.OnCartCountListener, MenuInteractor.OnCheckCartAvailabilityForCartListener {


    private MenuView menuView;
    MenuInteractor menuInteractor;


    public MenuPresenterImpli(MenuView menuview) {


        this.menuView = menuview;
        this.menuInteractor = new MenuInteractorImpil();

    }


    //--------getMenuCategory
    @Override
    public void getMenuCategory(String outletID, String menuTitleID, String foodCat) {
        menuInteractor.getMenuCategory(outletID, menuTitleID, foodCat, this);
    }


    @Override
    public void menuCategory(ArrayList<MenuCategoryItems> menuCategoryItems) {
        menuView.menuCategory(menuCategoryItems);
    }

    @Override
    public void menuCategoryFail(String msg) {
        menuView.menuCategoryFail(msg);
    }

    //getMenuCategory------


    @Override
    public void getMainFoodByOutlet(String outletId, String menuCategoryID) {
        menuInteractor.getMainFoodByOutlet(outletId, menuCategoryID, this);
    }

    @Override
    public void getMainFoodByOutletSuccess(ArrayList<MenuItems> menuItems) {
        menuView.getMainFoodByOutletSuccess(menuItems);
    }

    @Override
    public void getMainFoodByOutletLoadFail(String msg, String outletId, String menuCategoryID) {
        menuView.getMainFoodByOutletLoadFail(msg, outletId, menuCategoryID);
    }

    @Override
    public void getMainFoodByOutletLoadEmpty() {
        menuView.getMainFoodByOutletLoadEmpty();
    }


    @Override
    public void geSelectedMenuCategory(String menuCategoryID) {
        menuInteractor.geSelectedMenuCategory(menuCategoryID, this);
    }


    @Override
    public void selectedMenuCategor(String menuCategoryID) {
        menuView.selectedMenuCategor(menuCategoryID);
    }


    @Override
    public void getMainFoodByFood(String foodId) {
        menuInteractor.getMainFoodByFood(foodId, this);
    }


    @Override
    public void getMainFoodByFoodSuccess(ArrayList<MenuItems> menuItems) {
        menuView.getMainFoodByFoodSuccess(menuItems);
    }

    @Override
    public void getMainFoodByFoodLoadFail(String msg) {
        menuView.getMainFoodByFoodLoadFail(msg);
    }

    @Override
    public void getMainFoodByFoodLoadEmpty() {
        menuView.getMainFoodByFoodLoadEmpty();
    }


    @Override
    public void geSelectedMenuDetails(int menuId, int foodId, int outletId, String menuName, String menuImg, String outlet) {
        menuInteractor.geSelectedMenuDetails(menuId, foodId, outletId, menuName, menuImg, outlet, this);
    }


    @Override
    public void selectedMenuDetails(int menuId, int foodId, int outletId, String menuName, String menuImg, String outlet) {
        menuView.selectedMenuDetails(menuId, foodId, outletId, menuName, menuImg, outlet);
    }


    @Override
    public void checkCartAvailability() {
        menuInteractor.checkCartAvailability(this);
    }


    @Override
    public void cartAvailable() {
        menuView.cartAvailable();
    }

    @Override
    public void cartNotAvailable() {
        menuView.cartNotAvailable();
    }


    @Override
    public void cartCount() {
        menuInteractor.cartCount(this);
    }


    @Override
    public void cartCountNumber(int count) {
        menuView.cartCountNumber(count);
    }


    @Override
    public void checkCartAvailabilityForCart() {
        menuInteractor.checkCartAvailabilityForCart(this);
    }

    @Override
    public void cartAvailableForCart() {
        menuView.cartAvailableForCart();
    }

    @Override
    public void cartNotAvailableForCart() {
        menuView.cartNotAvailableForCart();
    }
}
