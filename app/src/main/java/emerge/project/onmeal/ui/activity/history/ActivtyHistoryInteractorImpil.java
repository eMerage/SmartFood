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
import emerge.project.onmeal.utils.entittes.v2.Orders.OrderMenus;
import emerge.project.onmeal.utils.entittes.v2.Orders.OrdersData;
import emerge.project.onmeal.utils.entittes.v2.Orders.OrdersList;
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


    Realm realm = Realm.getDefaultInstance();
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    //  List<OrderHistoryItems> orderHistory;


    OrdersData orderHistory;

    JsonObject orderHistoryDetails;

    OutletItems outletItems;

    @Override
    public void getOrderHistory(final OnOrderHistoryLoadFinishedListener onOrderHistoryLoadFinishedListener) {

        User user = realm.where(User.class).findFirst();
        final ArrayList<OrdersList> orderHistoryItemsCurrent = new ArrayList<OrdersList>();
        final ArrayList<OrdersList> orderHistoryItemsPast = new ArrayList<OrdersList>();


        System.out.println("dfdffdfdfdfdfdff "+user.getUserId());


        try {
            apiService.orderHistory(user.getUserId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<OrdersData>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(OrdersData respond) {
                            orderHistory = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onOrderHistoryLoadFinishedListener.getOrderHistoryFail("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (orderHistory.getStatus()) {
                                for (OrdersList oh : orderHistory.getOrdersList()) {
                                    if (oh.getLastStatus().equals("ODCP")) {
                                        orderHistoryItemsPast.add(new OrdersList(
                                                oh.getOrderID(),
                                                oh.getOutlet(),
                                                oh.getOutletID(),
                                                oh.getOrderDate(),
                                                oh.getPickUpTime(),
                                                oh.getDeliveryTime(),
                                                oh.getPaymentType(),
                                                oh.getRideName(),
                                                oh.getOrderTotal(),
                                                oh.getOrderTotalWithoutDeliveryCost(),
                                                oh.getSubTotal(),
                                                oh.getDeliveryCost(),
                                                oh.getOrderQty(),
                                                oh.getDispatchType(),
                                                oh.getPromoCode(),
                                                oh.getPromoTitle(),
                                                oh.getOrderNote(),
                                                oh.getLastStatus(),
                                                oh.getPromoDiscountValue(),
                                                oh.getMenuItems()
                                        ));


                                    } else {

                                        orderHistoryItemsCurrent.add(new OrdersList(
                                                oh.getOrderID(),
                                                oh.getOutlet(),
                                                oh.getOutletID(),
                                                oh.getOrderDate(),
                                                oh.getPickUpTime(),
                                                oh.getDeliveryTime(),
                                                oh.getPaymentType(),
                                                oh.getRideName(),
                                                oh.getOrderTotal(),
                                                oh.getOrderTotalWithoutDeliveryCost(),
                                                oh.getSubTotal(),
                                                oh.getDeliveryCost(),
                                                oh.getOrderQty(),
                                                oh.getDispatchType(),
                                                oh.getPromoCode(),
                                                oh.getPromoTitle(),
                                                oh.getOrderNote(),
                                                oh.getLastStatus(),
                                                oh.getPromoDiscountValue(),
                                                oh.getMenuItems()
                                        ));

                                    }
                                }
                                onOrderHistoryLoadFinishedListener.getOrderHistoryCurrent(orderHistoryItemsCurrent);
                                onOrderHistoryLoadFinishedListener.getOrderHistoryPAst(orderHistoryItemsPast);

                            } else {
                                if (orderHistory.getError().getErrCode().equals("RNF")) {
                                    onOrderHistoryLoadFinishedListener.getOrderHistoryEmpty();
                                } else {
                                    onOrderHistoryLoadFinishedListener.getOrderHistoryFail(orderHistory.getError().getErrDescription());
                                }

                            }


                        }
                    });
        } catch (Exception ex) {
            onOrderHistoryLoadFinishedListener.getOrderHistoryFail("Communication error, Please try again");
        }


    }

    @Override
    public void getOrderHistoryDetails( ArrayList<OrderMenus> orderMenus, final OnOrderHistoryDetailsFinishedListener onOrderHistoryDetailsFinishedListener) {
        onOrderHistoryDetailsFinishedListener.orderHistoryDetails(orderMenus);
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

    @Override
    public void getOutlet(final int outletID, final OnGetOutletFinishedListener onGetOutletFinishedListener) {


        try {
            apiService.getOutlet(outletID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<OutletItems>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(OutletItems respond) {
                            outletItems = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onGetOutletFinishedListener.getOutletDetailsFail("Communication error, Please try again", outletID);

                        }

                        @Override
                        public void onComplete() {
                            onGetOutletFinishedListener.getOutletDetails(outletItems);
                        }
                    });

        } catch (Exception ex) {
            onGetOutletFinishedListener.getOutletDetailsFail("Communication error, Please try again", outletID);
        }


    }

}
