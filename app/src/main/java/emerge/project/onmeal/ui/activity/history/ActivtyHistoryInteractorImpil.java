package emerge.project.onmeal.ui.activity.history;


import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractor;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.OrderHistoryItems;
import emerge.project.onmeal.utils.entittes.OrderHistoryMenu;
import emerge.project.onmeal.utils.entittes.OrderHistorySubMenu;
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

public class ActivtyHistoryInteractorImpil implements ActivtyHistoryInteractor {


    Realm  realm = Realm.getDefaultInstance();
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    List<OrderHistoryItems> orderHistory;

    JsonObject orderHistoryDetails;

    @Override
    public void getOrderHistory(final OnOrderHistoryLoadFinishedListener onOrderHistoryLoadFinishedListener) {

        User user = realm.where(User.class).findFirst();
        final ArrayList<OrderHistoryItems> orderHistoryItemsCurrent = new ArrayList<OrderHistoryItems>();
        final ArrayList<OrderHistoryItems> orderHistoryItemsPast = new ArrayList<OrderHistoryItems>();
        try {
            apiService.orderHistor(user.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<OrderHistoryItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<OrderHistoryItems> respond) {
                            orderHistory = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onOrderHistoryLoadFinishedListener.getOrderHistoryFail(String.valueOf(R.string.server_error_msg));
                        }

                        @Override
                        public void onComplete() {
                            if (orderHistory != null) {
                                try {
                                    if (orderHistory.isEmpty()) {
                                        onOrderHistoryLoadFinishedListener.getOrderHistoryEmpty();
                                    } else {
                                        for (int i = 0; i < orderHistory.size(); i++) {
                                            if (orderHistory.get(i).getStatusCode().equals("ODCP")) {
                                                orderHistoryItemsPast.add(new OrderHistoryItems(orderHistory.get(i).getOrderID(), orderHistory.get(i).getOrderDate(), orderHistory.get(i).getOrderTotal(),
                                                        orderHistory.get(i).getDispatchType(), orderHistory.get(i).getStatusCode(), "",orderHistory.get(i).getOutletName()));
                                            } else {
                                                orderHistoryItemsCurrent.add(new OrderHistoryItems(orderHistory.get(i).getOrderID(), orderHistory.get(i).getOrderDate(), orderHistory.get(i).getOrderTotal(),
                                                        orderHistory.get(i).getDispatchType(), orderHistory.get(i).getStatusCode(), "",orderHistory.get(i).getOutletName()));
                                            }
                                        }
                                        onOrderHistoryLoadFinishedListener.getOrderHistoryCurrent(orderHistoryItemsCurrent);
                                        onOrderHistoryLoadFinishedListener.getOrderHistoryPAst(orderHistoryItemsPast);
                                    }

                                } catch (NullPointerException exNull) {
                                    onOrderHistoryLoadFinishedListener.getOrderHistoryFail(String.valueOf(R.string.server_error_msg));
                                }
                            } else {
                                onOrderHistoryLoadFinishedListener.getOrderHistoryFail(String.valueOf(R.string.server_error_msg));
                            }
                        }
                    });
        } catch (Exception ex) {
            onOrderHistoryLoadFinishedListener.getOrderHistoryFail(String.valueOf(R.string.server_error_msg));
        }
    }

    @Override
    public void getOrderHistoryDetails(final String orderID, final int level, final OnOrderHistoryDetailsFinishedListener onOrderHistoryDetailsFinishedListener) {


        onOrderHistoryDetailsFinishedListener.getOrderHistoryDetailsStart();
        final ArrayList<OrderHistoryMenu> menusArrayList = new ArrayList<OrderHistoryMenu>();//main




        try {
            apiService.orderHistorDetails(Integer.parseInt(orderID))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject respond) {
                            orderHistoryDetails = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onOrderHistoryDetailsFinishedListener.getOrderHistoryDetailsFail(String.valueOf(R.string.server_error_msg), orderID,level);
                        }

                        @Override
                        public void onComplete() {
                            if (orderHistoryDetails != null) {
                                JSONObject historyListist = null;
                                try {
                                    historyListist = new JSONObject(orderHistoryDetails.toString());
                                    JSONArray orderMenusList;
                                    JSONArray orderMenusDetailsList;

                                    JSONObject outletJson;

                                    orderMenusList = historyListist.getJSONArray("orderMenus");
                                    orderMenusDetailsList = historyListist.getJSONArray("orderMenuDetails");

                                    outletJson = historyListist.getJSONObject("orderOutlet");

                                    JSONArray outletImages=outletJson.getJSONArray("images");
                                    ArrayList<OutletImages> images =  new ArrayList<>();

                                    for (int i = 0; i < outletImages.length(); i++) {
                                        JSONObject jsonData = outletImages.getJSONObject(i);
                                        images.add(new OutletImages(jsonData.getString("id"),jsonData.getString("name"),jsonData.getString("imageUrl")));
                                    }

                                        OutletItems outlet = new OutletItems(outletJson.getString("id"),
                                                outletJson.getString("name"), outletJson.getString("imageUrl"), outletJson.getInt("outletTypeID"),
                                                outletJson.getString("city"), outletJson.getString("ownerName"), outletJson.getDouble("latitude"),
                                                outletJson.getDouble("longitude"),images, outletJson.getString("phone01"), outletJson.getString("eMail"));



                                    for (int i = 0; i < orderMenusList.length(); i++) {
                                        JSONObject jsonData = orderMenusList.getJSONObject(i);

                                        final ArrayList<OrderHistorySubMenu> foodsArrayList = new ArrayList<OrderHistorySubMenu>();//sub

                                        for (int k = 0; k < orderMenusDetailsList.length(); k++) {
                                            JSONObject jsonDatafoodsyList = orderMenusDetailsList.getJSONObject(k);


                                            if (jsonDatafoodsyList.getString("cartID").equals(jsonData.getString("cartID"))) {
                                                foodsArrayList.add(new OrderHistorySubMenu(jsonDatafoodsyList.getString("id"), jsonDatafoodsyList.getString("name"), jsonDatafoodsyList.getInt("foodQty"),
                                                        jsonDatafoodsyList.getBoolean("isBase"), jsonDatafoodsyList.getString("foodItemCategory"), jsonDatafoodsyList.getString("foodItemTypeCode")));
                                            } else {

                                            }

                                        }
                                        menusArrayList.add(new OrderHistoryMenu(jsonData.getInt("orderID"), jsonData.getString("id"), jsonData.getString("name"),
                                                jsonData.getString("menuSizeCode"),jsonData.getDouble("menuPrice"), jsonData.getInt("menuQty"), foodsArrayList));

                                    }
                                    onOrderHistoryDetailsFinishedListener.getOrderHistoryDetails(menusArrayList,level,outlet);


                                } catch (JSONException e) {
                                    onOrderHistoryDetailsFinishedListener.getOrderHistoryDetailsFail(String.valueOf(R.string.server_error_msg), orderID,level);
                                }



                            } else {
                                onOrderHistoryDetailsFinishedListener.getOrderHistoryDetailsFail(String.valueOf(R.string.server_error_msg), orderID,level);
                            }
                        }
                    });

        } catch (Exception ex) {
            onOrderHistoryDetailsFinishedListener.getOrderHistoryDetailsFail(String.valueOf(R.string.server_error_msg), orderID,level);
        }

    }

    @Override
    public void signOut(Context context, final OnsignOutinishedListener onsignOutinishedListener) {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> resultsAddress = realm.where(User.class).findAll();
                resultsAddress.deleteAllFromRealm();


                onsignOutinishedListener.signOutSuccess();
            }
        });

    }

}
