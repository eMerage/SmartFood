package emerge.project.onmeal.ui.activity.home;


import android.content.Context;

import com.luseen.logger.Logger;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.OutletImages;
import emerge.project.onmeal.utils.entittes.OutletItems;
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

public class HomeInteractorImpil implements HomeInteractor {

    Realm realm = Realm.getDefaultInstance();
    EncryptedPreferences encryptedPreferences;
    private static final String DISPATCH_TYPE = "dispatch_type";

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    List<HomeFavouriteItems> favouriteItems;
    List<Foodtems> mainFood;

    ArrayList<OutletItems> getOutletList;

    @Override
    public void getFavouriteItems(final OnFavouriteItemsLoadFinishedListener onFavouriteItemsLoadFinishedListener) {
        try {

            final ArrayList<HomeFavouriteItems> favouriteItemsArrayList = new ArrayList<HomeFavouriteItems>();

            User user = realm.where(User.class).findFirst();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    RealmResults<MenuSubItems> resultsMenuSubItems = realm.where(MenuSubItems.class).findAll();
                    resultsMenuSubItems.deleteAllFromRealm();


                    RealmResults<CartDetail> resultCartDetail = realm.where(CartDetail.class).findAll();
                    resultCartDetail.deleteAllFromRealm();

                    RealmResults<CartHeader> resultsCartHeader = realm.where(CartHeader.class).findAll();
                    resultsCartHeader.deleteAllFromRealm();


                }
            });

            apiService.getFavouriteItems(Integer.parseInt(user.getUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<HomeFavouriteItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<HomeFavouriteItems> respond) {
                            favouriteItems = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (favouriteItems != null) {
                                try {
                                    if (favouriteItems.isEmpty()) {
                                        onFavouriteItemsLoadFinishedListener.getFavouriteItemsEmpty();
                                    } else {
                                        for (int i = 0; i < favouriteItems.size(); i++) {
                                            favouriteItemsArrayList.add(new HomeFavouriteItems(favouriteItems.get(i).getFavId(),
                                                    favouriteItems.get(i).getFavName(), favouriteItems.get(i).getFavImgUrl(),
                                                    favouriteItems.get(i).getFavType(), favouriteItems.get(i).getFavTypeId()));
                                        }
                                        onFavouriteItemsLoadFinishedListener.getFavouriteItemsSuccessful(favouriteItemsArrayList);
                                    }

                                } catch (NullPointerException exNull) {
                                    onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
                                }
                            } else {
                                onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
                            }
                        }
                    });
        } catch (Exception ex) {
            onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
        }

    }

    @Override
    public void getMainFood(Context mContext,String serachtext, final OnMainFoodLoadFinishedListener onMainFoodLoadFinishedListener) {
        try {
            encryptedPreferences = new EncryptedPreferences.Builder(mContext).withEncryptionPassword("122547895511").build();

            String dispatch = encryptedPreferences.getString(DISPATCH_TYPE, "");

            String dispatchType = "";
            String addressId;
            final ArrayList<Foodtems> foodtemsArrayList = new ArrayList<Foodtems>();

            User user = realm.where(User.class).findFirst();
            Address address = realm.where(Address.class).findFirst();
            if (dispatch.equals("Pickup")) {
                addressId = "";
                dispatchType = "P";
            } else {
                addressId = address.getAddressId();
                dispatchType = "D";
            }


            apiService.getMainFood(2, Integer.parseInt(user.getUserId()), addressId, dispatchType, "M",serachtext)
            .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<Foodtems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Foodtems> respond) {
                            mainFood = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onMainFoodLoadFinishedListener.getMainFoodFail("Something went wrong, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (mainFood != null) {
                                try {
                                    if (mainFood.isEmpty()) {
                                        onMainFoodLoadFinishedListener.getMainFoodEmpty();
                                    } else {
                                        for (int i = 0; i < mainFood.size(); i++) {
                                            foodtemsArrayList.add(new Foodtems(mainFood.get(i).getFoodName(), mainFood.get(i).getFoodId(), mainFood.get(i).getFoodCoverImage(), mainFood.get(i).getMenuCategory()));
                                        }
                                        onMainFoodLoadFinishedListener.getMainFoodSuccessful(foodtemsArrayList);
                                    }
                                } catch (NullPointerException exNull) {
                                    onMainFoodLoadFinishedListener.getMainFoodFail("Something went wrong, Please try again");
                                }
                            } else {
                                onMainFoodLoadFinishedListener.getMainFoodFail("Something went wrong, Please try again");
                            }
                        }
                    });

        } catch (Exception ex) {
            onMainFoodLoadFinishedListener.getMainFoodFail("Something went wrong, Please try again");
        }
    }

    @Override
    public void getOutlet(Context mContext,String serachtext ,final OnOutletLoadFinishedListener onOutletLoadFinishedListener) {
        try {


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



            apiService.getOutlet(Integer.parseInt(user.getUserId()), addressId, dispatchType,serachtext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<OutletItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(ArrayList<OutletItems> respond) {
                            getOutletList = respond;
                        }
                        @Override
                        public void onError(Throwable e) {
                            onOutletLoadFinishedListener.getOutletFail("Something went wrong, Please try again");
                        }
                        @Override
                        public void onComplete() {
                            if (getOutletList != null) {
                                try {
                                    if (getOutletList.isEmpty()) {
                                        onOutletLoadFinishedListener.getOutletEmpty();
                                    } else {
                                        onOutletLoadFinishedListener.getOutletSuccessful(getOutletList);
                                    }

                                } catch (NullPointerException exNull) {
                                    onOutletLoadFinishedListener.getOutletFail("Something went wrong, Please try again");
                                }

                            } else {
                                onOutletLoadFinishedListener.getOutletFail("Something went wrong, Please try again");
                            }
                        }
                    });

        } catch (Exception ex) {
            onOutletLoadFinishedListener.getOutletFail("Something went wrong, Please try again");
        }
    }
}
