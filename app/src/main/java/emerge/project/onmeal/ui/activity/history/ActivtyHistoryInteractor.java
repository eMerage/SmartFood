package emerge.project.onmeal.ui.activity.history;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.utils.entittes.OrderHistoryItems;
import emerge.project.onmeal.utils.entittes.OrderHistoryMenu;
import emerge.project.onmeal.utils.entittes.OutletItems;
import emerge.project.onmeal.utils.entittes.v2.Orders.OrderMenus;
import emerge.project.onmeal.utils.entittes.v2.Orders.OrdersList;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ActivtyHistoryInteractor {

    interface OnOrderHistoryLoadFinishedListener {
        void getOrderHistoryEmpty();
        void getOrderHistoryCurrent( ArrayList<OrdersList> orderHistoryItemsArrayList);
        void getOrderHistoryPAst( ArrayList<OrdersList> orderHistoryItemsArrayList);
        void getOrderHistoryFail(String msg);
    }
    void getOrderHistory(OnOrderHistoryLoadFinishedListener  onOrderHistoryLoadFinishedListener);



    interface OnOrderHistoryDetailsFinishedListener {
        void orderHistoryDetails( ArrayList<OrderMenus> orderMenus);
    }
    void getOrderHistoryDetails( ArrayList<OrderMenus> orderMenus, OnOrderHistoryDetailsFinishedListener  onOrderHistoryDetailsFinishedListener);


    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context, OnsignOutinishedListener onsignOutinishedListener);


    interface OnGetOutletFinishedListener {
        void getOutletDetails(OutletItems outletItems);
        void getOutletDetailsFail(String msg,int outletID);
    }
    void getOutlet(int outletID,OnGetOutletFinishedListener  onGetOutletFinishedListener);





}
