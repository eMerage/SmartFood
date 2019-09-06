package emerge.project.onmeal.ui.activity.history;


import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.OrderHistoryItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ActivtyHistoryPresenter {

    void getOrderHistory();


    void getOrderHistoryDetails(String orderID,int level);


}
