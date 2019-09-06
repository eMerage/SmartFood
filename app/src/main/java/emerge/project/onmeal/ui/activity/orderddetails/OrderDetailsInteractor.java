package emerge.project.onmeal.ui.activity.orderddetails;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.ui.activity.cart.CartInteractor;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface OrderDetailsInteractor {



    interface OnCartItemsFinishedListener {
        void cartItems(ArrayList<CartHeader> cartHeaderArrayList);
    }
    void getCartItems(OnCartItemsFinishedListener onCartItemsFinishedListener);

    void removeOrederData();


}
