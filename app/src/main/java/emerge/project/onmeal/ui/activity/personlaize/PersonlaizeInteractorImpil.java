package emerge.project.onmeal.ui.activity.personlaize;


import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.luseen.logger.Logger;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.menu.MenuInteractor;
import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;
import emerge.project.onmeal.utils.entittes.MenuSize;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class PersonlaizeInteractorImpil implements PersonlaizeInteractor {

    EncryptedPreferences encryptedPreferences;
    private static final String DISPATCH_TYPE = "dispatch_type";
    Realm realm = Realm.getDefaultInstance();

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    int checkMaxOrderQtyResult = 9;
    ArrayList<FoodCategoryItems> foodCategoryItemsArrayList;
    List<FoodCategoryItems> foodCategory;
    List<MenuSubItems> subFoodsList;

    List<MenuSize> menuSize;

    @Override
    public void getFoodCategory(Context mContext, final int outletID, final int menuTitleID, final int outletMenuTitleID, final OnGetFoodCategoryFinishedListener onGetFoodCategoryFinishedListener) {

        encryptedPreferences = new EncryptedPreferences.Builder(mContext).withEncryptionPassword("122547895511").build();

        String dispatch = encryptedPreferences.getString(DISPATCH_TYPE, "");
        final ArrayList<FoodCategoryItems> foodCategoryItemsArrayList = new ArrayList<FoodCategoryItems>();

        String dispatchType = "";
        String addressId;

        User user = realm.where(User.class).findFirst();
        Address address = realm.where(Address.class).findFirst();


        if (dispatch.equals("Pickup")) {
            addressId = "0";
            dispatchType = "P";
        } else {
            addressId = address.getAddressId();
            dispatchType = "D";
        }
        try {
            apiService.GetFoodCategoriesForOutletMenuTitle(outletID, 2, menuTitleID, outletMenuTitleID, Integer.parseInt(user.getUserId()), Integer.parseInt(addressId), dispatchType, "M")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<FoodCategoryItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<FoodCategoryItems> respond) {
                            foodCategory = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onGetFoodCategoryFinishedListener.foodCategoryFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
                        }

                        @Override
                        public void onComplete() {
                            if (foodCategory != null) {
                                try {
                                    if (foodCategory.isEmpty()) {
                                        onGetFoodCategoryFinishedListener.foodCategoryFail("No Items, Please try again", outletID, menuTitleID, outletMenuTitleID);
                                    } else {
                                        for (int i = 0; i < foodCategory.size(); i++) {
                                            if (i == 0) {
                                                foodCategoryItemsArrayList.add(new FoodCategoryItems(foodCategory.get(i).getFoodItemCategoryID(), foodCategory.get(i).getFoodItemCategory(), true));
                                            } else {
                                                foodCategoryItemsArrayList.add(new FoodCategoryItems(foodCategory.get(i).getFoodItemCategoryID(), foodCategory.get(i).getFoodItemCategory(), false));
                                            }
                                        }
                                        onGetFoodCategoryFinishedListener.foodCategory(foodCategoryItemsArrayList);
                                    }
                                } catch (NullPointerException exNull) {
                                    onGetFoodCategoryFinishedListener.foodCategoryFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
                                }

                            } else {
                                onGetFoodCategoryFinishedListener.foodCategoryFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
                            }
                        }
                    });
        } catch (Exception ex) {
            onGetFoodCategoryFinishedListener.foodCategoryFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
        }

    }

    @Override
    public void geSelectedFoodCategory(int foodItemCategoryID, int position, OnSelectedFoodCategoryListener onSelectedFoodCategoryListener) {
        onSelectedFoodCategoryListener.selectedFoodCategor(foodItemCategoryID, position);
    }


    @Override
    public void getSubFoods(final Context mContext, final int menuId, final int foodId, final int outletId, final int foodItemCategoryID, ArrayList<FoodCategoryItems> foodCategoryItemsArrayList, final OnSubFoodsListener onSubFoodsListener) {

        RealmResults<MenuSubItems> menuSubItemsdetail = realm.where(MenuSubItems.class)
                .equalTo("outletID", outletId)
                .and()
                .equalTo("menuTitleID", menuId)
                .and()
                .equalTo("outletMenuTitleID", foodId)
                .and()
                .equalTo("foodItemCategoryID", foodItemCategoryID)
                .findAll();


        if (menuSubItemsdetail.isEmpty()) {
            encryptedPreferences = new EncryptedPreferences.Builder(mContext).withEncryptionPassword("122547895511").build();
            String dispatch = encryptedPreferences.getString(DISPATCH_TYPE, "");

            String dispatchType = "";
            String addressId;

            User user = realm.where(User.class).findFirst();
            Address address = realm.where(Address.class).findFirst();
            if (dispatch.equals("Pickup")) {
                addressId = "0";
                dispatchType = "P";
            } else {
                addressId = address.getAddressId();
                dispatchType = "D";
            }

            for (int i = 0; i < foodCategoryItemsArrayList.size(); i++) {
                try {
                    apiService.GetMenuFoodItemForOutlets(outletId, 2, menuId, foodId, Integer.parseInt(user.getUserId()), Integer.parseInt(addressId), dispatchType, "M", foodCategoryItemsArrayList.get(i).getFoodItemCategoryID())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<MenuSubItems>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(List<MenuSubItems> respond) {
                                    subFoodsList = respond;
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {
                                    if (subFoodsList != null) {
                                        try {
                                            if (subFoodsList.isEmpty()) {
                                                Toast.makeText(mContext, "No Product available", Toast.LENGTH_SHORT).show();
                                            } else {
                                                addSubItems(menuId, foodId, outletId, foodItemCategoryID, subFoodsList, onSubFoodsListener);
                                            }
                                        } catch (NullPointerException exNull) {
                                            Toast.makeText(mContext, "No Product available", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(mContext, "No Product available", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } catch (Exception ex) {
                    Toast.makeText(mContext, "No Product available", Toast.LENGTH_SHORT).show();
                }

            }




           /* ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<MenuSubItems>> call = apiService.GetMenuFoodItemForOutlets(outletId, 2, menuId, foodId, Integer.parseInt(user.getUserId()), Integer.parseInt(addressId), dispatchType, "M", foodItemCategoryID);
            call.enqueue(new Callback<List<MenuSubItems>>() {
                @Override
                public void onResponse(Call<List<MenuSubItems>> call, Response<List<MenuSubItems>> response) {
                    List<MenuSubItems> menuSubItemsList = response.body();
                    if (menuSubItemsList.isEmpty()) {
                        Toast.makeText(mContext, "No Product available", Toast.LENGTH_SHORT).show();
                    } else {
                        addSubItems(menuId, foodId, outletId, foodItemCategoryID, menuSubItemsList, onSubFoodsListener);
                    }
                }

                @Override
                public void onFailure(Call<List<MenuSubItems>> call, Throwable t) {
                    Logger.e(t.toString());

                }
            });
*/
        } else {
            getSubItems(menuId, foodId, outletId, foodItemCategoryID, onSubFoodsListener);


        }

    }

    @Override
    public void getMenuSize(Context mContext, final int outletID, final int menuTitleID, final int outletMenuTitleID, final OnGetMenuSizeFinishedListener onGetMenuSizeFinishedListener) {

        encryptedPreferences = new EncryptedPreferences.Builder(mContext).withEncryptionPassword("122547895511").build();

        String dispatch = encryptedPreferences.getString(DISPATCH_TYPE, "");
        final ArrayList<MenuSize> menuSizeArrayList = new ArrayList<MenuSize>();

        String dispatchType = "";
        String addressId;

        User user = realm.where(User.class).findFirst();
        Address address = realm.where(Address.class).findFirst();


        if (dispatch.equals("Pickup")) {
            addressId = "0";
            dispatchType = "P";
        } else {
            addressId = address.getAddressId();
            dispatchType = "D";
        }

        try {
            apiService.GetFoodItemSizesForOutletMenuTitle(outletID, 2, menuTitleID, outletMenuTitleID, Integer.parseInt(user.getUserId()), Integer.parseInt(addressId), dispatchType, "M")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<MenuSize>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<MenuSize> respond) {
                            menuSize = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onGetMenuSizeFinishedListener.menuSizeFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
                        }

                        @Override
                        public void onComplete() {
                            if (menuSize != null) {
                                try {
                                    if (menuSize.isEmpty()) {
                                    } else {
                                        for (int i = 0; i < menuSize.size(); i++) {
                                            if (i == 0) {
                                                menuSizeArrayList.add(new MenuSize(menuSize.get(i).getFoodItemSizeCode(), menuSize.get(i).getFoodItemSize(), true));
                                            } else {
                                                menuSizeArrayList.add(new MenuSize(menuSize.get(i).getFoodItemSizeCode(), menuSize.get(i).getFoodItemSize(), false));
                                            }
                                        }
                                        onGetMenuSizeFinishedListener.menuSize(menuSizeArrayList);
                                    }

                                } catch (NullPointerException exNull) {
                                    onGetMenuSizeFinishedListener.menuSizeFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
                                }
                            } else {
                                onGetMenuSizeFinishedListener.menuSizeFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
                            }
                        }
                    });
        } catch (Exception ex) {
            onGetMenuSizeFinishedListener.menuSizeFail("Something went wrong, Please try again", outletID, menuTitleID, outletMenuTitleID);
        }

    }

    @Override
    public void getTotalPrice(int qty, String size, OnTotalPriceListener onTotalPriceListener) {

        realm = Realm.getDefaultInstance();

        Double totalPrice = 0.0;

        for (MenuSubItems no : realm.where(MenuSubItems.class).equalTo("isSelect", true).findAll()) {
            int aaa = no.getFoodItemID();
            MenuSubItems cartdetail = realm.where(MenuSubItems.class).equalTo("foodItemID", no.getFoodItemID())
                    .equalTo("foodItemSizeCode", size)
                    .and()
                    .equalTo("outletMenuTitleID", no.getOutletMenuTitleID())
                    .and()
                    .equalTo("foodname", no.getFoodname())
                    .findFirst();


            if (cartdetail == null) {

                RealmResults<MenuSubItems> results = realm.where(MenuSubItems.class)
                        .equalTo("foodItemID", no.getFoodItemID())
                        .and()
                        .findAll();

                long max = results.max("foodItemPrice").longValue();

                totalPrice = totalPrice + (max * no.getQuantity());

            } else {
                totalPrice = totalPrice + (cartdetail.getFoodItemPrice() * no.getQuantity());

            }

        }
        totalPrice = totalPrice * qty;
        onTotalPriceListener.totalPrice(totalPrice);

    }

    @Override
    public void setMenuSize(String sizeCode, OnSetMenuSizeListener onSetMenuSizeListener) {
        onSetMenuSizeListener.selectedMenuSize(sizeCode);
    }

    @Override
    public void addToCart(final SelectedMenuDetails selectedMenuDetails, final int qty, final String size, final Double price, final OnAddToCartListener onAddToCartListener) {
        realm = Realm.getDefaultInstance();

        RealmResults<MenuSubItems> results = realm.where(MenuSubItems.class)
                .equalTo("isSelect", true)
                .and()
                .greaterThan("quantity", 0)
                .findAll();

        if (results.isEmpty()) {
            onAddToCartListener.itemAddToCartNoItems();
        } else {
            int resultCheckMainFood = checkMainFoodAdded(results.get(0).getMenuTitleID());
            if (resultCheckMainFood == 1) {
                MenuSubItems resultsforBase = realm.where(MenuSubItems.class)
                        .equalTo("isBaseFood", true)
                        .and()
                        .equalTo("menuTitleID", results.get(0).getMenuTitleID())
                        .findFirst();

                onAddToCartListener.itemAddToCartFaild("Please add " + resultsforBase.getFoodItemCategory());

                //   onAddToCartListener.itemAddToCartFaild("Please add Base");
            } else if (resultCheckMainFood == 2) {
                onAddToCartListener.itemAddToCartFaild("Please add at least one Meat");
            } else if (resultCheckMainFood == 3) {
                onAddToCartListener.itemAddToCartFaild("Please add at least one Vegetable");
            } else if (resultCheckMainFood == 4) {
                onAddToCartListener.itemAddToCartFaild("Please add at least one Extra Item");
            } else if (resultCheckMainFood == 5) {
                onAddToCartListener.itemAddToCartFaild("Please add at least one Other Item");
            } else {
                addCartHeader(selectedMenuDetails, qty, size, price, onAddToCartListener);
            }



           /* ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<JsonObject> call = apiService.GetOutletMenuSetup(selectedMenuDetails.getFoodId());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.isSuccessful()){
                        try {
                            int maxOrderQty = 0;
                            JSONObject subFoodResponse = null;
                            try {
                                subFoodResponse = new JSONObject(response.body().toString());
                                maxOrderQty = subFoodResponse.getInt("maxOrderQty");
                            } catch (JSONException e) {
                                onAddToCartListener.itemAddToCartOrderQtyExeed();
                            }

                            if (maxOrderQty < qty) {
                                onAddToCartListener.itemAddToCartOrderQtyExeed();

                            } else {
                                int resultCheckMainFood = checkMainFoodAdded();
                                if (resultCheckMainFood == 1) {
                                    onAddToCartListener.itemAddToCartFaild("Please add Base Food");
                                } else if (resultCheckMainFood == 2) {
                                    onAddToCartListener.itemAddToCartFaild("Please add at least one Meat");
                                } else if (resultCheckMainFood == 3) {
                                    onAddToCartListener.itemAddToCartFaild("Please add at least one Vegetable");
                                } else if (resultCheckMainFood == 4) {
                                    onAddToCartListener.itemAddToCartFaild("Please add at least one Extra Item");
                                } else if (resultCheckMainFood == 5) {
                                    onAddToCartListener.itemAddToCartFaild("Please add at least one Other Item");
                                } else {
                                    addCartHeader(selectedMenuDetails, qty, size, price, onAddToCartListener);
                                }

                            }

                        }catch (NullPointerException exNull){
                            onAddToCartListener.itemAddToCartSocketFail(selectedMenuDetails, "Something went wrong, Please try again");
                        }
                    }else {
                        onAddToCartListener.itemAddToCartSocketFail(selectedMenuDetails, "Something went wrong, Please try again");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    onAddToCartListener.itemAddToCartSocketFail(selectedMenuDetails, "Something went wrong, Please try again");
                    Logger.e(t.toString());

                }
            });
*/

        }


    }

    @Override
    public void checkCartAvailability(OnCheckCartAvailabilityListener onCheckCartAvailabilityListener) {
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
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<MenuSubItems> results = realm.where(MenuSubItems.class)
                        .findAll();

                if (results.size() == 0) {

                } else {
                    for (int i = 0; i < results.size(); i++) {
                        results.get(i).setSelect(false);
                    }

                }

            }
        });


        int count = 0;
        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            count = count + ns.getQuantity();
        }

        onCartCountListener.cartCountNumber(count);

    }

    @Override
    public void clareMenus(final OnClareMenusListener onClareMenusListener) {


        int count = 0;
        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            count = count + ns.getQuantity();
        }


        realm = Realm.getDefaultInstance();
        final int finalCount = count;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmResults<MenuSubItems> resultsMenuSubItems = bgRealm.where(MenuSubItems.class).findAll();
                resultsMenuSubItems.deleteAllFromRealm();


                onClareMenusListener.clareMenusFinsh(finalCount);
            }
        });


    }


    public void addCartHeader(final SelectedMenuDetails selectedMenuDetails, final int qty, final String size, final Double price, final OnAddToCartListener onAddToCartListener) {
        final Long cartHeaderId;
        cartHeaderId = cratePrimerykeyID();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                CartHeader crtHeader = bgRealm.createObject(CartHeader.class, cartHeaderId);
                crtHeader.setOutletMenuTitleID(selectedMenuDetails.getFoodId());
                crtHeader.setOutletID(selectedMenuDetails.getOutletId());
                crtHeader.setOutlet(selectedMenuDetails.getOutletName());
                crtHeader.setName(selectedMenuDetails.getMenuName());
                crtHeader.setImageUrl(selectedMenuDetails.getMenuImg());
                crtHeader.setSize(size);
                crtHeader.setQuantity(qty);
                crtHeader.setPriceTotal(price);
                crtHeader.setActive(true);

                addCartDetails(qty, size, cartHeaderId, onAddToCartListener);

            }
        });

    }

    private void addCartDetails(final int qty, final String size, final Long cartHeaderId, final OnAddToCartListener onAddToCartListener) {


        for (MenuSubItems no : realm.where(MenuSubItems.class)
                .equalTo("isSelect", true)
                .findAll()) {


            MenuSubItems cartdetail = realm.where(MenuSubItems.class)
                    .equalTo("foodItemID", no.getFoodItemID())
                    .and()
                    .equalTo("foodItemSizeCode", size)
                    .and()
                    .equalTo("foodItemType", no.getFoodItemType())
                    .findFirst();

            long lastInsertedId;

            RealmResults<CartDetail> allTransactions = realm.where(CartDetail.class).findAll();
            if (allTransactions.size() == 0) {
                lastInsertedId = 1;
            } else {
                lastInsertedId = allTransactions.last().getCartDetailId();
            }

            if (cartdetail != null) {

                CartDetail cartDetail = realm.createObject(CartDetail.class, (cratePrimerykeyID() + lastInsertedId));
                cartDetail.setCartHeaderId(cartHeaderId);
                cartDetail.setMenuCategory(cartdetail.getMenuCategory());
                cartDetail.setMenuCategoryID(cartdetail.getMenuCategoryID());
                cartDetail.setMenuTitle(cartdetail.getMenuTitle());
                cartDetail.setMenuTitleID(cartdetail.getMenuTitleID());
                cartDetail.setOutletMenuName(cartdetail.getOutletMenuName());
                cartDetail.setOutletMenuTitleID(cartdetail.getOutletMenuTitleID());
                cartDetail.setFoodItemCategoryID(cartdetail.getFoodItemCategoryID());
                cartDetail.setFoodItemCategory(cartdetail.getFoodItemCategory());
                cartDetail.setFoodItemID(cartdetail.getFoodItemID());
                cartDetail.setFoodId(cartdetail.getFoodId());
                cartDetail.setFoodname(cartdetail.getFoodname());
                cartDetail.setFoodItemTypeID(cartdetail.getFoodItemTypeID());
                cartDetail.setFoodItemType(cartdetail.getFoodItemType());
                cartDetail.setFoodItemSizeID(cartdetail.getFoodItemSizeID());
                cartDetail.setFoodItemSize(cartdetail.getFoodItemSize());
                cartDetail.setFoodItemSizeCode(cartdetail.getFoodItemSizeCode());
                cartDetail.setOutletID(cartdetail.getOutletID());
                cartDetail.setOutlet(cartdetail.getOutlet());
                cartDetail.setFoodItemPrice(cartdetail.getFoodItemPrice());
                cartDetail.setImageUrl(cartdetail.getImageUrl());
                cartDetail.setQuantity(no.getQuantity());
                cartDetail.setIsFoodItemCategoryCompulsory(cartdetail.getIsFoodItemCategoryCompulsory());
                cartDetail.setBaseFood(cartdetail.getBaseFood());

            } else {

                CartDetail cartDetail = realm.createObject(CartDetail.class, (cratePrimerykeyID() + lastInsertedId));
                cartDetail.setCartHeaderId(cartHeaderId);
                cartDetail.setMenuCategory(no.getMenuCategory());
                cartDetail.setMenuCategoryID(no.getMenuCategoryID());
                cartDetail.setMenuTitle(no.getMenuTitle());
                cartDetail.setMenuTitleID(no.getMenuTitleID());
                cartDetail.setOutletMenuName(no.getOutletMenuName());
                cartDetail.setOutletMenuTitleID(no.getOutletMenuTitleID());
                cartDetail.setFoodItemCategoryID(no.getFoodItemCategoryID());
                cartDetail.setFoodItemCategory(no.getFoodItemCategory());
                cartDetail.setFoodItemID(no.getFoodItemID());
                cartDetail.setFoodId(no.getFoodId());
                cartDetail.setFoodname(no.getFoodname());
                cartDetail.setFoodItemTypeID(no.getFoodItemTypeID());
                cartDetail.setFoodItemType(no.getFoodItemType());
                cartDetail.setFoodItemSizeID(no.getFoodItemSizeID());
                cartDetail.setFoodItemSize(no.getFoodItemSize());
                cartDetail.setFoodItemSizeCode(no.getFoodItemSizeCode());
                cartDetail.setOutletID(no.getOutletID());
                cartDetail.setOutlet(no.getOutlet());
                cartDetail.setFoodItemPrice(no.getFoodItemPrice());
                cartDetail.setImageUrl(no.getImageUrl());
                cartDetail.setIsFoodItemCategoryCompulsory(no.getIsFoodItemCategoryCompulsory());
                cartDetail.setBaseFood(no.getBaseFood());
                cartDetail.setQuantity(no.getQuantity());


            }


        }


        deleteAfterAddToCart(onAddToCartListener);

    }


    private void deleteAfterAddToCart(final OnAddToCartListener onAddToCartListener) {

        RealmResults<MenuSubItems> results = realm.where(MenuSubItems.class)
                .findAll();

        if (results.size() == 0) {

        } else {
            for (int i = 0; i < results.size(); i++) {
                results.get(i).setSelect(false);
            }

        }

        int count = 0;
        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            count = count + ns.getQuantity();
        }

        onAddToCartListener.itemAddedToCart(count);

    }


    private int checkMainFoodAdded(int menuTitleID) {

        int result;

        if (isBaseItemCompulsory(menuTitleID)) {
            result = 1;
        } else if (isMeatItemCompulsory(menuTitleID)) {
            result = 2;
        } else if (isVegetableItemCompulsory(menuTitleID)) {
            result = 3;
        } else if (isExtraItemCompulsory(menuTitleID)) {
            result = 4;
        } else if (isOtherItemCompulsory(menuTitleID)) {
            result = 5;
        } else {
            result = 6;
        }

        return result;

    }


    private boolean isBaseItemCompulsory(int menuTitleID) {

        final boolean[] result = {false};

        MenuSubItems menuSubItemsForRice = realm.where(MenuSubItems.class)
                .equalTo("isBaseFood", true)
                .and()
                .equalTo("menuTitleID", menuTitleID)
                .findFirst();

        if (menuSubItemsForRice == null) {
            result[0] = false;
        } else {
            if (menuSubItemsForRice.getIsFoodItemCategoryCompulsory() == 1) {

                RealmResults<MenuSubItems> cartdetailForRice = realm.where(MenuSubItems.class)
                        .equalTo("isBaseFood", true)
                        .and()
                        .greaterThan("quantity", 0)
                        .and()
                        .equalTo("isSelect", true)
                        .findAll();
                if (cartdetailForRice.size() == 0) {
                    result[0] = true;
                } else {
                    result[0] = false;
                }
            } else {
                result[0] = false;
            }
        }


        return result[0];
    }


    private boolean isMeatItemCompulsory(int menuTitleID) {

        final boolean[] result = {false};
        MenuSubItems menuSubItemsForMeat = realm.where(MenuSubItems.class)
                .equalTo("foodItemCategory", "Meat")
                .and()
                .equalTo("menuTitleID", menuTitleID)
                .findFirst();


        if (menuSubItemsForMeat == null) {
            result[0] = false;
        } else {
            if (menuSubItemsForMeat.getIsFoodItemCategoryCompulsory() == 1) {
                RealmResults<MenuSubItems> cartdetailForMeat = realm.where(MenuSubItems.class)
                        .equalTo("foodItemCategory", "Meat")
                        .and()
                        .greaterThan("quantity", 0)
                        .and()
                        .equalTo("isSelect", true)
                        .findAll();

                if (cartdetailForMeat.size() == 0) {
                    result[0] = true;
                } else {
                    result[0] = false;
                }
            } else {
                result[0] = false;
            }
        }


        return result[0];
    }


    private boolean isVegetableItemCompulsory(int menuTitleID) {
        final boolean[] result = {false};

        MenuSubItems menuSubItemsForVegetable = realm.where(MenuSubItems.class)
                .equalTo("foodItemCategory", "Vegetable")
                .and()
                .equalTo("menuTitleID", menuTitleID)
                .findFirst();

        if (menuSubItemsForVegetable == null) {

            result[0] = false;
        } else {
            if (menuSubItemsForVegetable.getIsFoodItemCategoryCompulsory() == 1) {
                RealmResults<MenuSubItems> cartdetailForVegetable = realm.where(MenuSubItems.class)
                        .equalTo("foodItemCategory", "Vegetable")
                        .and()
                        .greaterThan("quantity", 0)
                        .and()
                        .equalTo("isSelect", true)
                        .findAll();

                if (cartdetailForVegetable.size() == 0) {
                    result[0] = true;
                } else {
                    result[0] = false;
                }
            } else {

                result[0] = false;
            }
        }

        return result[0];

    }


    private boolean isOtherItemCompulsory(int menuTitleID) {


        final boolean[] result = {false};

        MenuSubItems menuSubItemsForExtra = realm.where(MenuSubItems.class)
                .equalTo("foodItemCategory", "Other")
                .and()
                .equalTo("menuTitleID", menuTitleID)
                .findFirst();

        if (menuSubItemsForExtra == null) {
            result[0] = false;
        } else {

            if (menuSubItemsForExtra.getIsFoodItemCategoryCompulsory() == 1) {
                RealmResults<MenuSubItems> cartdetailForOther = realm.where(MenuSubItems.class)
                        .equalTo("foodItemCategory", "Other")
                        .and()
                        .greaterThan("quantity", 0)
                        .and()
                        .equalTo("isSelect", true)
                        .findAll();


                if (cartdetailForOther.size() == 0) {
                    result[0] = true;
                } else {
                    result[0] = false;
                }

            } else {

                result[0] = false;
            }
        }


        return result[0];
    }


    private boolean isExtraItemCompulsory(int menuTitleID) {


        final boolean[] result = {false};
        MenuSubItems menuSubItemsForOther = realm.where(MenuSubItems.class)
                .equalTo("foodItemType", "Extra")
                .and()
                .equalTo("menuTitleID", menuTitleID)
                .findFirst();


        if (menuSubItemsForOther == null) {

            result[0] = false;
        } else {

            if (menuSubItemsForOther.getIsFoodItemCategoryCompulsory() == 1) {
                RealmResults<MenuSubItems> cartdetailForExtra = realm.where(MenuSubItems.class)
                        .equalTo("foodItemType", "Extra")
                        .and()
                        .greaterThan("quantity", 0)
                        .and()
                        .equalTo("isSelect", true)
                        .findAll();
                if (cartdetailForExtra.size() == 0) {
                    result[0] = true;
                } else {
                    result[0] = false;
                }

            } else {
                result[0] = false;
            }
        }


        return result[0];
    }


    public void addSubItems(final int menuId, final int foodId, final int outletId, final int foodItemCategoryID, final List<MenuSubItems> menuSubItems, final OnSubFoodsListener onSubFoodsListener) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                for (int i = 0; i < menuSubItems.size(); i++) {
                    long lastInsertedId;


                    RealmResults<MenuSubItems> allTransactions = bgRealm.where(MenuSubItems.class).findAll();
                    if (allTransactions.size() == 0) {
                        lastInsertedId = 1;
                    } else {
                        lastInsertedId = allTransactions.last().getRowId();
                    }
                    MenuSubItems menuItems = bgRealm.createObject(MenuSubItems.class, (cratePrimerykeyID() + lastInsertedId));


                    System.out.println("bbbbbbbbbb : "+menuSubItems.get(i).getFoodname());


                    menuItems.setMenuCategory(menuSubItems.get(i).getMenuCategory());
                    menuItems.setMenuCategoryID(menuSubItems.get(i).getMenuCategoryID());
                    menuItems.setFoodItemID(menuSubItems.get(i).getFoodItemID());
                    menuItems.setMenuTitle(menuSubItems.get(i).getMenuTitle());
                    menuItems.setMenuTitleID(menuSubItems.get(i).getMenuTitleID());
                    menuItems.setOutletMenuName(menuSubItems.get(i).getOutletMenuName());
                    menuItems.setOutletMenuTitleID(menuSubItems.get(i).getOutletMenuTitleID());
                    menuItems.setFoodItemCategoryID(menuSubItems.get(i).getFoodItemCategoryID());
                    menuItems.setFoodItemCategory(menuSubItems.get(i).getFoodItemCategory());
                    menuItems.setFoodId(menuSubItems.get(i).getFoodId());
                    menuItems.setFoodname(menuSubItems.get(i).getFoodname());
                    menuItems.setFoodItemTypeID(menuSubItems.get(i).getFoodItemTypeID());
                    menuItems.setFoodItemType(menuSubItems.get(i).getFoodItemType());
                    menuItems.setFoodItemSize(menuSubItems.get(i).getFoodItemSize());
                    menuItems.setFoodItemSizeID(menuSubItems.get(i).getFoodItemSizeID());
                    menuItems.setOutletID(menuSubItems.get(i).getOutletID());
                    menuItems.setOutlet(menuSubItems.get(i).getOutlet());
                    menuItems.setFoodItemPrice(menuSubItems.get(i).getFoodItemPrice());
                    menuItems.setImageUrl(menuSubItems.get(i).getImageUrl());
                    menuItems.setMaxOrderQty(menuSubItems.get(i).getMaxOrderQty());
                    menuItems.setMaxCurryCount(menuSubItems.get(i).getMaxCurryCount());
                    menuItems.setMaxExtrasQty(menuSubItems.get(i).getMaxExtrasQty());
                    menuItems.setMaxFreeItemQty(menuSubItems.get(i).getMaxFreeItemQty());
                    menuItems.setIsFoodItemCategoryCompulsory(menuSubItems.get(i).getIsFoodItemCategoryCompulsory());
                    menuItems.setBaseFood(menuSubItems.get(i).getBaseFood());
                    menuItems.setFoodItemSizeCode(menuSubItems.get(i).getFoodItemSizeCode());


                    menuItems.setSelect(false);


                }


                getSubItems(menuId, foodId, outletId, foodItemCategoryID, onSubFoodsListener);


            }
        });


    }

    private void getSubItems(int menuId, int foodId, int outletId, int catid, OnSubFoodsListener onSubFoodsListener) {


        final ArrayList<MenuSubItems> menuSubItemsArrayList = new ArrayList<MenuSubItems>();

        for (MenuSubItems no : realm.where(MenuSubItems.class)
                .equalTo("outletID", outletId)
                .and()
                .equalTo("menuTitleID", menuId)
                .and()
                .equalTo("outletMenuTitleID", foodId)
                .and()
                .equalTo("foodItemCategoryID", catid)
                .and()
                .distinct("foodItemID")
                .findAll()) {


            System.out.println("ffffffff : "+no.getFoodname());


            if (no.isSelect()) {
                menuSubItemsArrayList.add(new MenuSubItems(no.getMenuCategory(), no.getMenuCategoryID(), no.getMenuTitle(), no.getMenuTitleID(), no.getOutletMenuName(),
                        no.getOutletMenuTitleID(), no.getFoodItemCategoryID(), no.getFoodItemCategory(), no.getFoodItemID(), no.getFoodId(), no.getFoodname(), no.getFoodItemTypeID(),
                        no.getFoodItemType(), no.getFoodItemSizeID(), no.getFoodItemSize(), no.getFoodItemSizeCode(), no.getOutletID(), no.getOutlet(), no.getFoodItemPrice(), no.getImageUrl(), no.getMaxOrderQty(),
                        no.getMaxCurryCount(), no.getMaxExtrasQty(), no.getMaxFreeItemQty(), true, no.getBaseFood(), no.getQuantity()));

            } else {
                menuSubItemsArrayList.add(new MenuSubItems(no.getMenuCategory(), no.getMenuCategoryID(), no.getMenuTitle(), no.getMenuTitleID(), no.getOutletMenuName(),
                        no.getOutletMenuTitleID(), no.getFoodItemCategoryID(), no.getFoodItemCategory(), no.getFoodItemID(), no.getFoodId(), no.getFoodname(), no.getFoodItemTypeID(),
                        no.getFoodItemType(), no.getFoodItemSizeID(), no.getFoodItemSize(), no.getFoodItemSizeCode(), no.getOutletID(), no.getOutlet(), no.getFoodItemPrice(), no.getImageUrl(), no.getMaxOrderQty(),
                        no.getMaxCurryCount(), no.getMaxExtrasQty(), no.getMaxFreeItemQty(), false, no.getBaseFood(), no.getQuantity()));


            }


        }


        onSubFoodsListener.subFoods(menuSubItemsArrayList);


    }


    private Long cratePrimerykeyID() {
        User user = realm.where(User.class).findFirst();
        Calendar c = Calendar.getInstance();
        String numberFromTime = String.valueOf(c.get(Calendar.YEAR)).substring(1) + String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DATE)) + String.valueOf(c.get(Calendar.HOUR)) + String.valueOf(c.get(Calendar.MINUTE)) + String.valueOf(c.get(Calendar.SECOND)) + String.valueOf(c.get(Calendar.MILLISECOND));
        if (numberFromTime.length() == 16) {
        } else {
            if (numberFromTime.length() > 16) {
                numberFromTime = numberFromTime.substring(0, 16);
            } else {
                int len = 16 - numberFromTime.length();
                for (int i = 0; i < len; i++) {
                    numberFromTime = numberFromTime + "0";
                }
            }
        }
        return Long.parseLong((numberFromTime + user.getUserId()));
    }


}
