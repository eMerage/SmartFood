package emerge.project.onmeal.ui.activity.orderddetails;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.ui.activity.profile.ProfileContactView;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractor;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractorImpil;
import emerge.project.onmeal.ui.activity.profile.ProfilePresenter;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class OrderDetailsPresenterImpli implements OrderDetailsPresenter,OrderDetailsInteractor.OnCartItemsFinishedListener{


    private OrderDetailsView orderDetailsView;
    OrderDetailsInteractor orderDetailsInteractor;


    public OrderDetailsPresenterImpli(OrderDetailsView orderdetailsview) {
        this.orderDetailsView = orderdetailsview;
        this.orderDetailsInteractor = new OrderDetailsInteractorImpil();

    }





    @Override
    public void getCartItems() {
        orderDetailsInteractor.getCartItems(this);
    }

    @Override
    public void removeOrederData() {
        orderDetailsInteractor.removeOrederData();
    }

    @Override
    public void cartItems(ArrayList<CartHeader> cartHeaderArrayList) {
        orderDetailsView.cartItems(cartHeaderArrayList);
    }
}
