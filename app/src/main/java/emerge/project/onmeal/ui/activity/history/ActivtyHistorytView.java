package emerge.project.onmeal.ui.activity.history;


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

public interface ActivtyHistorytView {

    void getOrderHistoryEmpty();
    void getOrderHistoryCurrent( ArrayList<OrdersList> orderHistoryItemsArrayList);
    void getOrderHistoryPAst( ArrayList<OrdersList> orderHistoryItemsArrayList);
    void getOrderHistoryFail(String msg);



    void orderHistoryDetails( ArrayList<OrderMenus> orderMenus);



    void signOutSuccess();


        void getOutletDetails(OutletItems outletItems);
        void getOutletDetailsFail(String msg,int outletID);



}
