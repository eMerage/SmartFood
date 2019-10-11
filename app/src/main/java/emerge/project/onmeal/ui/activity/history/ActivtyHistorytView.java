package emerge.project.onmeal.ui.activity.history;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.utils.entittes.OrderHistoryItems;
import emerge.project.onmeal.utils.entittes.OrderHistoryMenu;
import emerge.project.onmeal.utils.entittes.OutletItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ActivtyHistorytView {

    void getOrderHistoryEmpty();
    void getOrderHistoryCurrent( ArrayList<OrderHistoryItems> orderHistoryItemsArrayList);
    void getOrderHistoryPAst( ArrayList<OrderHistoryItems> orderHistoryItemsArrayList);
    void getOrderHistoryFail(String msg);


    void getOrderHistoryDetailsStart();
    void getOrderHistoryDetails(ArrayList<OrderHistoryMenu> orderHistoryMenu, int level, OutletItems outlet);
    void getOrderHistoryDetailsFail(String msg,String orderID,int level);


    void signOutSuccess();
}
