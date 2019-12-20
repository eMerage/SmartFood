package emerge.project.onmeal.ui.activity.cart;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.MenuItemsError;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface CartPresenter {
    void getCartItems();

    void getPromoCodeValidation(Context context, String promoCode,String orderCode);

    void getDeliveryTimeSlot();

    void selectedTimeSlot(int slotId);

    void validatePickupTime(String hourtime,String minutetime);

    void getDeliveryAddress(Context context);


    void orderProsess(String orderNote,String paymentType,
                      String orderCode,Double deliveryChargers,
                      Double totalAmount,String Promocode,String pickupTime,int timeslotId ,
                      Context context);


    void genarateOrderCode();


    void removeOrederData();


    void removeFaildMenus(ArrayList<MenuItemsError> menuItemsError);


    void setDataToPaymentGateway(double ammount,String orderID,String pyemtnRefNumber,String note);


    void orderPaymentUpdate(String orderID,long paymentNo);




    void getSubCartItems(Long cartID);


}
