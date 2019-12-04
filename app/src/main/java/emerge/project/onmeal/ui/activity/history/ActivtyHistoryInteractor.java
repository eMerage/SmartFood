package emerge.project.onmeal.ui.activity.history;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.utils.entittes.OrderHistoryItems;
import emerge.project.onmeal.utils.entittes.OrderHistoryMenu;
import emerge.project.onmeal.utils.entittes.OutletItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ActivtyHistoryInteractor {

    interface OnOrderHistoryLoadFinishedListener {
        void getOrderHistoryEmpty();
        void getOrderHistoryCurrent( ArrayList<OrderHistoryItems> orderHistoryItemsArrayList);
        void getOrderHistoryPAst( ArrayList<OrderHistoryItems> orderHistoryItemsArrayList);
        void getOrderHistoryFail(String msg);
    }
    void getOrderHistory(OnOrderHistoryLoadFinishedListener  onOrderHistoryLoadFinishedListener);



    interface OnOrderHistoryDetailsFinishedListener {

        void getOrderHistoryDetailsStart();
        void getOrderHistoryDetails(ArrayList<OrderHistoryMenu> OrderHistoryMenu, OutletItems outlet);
        void getOrderHistoryDetailsFail(String msg,String orderID);
    }
    void getOrderHistoryDetails(String orderID,OnOrderHistoryDetailsFinishedListener  onOrderHistoryDetailsFinishedListener);


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
