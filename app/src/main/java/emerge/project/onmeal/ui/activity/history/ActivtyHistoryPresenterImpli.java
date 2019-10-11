package emerge.project.onmeal.ui.activity.history;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.ui.activity.profile.ProfileContactView;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractor;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractorImpil;
import emerge.project.onmeal.ui.activity.profile.ProfilePresenter;
import emerge.project.onmeal.utils.entittes.OrderHistoryItems;
import emerge.project.onmeal.utils.entittes.OrderHistoryMenu;
import emerge.project.onmeal.utils.entittes.OutletItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class ActivtyHistoryPresenterImpli implements ActivtyHistoryPresenter,
        ActivtyHistoryInteractor.OnOrderHistoryLoadFinishedListener,
        ActivtyHistoryInteractor.OnOrderHistoryDetailsFinishedListener,
        ActivtyHistoryInteractor.OnsignOutinishedListener{


    private ActivtyHistorytView activtyHistorytView;
    ActivtyHistoryInteractor activtyHistoryInteractor;


    public ActivtyHistoryPresenterImpli(ActivtyHistorytView activtyHistorytView) {
        this.activtyHistorytView = activtyHistorytView;
        this.activtyHistoryInteractor = new ActivtyHistoryInteractorImpil();

    }




    @Override
    public void signOut(Context context) {
        activtyHistoryInteractor.signOut(context,this);
    }


    @Override
    public void signOutSuccess() {
        activtyHistorytView.signOutSuccess();
    }

    @Override
    public void getOrderHistory() {
        activtyHistoryInteractor.getOrderHistory(this);
    }



    @Override
    public void getOrderHistoryEmpty() {
        activtyHistorytView.getOrderHistoryEmpty();
    }

    @Override
    public void getOrderHistoryCurrent(ArrayList<OrderHistoryItems> orderHistoryItemsArrayList) {
        activtyHistorytView.getOrderHistoryCurrent(orderHistoryItemsArrayList);
    }

    @Override
    public void getOrderHistoryPAst(ArrayList<OrderHistoryItems> orderHistoryItemsArrayList) {
        activtyHistorytView.getOrderHistoryPAst(orderHistoryItemsArrayList);
    }

    @Override
    public void getOrderHistoryFail(String msg) {
        activtyHistorytView.getOrderHistoryFail(msg);
    }




    @Override
    public void getOrderHistoryDetails(String orderID,int level) {
        activtyHistoryInteractor.getOrderHistoryDetails(orderID,level,this);
    }

    @Override
    public void getOrderHistoryDetailsStart() {
        activtyHistorytView.getOrderHistoryDetailsStart();
    }

    @Override
    public void getOrderHistoryDetails(ArrayList<OrderHistoryMenu> cartHeader, int level, OutletItems outlet) {
        activtyHistorytView.getOrderHistoryDetails(cartHeader,level,outlet);
    }

    @Override
    public void getOrderHistoryDetailsFail(String msg,String orderID,int level) {
        activtyHistorytView.getOrderHistoryDetailsFail(msg,orderID,level);
    }
}
