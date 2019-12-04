package emerge.project.onmeal.ui.activity.menu;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.luseen.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;
import emerge.project.onmeal.utils.entittes.OutletItems;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class MenuInteractorImpil implements MenuInteractor {

    Realm realm = Realm.getDefaultInstance();
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    List<MenuCategoryItems> menuCategory;
    List<MenuItems> menuItemsList;
    List<MenuItems>  mainFoodByFood;

    @Override
    public void getMenuCategory(String outletID, String menuTitleID, String foodCat, final OnGetMenuCategoryFinishedListener onGetMenuCategoryFinishedListener) {

        final ArrayList<MenuCategoryItems> menuCategoryItemsArrayList = new ArrayList<MenuCategoryItems>();
        if (outletID == null) {
            menuCategoryItemsArrayList.add(new MenuCategoryItems("0", foodCat, true));
            onGetMenuCategoryFinishedListener.menuCategory(menuCategoryItemsArrayList);
        } else {
            try {
                apiService.getMenuCategoriesForOutlet(Integer.parseInt(outletID))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<MenuCategoryItems>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<MenuCategoryItems> respond) {
                                menuCategory = respond;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onGetMenuCategoryFinishedListener.menuCategoryFail("Communication error, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (menuCategory != null) {
                                    try {
                                        if (menuCategory.isEmpty()) {
                                            onGetMenuCategoryFinishedListener.menuCategoryFail("Communication error, Please try again");
                                        } else {
                                            for (int i = 0; i < menuCategory.size(); i++) {
                                                if (i == 0) {
                                                    menuCategoryItemsArrayList.add(new MenuCategoryItems(menuCategory.get(i).getMenuCategoryID(), menuCategory.get(i).getMenuCategory(), true));
                                                } else {
                                                    menuCategoryItemsArrayList.add(new MenuCategoryItems(menuCategory.get(i).getMenuCategoryID(), menuCategory.get(i).getMenuCategory(), false));
                                                }
                                            }
                                            onGetMenuCategoryFinishedListener.menuCategory(menuCategoryItemsArrayList);
                                        }
                                    } catch (NullPointerException exNull) {
                                        onGetMenuCategoryFinishedListener.menuCategoryFail("Communication error, Please try again");
                                    }
                                } else {
                                    onGetMenuCategoryFinishedListener.menuCategoryFail("Communication error, Please try again");
                                }
                            }
                        });
            } catch (Exception ex) {
                onGetMenuCategoryFinishedListener.menuCategoryFail("Communication error, Please try again");
            }
        }


    }

    @Override
    public void getMainFoodByOutlet(final String outletId, final String menuCategoryID, final OnMainFoodByOutletListener otMainFoodByOutletListener) {

        final ArrayList<MenuItems> menuItemsArrayList = new ArrayList<MenuItems>();
        try {


            apiService.getMainFoodByOutlet(Integer.parseInt(outletId), Integer.parseInt(menuCategoryID))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MenuItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<MenuItems> respond) {
                            menuItemsList = respond;
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            if (menuItemsList != null) {
                                try {
                                    if (menuItemsList.isEmpty()) {
                                        otMainFoodByOutletListener.getMainFoodByOutletLoadEmpty();
                                    } else {
                                        for (int i = 0; i < menuItemsList.size(); i++) {
                                            menuItemsArrayList.add(new MenuItems(menuItemsList.get(i).getMenuId(), menuItemsList.get(i).getFoodId(), menuItemsList.get(i).getFoodName(),
                                                    menuItemsList.get(i).getFoodCoverImage(), menuItemsList.get(i).getOutletId(), menuItemsList.get(i).getOutletName(), menuItemsList.get(i).getMenuCategoryID(), menuItemsList.get(i).getMenuCategory()));
                                        }
                                        otMainFoodByOutletListener.getMainFoodByOutletSuccess(menuItemsArrayList);
                                    }
                                } catch (NullPointerException exNull) {
                                    otMainFoodByOutletListener.getMainFoodByOutletLoadFail("Communication error, Please try again", outletId, menuCategoryID);
                                }
                            } else {
                                otMainFoodByOutletListener.getMainFoodByOutletLoadFail("Communication error, Please try again", outletId, menuCategoryID);
                            }
                        }
                    });
        } catch (Exception ex) {
            otMainFoodByOutletListener.getMainFoodByOutletLoadFail("Communication error, Please try again", outletId, menuCategoryID);
        }

    }

    @Override
    public void getMainFoodByFood(String foodId, final OnMainFoodByFoodListener onMainFoodByFoodListener) {

        String dispatchType = "";
        String addressId;
        final ArrayList<OutletItems> outletItemsArrayList = new ArrayList<OutletItems>();

        Address address = realm.where(Address.class).findFirst();
        User user = realm.where(User.class).findFirst();

        if (address == null) {
            addressId = "";
            dispatchType = "P";
        } else {
            addressId = address.getAddressId();
            dispatchType = "D";
        }



        try {


            final ArrayList<MenuItems> menuItemsArrayList = new ArrayList<MenuItems>();
            apiService.getMainFoodByFood(foodId, Integer.parseInt(user.getUserId()), addressId, dispatchType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MenuItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<MenuItems> respond) {
                            mainFoodByFood = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onMainFoodByFoodListener.getMainFoodByFoodLoadFail("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (mainFoodByFood != null) {
                                try {
                                    if (mainFoodByFood.isEmpty()) {
                                        onMainFoodByFoodListener.getMainFoodByFoodLoadEmpty();
                                    } else {
                                        for (int i = 0; i < mainFoodByFood.size(); i++) {
                                            menuItemsArrayList.add(new MenuItems(mainFoodByFood.get(i).getMenuId(), mainFoodByFood.get(i).getFoodId(), mainFoodByFood.get(i).getFoodName(),
                                                    mainFoodByFood.get(i).getFoodCoverImage(), mainFoodByFood.get(i).getOutletId(), mainFoodByFood.get(i).getOutletName(), mainFoodByFood.get(i).getMenuCategoryID(), mainFoodByFood.get(i).getMenuCategory()));
                                        }
                                        onMainFoodByFoodListener.getMainFoodByFoodSuccess(menuItemsArrayList);
                                    }
                                } catch (NullPointerException exNull) {
                                    onMainFoodByFoodListener.getMainFoodByFoodLoadFail("Communication error, Please try again");
                                }
                            } else {
                                onMainFoodByFoodListener.getMainFoodByFoodLoadFail("Communication error, Please try again");
                            }
                        }
                    });

        } catch (Exception ex) {
            onMainFoodByFoodListener.getMainFoodByFoodLoadFail("Communication error, Please try again");
        }
    }

    @Override
    public void geSelectedMenuCategory(String menuCategoryID, OnSelectedMenuCategoryListener onSelectedMenuCategoryListener) {
        onSelectedMenuCategoryListener.selectedMenuCategor(menuCategoryID);
    }

    @Override
    public void geSelectedMenuDetails(int menuId, int foodId, int outletId, String menuName, String menuImg, String outlet, int menucat,OnSelectedMenuDetailsListener onSelectedMenuDetailsListener) {
        onSelectedMenuDetailsListener.selectedMenuDetails(menuId, foodId, outletId, menuName, menuImg, outlet,menucat);
    }

    @Override
    public void checkCartAvailability(OnCheckCartAvailabilityListener onCheckCartAvailabilityListener) {

        realm = Realm.getDefaultInstance();
        int count = 0;
        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            count = count + ns.getQuantity();
        }

        if (count == 0) {
            onCheckCartAvailabilityListener.cartNotAvailable();
        } else {
            onCheckCartAvailabilityListener.cartAvailable();
        }

    }

    @Override
    public void cartCount(OnCartCountListener onCartCountListener) {
        realm = Realm.getDefaultInstance();
        int count = 0;
        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            count = count + ns.getQuantity();
        }
        onCartCountListener.cartCountNumber(count);

    }

    @Override
    public void checkCartAvailabilityForCart(OnCheckCartAvailabilityForCartListener onCheckCartAvailabilityForCartListener) {

        realm = Realm.getDefaultInstance();
        int count = 0;
        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            count = count + ns.getQuantity();
        }

        if (count == 0) {
            onCheckCartAvailabilityForCartListener.cartNotAvailableForCart();
        } else {
            onCheckCartAvailabilityForCartListener.cartAvailableForCart();
        }
    }
}
