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

public class CartPresenterImpli implements CartPresenter,
        CartInteractor.OnCartItemsFinishedListener,
        CartInteractor.OnPromoCodeValidationFinishedListener,
        CartInteractor.OngetDeliveryTimeSlotFinishedListener,
        CartInteractor.OnSelectedTimeSlotFinishedListener,
        CartInteractor.OnValidatePickupTimeFinishedListener,
        CartInteractor.OnDeliveryAddressFinishedListener,
        CartInteractor.OnOrderProsessFinishedListener,
        CartInteractor.OnGenarateOrderCodeFinishedListener,
CartInteractor.OnRemoveFaildMenusFinishedListener,
        CartInteractor.OnSetDataToPaymentGatewayFinishedListener,
        CartInteractor.OnOrderPaymentUpdateFinishedListener,CartInteractor.OnSubCartItemLoadFinishedListener{


    private CartView cartView;
    CartInteractor cartInteractor;


    public CartPresenterImpli(CartView cartview) {
        this.cartView = cartview;
        this.cartInteractor = new CartInteractorImpil();

    }


    @Override
    public void getCartItems() {
        cartInteractor.getCartItems(this);
    }

    @Override
    public void cartItems(ArrayList<CartHeader> cartHeaderArrayList) {
        cartView.cartItems(cartHeaderArrayList);
    }


    @Override
    public void getPromoCodeValidation(Context context, String promoCode,String orderCode) {
        cartInteractor.getPromoCodeValidation(context, promoCode,orderCode ,this);
    }


    @Override
    public void getPromoCodeValidationSuccessful(String promoTitle,String code, Double discount, Double subtotal, String image, Double deliveryCharges,String service,String serviceValue,Double total) {
        cartView.getPromoCodeValidationSuccessful( promoTitle,code, discount, subtotal, image, deliveryCharges,service, serviceValue,total);
    }

    @Override
    public void getPromoCodeValidationFail(String promoCode,String orderCode ,String msg) {
        cartView.getPromoCodeValidationFail(promoCode,orderCode ,msg);
    }


    @Override
    public void getDeliveryTimeSlot() {
        cartInteractor.getDeliveryTimeSlot(this);
    }


    @Override
    public void getDeliveryTimeSlotSuccessful(ArrayList<TimeSlots> timeSlotsItems) {
        cartView.getDeliveryTimeSlotSuccessful(timeSlotsItems);
    }

    @Override
    public void getDeliveryTimeSlotFail(String msg) {
        cartView.getDeliveryTimeSlotFail(msg);
    }


    @Override
    public void selectedTimeSlot(int slotId) {
        cartInteractor.selectedTimeSlot(slotId, this);
    }


    @Override
    public void timeSlots(int slotId) {
        cartView.timeSlots(slotId);
    }


    @Override
    public void validatePickupTime(String hourtime, String minutetime) {
        cartInteractor.validatePickupTime(hourtime, minutetime, this);
    }


    @Override
    public void pickupTime(String pickupTime) {
        cartView.pickupTime(pickupTime);
    }

    @Override
    public void pickupTimeFail(String msg) {
        cartView.pickupTimeFail(msg);
    }


    @Override
    public void getDeliveryAddress(Context context) {
        cartInteractor.getDeliveryAddress(context, this);
    }


    @Override
    public void deliveryAddress(String delveryAddress) {
        cartView.deliveryAddress(delveryAddress);
    }


    @Override
    public void orderProsess(String orderNote, String paymentType, String orderCode, Double deliveryChargers, Double totalAmount, String Promocode, String pickupTime, int timeslotId, Context context) {
        cartInteractor.orderProsess(orderNote, paymentType, orderCode, deliveryChargers, totalAmount, Promocode, pickupTime, timeslotId, context, this);
    }


    @Override
    public void cartIsEmpty() {
        cartView.cartIsEmpty();
    }

    @Override
    public void paymentTypeEmpty() {
        cartView.paymentTypeEmpty();
    }

    @Override
    public void pickupTimeEmpty() {
        cartView.pickupTimeEmpty();
    }

    @Override
    public void orderProsessPickupTimeFail(String msg) {
        cartView.orderProsessPickupTimeFail(msg);
    }

    @Override
    public void deliveryTimeSlotEmpty() {
        cartView.deliveryTimeSlotEmpty();
    }

    @Override
    public void orderProsessDeliveryTimeSlotFail(String msg) {
        cartView.orderProsessDeliveryTimeSlotFail(msg);
    }

    @Override
    public void orderProsessFail(String msg) {
        cartView.orderProsessFail(msg);
    }

    @Override
    public void orderProsessSuccess(OrderConfirmDetails orderConfirmDetails) {
        cartView.orderProsessSuccess(orderConfirmDetails);
    }

    @Override
    public void menuItemsErrorList(ArrayList<MenuItemsError> menuItemsError) {
        cartView.menuItemsErrorList(menuItemsError);
    }

    @Override
    public void dineinTimeEmpty() {
        cartView.dineinTimeEmpty();
    }


    @Override
    public void genarateOrderCode() {
        cartInteractor.genarateOrderCode(this);
    }


    @Override
    public void getOrderCode(String orderCode) {
        cartView.getOrderCode(orderCode);
    }


    @Override
    public void removeOrederData() {
        cartInteractor.removeOrederData();
    }




    @Override
    public void removeFaildMenus(ArrayList<MenuItemsError> menuItemsError) {
        cartInteractor.removeFaildMenus(menuItemsError,this);
    }


    @Override
    public void removeFaildMenusSuccess() {
        cartView.removeFaildMenusSuccess();
    }






    @Override
    public void setDataToPaymentGateway(double ammount, String orderID, String pyemtnRefNumber, String note) {
        cartInteractor.setDataToPaymentGateway(ammount,orderID,pyemtnRefNumber,note,this);
    }




    @Override
    public void dataSetToPaymentGateway(InitRequest req, String orderID) {
        cartView.dataSetToPaymentGateway(req,orderID);
    }




    @Override
    public void orderPaymentUpdate(String orderID, long paymentNo) {
        cartInteractor.orderPaymentUpdate(orderID,paymentNo,this);
    }



    @Override
    public void orderPaymentUpdate() {
        cartView.orderPaymentUpdate();
    }







    @Override
    public void getSubCartItems(Long cartID) {
        cartInteractor.getSubCartItems(cartID,this);
    }

    @Override
    public void getSubCartItemsEmpty() {
        cartView.getSubCartItemsEmpty();
    }

    @Override
    public void getSubCartItemsSuccessful(ArrayList<CartDetail> cartDetails) {
        cartView.getSubCartItemsSuccessful(cartDetails);
    }
}
