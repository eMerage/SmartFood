package emerge.project.onmeal.ui.activity.cart;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.utils.entittes.MenuItemsError;
import emerge.project.onmeal.utils.entittes.OrderConfirmDetails;
import emerge.project.onmeal.utils.entittes.TimeSlots;
import lk.payhere.androidsdk.model.InitRequest;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface CartView {

    void cartItems(ArrayList<CartHeader> cartHeaderArrayList);


    void getPromoCodeValidationSuccessful(String code, Double discount, Double subtotal, String image, Double deliveryCharges,String service,String serviceValue,Double total);

    void getPromoCodeValidationFail(String promoCode, String msg);


    void getDeliveryTimeSlotSuccessful(ArrayList<TimeSlots> timeSlotsItems);

    void getDeliveryTimeSlotFail(String msg);


    void timeSlots(int slotId);


    void pickupTime(String pickupTime);

    void pickupTimeFail(String msg);


    void deliveryAddress(String delveryAddress);


    void cartIsEmpty();

    void paymentTypeEmpty();

    void pickupTimeEmpty();

    void orderProsessPickupTimeFail(String msg);

    void deliveryTimeSlotEmpty();

    void orderProsessDeliveryTimeSlotFail(String msg);

    void orderProsessFail(String msg);

    void orderProsessSuccess(OrderConfirmDetails orderConfirmDetails);

    void menuItemsErrorList(ArrayList<MenuItemsError> menuItemsError);


    void getOrderCode(String orderCode);


    void removeFaildMenusSuccess();


    void dataSetToPaymentGateway(InitRequest req, String orderID);


    void orderPaymentUpdate();


    void getSubCartItemsEmpty();

    void getSubCartItemsSuccessful(ArrayList<CartDetail> cartDetails);


}
