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
import emerge.project.onmeal.utils.entittes.v2.Orders.OrderMenus;
import emerge.project.onmeal.utils.entittes.v2.Orders.OrdersList;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class ActivtyHistoryPresenterImpli implements ActivtyHistoryPresenter,
        ActivtyHistoryInteractor.OnOrderHistoryLoadFinishedListener,
        ActivtyHistoryInteractor.OnOrderHistoryDetailsFinishedListener,
        ActivtyHistoryInteractor.OnsignOutinishedListener,
        ActivtyHistoryInteractor.OnGetOutletFinishedListener{


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
    public void getOrderHistoryCurrent(ArrayList<OrdersList> orderHistoryItemsArrayList) {
        activtyHistorytView.getOrderHistoryCurrent(orderHistoryItemsArrayList);
    }

    @Override
    public void getOrderHistoryPAst(ArrayList<OrdersList> orderHistoryItemsArrayList) {
        activtyHistorytView.getOrderHistoryPAst(orderHistoryItemsArrayList);
    }

    @Override
    public void getOrderHistoryFail(String msg) {
        activtyHistorytView.getOrderHistoryFail(msg);
    }




    @Override
    public void getOutlet(int outletID) {
        activtyHistoryInteractor.getOutlet(outletID,this);
    }

    @Override
    public void getOutletDetails(OutletItems outletItems) {
        activtyHistorytView.getOutletDetails(outletItems);
    }

    @Override
    public void getOutletDetailsFail(String msg, int outletID) {
        activtyHistorytView.getOutletDetailsFail(msg,outletID);
    }



    @Override
    public void getOrderHistoryDetails( ArrayList<OrderMenus> orderMenus) {
        activtyHistoryInteractor.getOrderHistoryDetails(orderMenus,this);
    }


    @Override
    public void orderHistoryDetails( ArrayList<OrderMenus> orderMenus) {
        activtyHistorytView.orderHistoryDetails(orderMenus);
    }
}
