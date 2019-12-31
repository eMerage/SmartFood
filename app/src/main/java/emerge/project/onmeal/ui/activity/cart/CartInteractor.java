package emerge.project.onmeal.ui.activity.cart;


import android.content.Context;

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

public interface CartInteractor {


    interface OnCartItemsFinishedListener {
        void cartItems(ArrayList<CartHeader> cartHeaderArrayList);
    }
    void getCartItems(OnCartItemsFinishedListener onCartItemsFinishedListener);



    interface OnPromoCodeValidationFinishedListener {
        void getPromoCodeValidationSuccessful(String promoTitle,String code,Double discount,Double Subtotal,String image,Double deliveryCharges,String service,String serviceValue,Double total);
        void getPromoCodeValidationFail(String promoCode,String orderCode,String msg);
    }
    void getPromoCodeValidation(Context context, String promoCode,String orderCode, OnPromoCodeValidationFinishedListener  onPromoCodeValidationFinishedListener);


    interface OngetDeliveryTimeSlotFinishedListener {
        void getDeliveryTimeSlotSuccessful(ArrayList<TimeSlots> timeSlotsItems);
        void getDeliveryTimeSlotFail(String msg);
    }
    void getDeliveryTimeSlot(OngetDeliveryTimeSlotFinishedListener ongetDeliveryTimeSlotFinishedListener);


    interface OnSelectedTimeSlotFinishedListener {
        void timeSlots(int slotId);
    }
    void selectedTimeSlot(int slotId,OnSelectedTimeSlotFinishedListener onSelectedTimeSlotFinishedListener);


    interface OnValidatePickupTimeFinishedListener {
        void pickupTime(String pickupTime);
        void pickupTimeFail(String msg);
    }
    void validatePickupTime(String hourtime,String minutetime,OnValidatePickupTimeFinishedListener onValidatePickupTimeFinishedListener);



    interface OnDeliveryAddressFinishedListener {
        void deliveryAddress(String delveryAddress);
    }
    void getDeliveryAddress(Context context,OnDeliveryAddressFinishedListener onDeliveryAddressFinishedListener);



    interface OnOrderProsessFinishedListener {

        void cartIsEmpty();
        void paymentTypeEmpty();
        void pickupTimeEmpty();
        void orderProsessPickupTimeFail(String msg);
        void deliveryTimeSlotEmpty();
        void orderProsessDeliveryTimeSlotFail(String msg);
        void orderProsessFail(String msg);
        void orderProsessSuccess(OrderConfirmDetails orderConfirmDetails);

        void menuItemsErrorList(ArrayList<MenuItemsError> menuItemsError);
        void dineinTimeEmpty();



    }
    void orderProsess(String orderNote,String paymentType,
                      String orderCode,Double deliveryChargers,
                      Double totalAmount,String Promocode,String pickupTime,int timeslotId ,
                      Context context,OnOrderProsessFinishedListener onOrderProsessFinishedListener);


    interface OnGenarateOrderCodeFinishedListener {
        void getOrderCode(String orderCode);
    }
    void genarateOrderCode(OnGenarateOrderCodeFinishedListener onGenarateOrderCodeFinishedListener);


    void removeOrederData();


    interface OnRemoveFaildMenusFinishedListener {
        void removeFaildMenusSuccess();
    }
    void removeFaildMenus(ArrayList<MenuItemsError> menuItemsError,OnRemoveFaildMenusFinishedListener onRemoveFaildMenusFinishedListener);



    interface OnSetDataToPaymentGatewayFinishedListener {
        void dataSetToPaymentGateway(InitRequest req, String orderID);
    }
    void setDataToPaymentGateway(double ammount,String orderID,String pyemtnRefNumber,String note,OnSetDataToPaymentGatewayFinishedListener onSetDataToPaymentGatewayFinishedListener);


    interface OnOrderPaymentUpdateFinishedListener {
        void orderPaymentUpdate();
    }
    void orderPaymentUpdate(String orderID,long paymentNo,OnOrderPaymentUpdateFinishedListener onOrderPaymentUpdateFinishedListener);




    interface OnSubCartItemLoadFinishedListener {
        void getSubCartItemsEmpty();
        void getSubCartItemsSuccessful(ArrayList<CartDetail> cartDetails);
    }
    void getSubCartItems(Long cartID,OnSubCartItemLoadFinishedListener  onSubCartItemLoadFinishedListener);




}
