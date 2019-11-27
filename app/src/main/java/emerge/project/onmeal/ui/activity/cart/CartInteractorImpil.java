package emerge.project.onmeal.ui.activity.cart;


import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.luseen.logger.Logger;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.MenuItemsError;
import emerge.project.onmeal.utils.entittes.OrderConfirmDetails;
import emerge.project.onmeal.utils.entittes.OrderErrorReturn;
import emerge.project.onmeal.utils.entittes.TimeSlots;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class CartInteractorImpil implements CartInteractor {

    Realm realm = Realm.getDefaultInstance();
    EncryptedPreferences encryptedPreferences;
    private static final String DISPATCH_TYPE = "dispatch_type";
    int callingRefUpdateCount = 0;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    JsonObject promoCodeValidationJsonObject;
    List<TimeSlots> deliveryTimeSlots;

    Boolean validatePickupTime;

    JsonObject orderProsess;

    List<OrderErrorReturn> orderSaveFail;
    int orderPaymentUpdate;

    Context appContext;

    @Override
    public void getCartItems(OnCartItemsFinishedListener onCartItemsFinishedListener) {

        realm = Realm.getDefaultInstance();
        ArrayList<CartHeader> cartHeaderArrayList = new ArrayList<CartHeader>();
        for (CartHeader cartHeader : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            cartHeaderArrayList.add(new CartHeader(cartHeader.getCartHeaderId(), cartHeader.getOutletMenuTitleID(),
                    cartHeader.getOutletID(), cartHeader.getOutlet(), cartHeader.getName(), cartHeader.getImageUrl(), cartHeader.getSize(), cartHeader.getQuantity(), cartHeader.getPriceTotal(), cartHeader.isActive()));
        }
        onCartItemsFinishedListener.cartItems(cartHeaderArrayList);

    }

    @Override
    public void getPromoCodeValidation(final Context context, final String promoCode, final OnPromoCodeValidationFinishedListener onPromoCodeValidationFinishedListener) {

        appContext = context;

        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();

        String dispatch = encryptedPreferences.getString(DISPATCH_TYPE, "");


        String addressId="0";
        Double totalPrice = 0.0;
        int outlet = 0;

        User user = realm.where(User.class).findFirst();
        Address address = realm.where(Address.class).findFirst();

        String dispatchPatchType="P";


      /*  if (dispatch.equals("Delivery")) {
            addressId = address.getAddressId();
        } else {
            addressId = "0";
        }*/

        if (dispatch.equals("Delivery")) {
            dispatchPatchType = "D";
            addressId = address.getAddressId();

        } else if(dispatch.equals("Pickup")){
            dispatchPatchType = "P";
            addressId = "0";
        }else {
            dispatchPatchType = "T";
            addressId = "0";
        }



        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            totalPrice = totalPrice + ns.getPriceTotal();
            outlet = ns.getOutletID();
        }

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String currentDate = sdfDate.format(new Date(System.currentTimeMillis()));

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("OrderDate", currentDate);
        jsonObject.addProperty("UserID", Integer.parseInt(user.getUserId()));
        jsonObject.addProperty("AddressID", addressId);
        jsonObject.addProperty("OutletID", outlet);
        jsonObject.addProperty("PromoCode", promoCode);
        jsonObject.addProperty("SubTotal", totalPrice);
        jsonObject.addProperty("DispatchType", dispatchPatchType);

        final Double finalTotalPrice = totalPrice;





        try {
            apiService.validatePromoCode(jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject respond) {
                            promoCodeValidationJsonObject = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                           // onPromoCodeValidationFinishedListener.getPromoCodeValidationFail(promoCode, "Communication error, Please try again"));
                            onPromoCodeValidationFinishedListener.getPromoCodeValidationFail(promoCode,"Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (promoCodeValidationJsonObject != null) {
                                JSONObject userVerifiedResponse = null;
                                try {
                                    userVerifiedResponse = new JSONObject(promoCodeValidationJsonObject.toString());
                                    Double deliveryCharges = userVerifiedResponse.getDouble("deliveryCost");
                                    double subAmount;
                                    double fullAmount;
                                    String serviceCharge;
                                    double serviceChargeValue = 0.0;

                                    if (userVerifiedResponse.getDouble("subTotal") == 0) {
                                        fullAmount = finalTotalPrice;
                                        Toast.makeText(context, "Promo Code not valid", Toast.LENGTH_SHORT).show();
                                    } else {
                                        fullAmount = userVerifiedResponse.getDouble("subTotal");
                                    }

                                    if (userVerifiedResponse.getString("serviceChargePercentage").equals("0") || userVerifiedResponse.getString("serviceChargePercentage").equals("null")) {
                                        serviceCharge = "0.00";
                                    } else {
                                        serviceCharge = userVerifiedResponse.getString("serviceChargePercentage");
                                        serviceChargeValue = userVerifiedResponse.getDouble("serviceCharge");
                                    }

                                    if (userVerifiedResponse.getDouble("promoTotal") == 0) {
                                        subAmount = fullAmount;
                                    } else {
                                        subAmount = userVerifiedResponse.getDouble("promoTotal");
                                    }

                                    double discount = 0.0;

                                    discount = userVerifiedResponse.getDouble("discountedAmount");

                                    String promo;
                                    if (userVerifiedResponse.getString("promoTitle").equals("") || userVerifiedResponse.getString("promoTitle") == null || userVerifiedResponse.getString("promoTitle").equals("null")) {
                                        promo = "No Promo";
                                    } else {
                                        promo = userVerifiedResponse.getString("promoTitle");
                                    }
                                    onPromoCodeValidationFinishedListener.getPromoCodeValidationSuccessful(promo, discount, subAmount, userVerifiedResponse.getString("imageUrl"), deliveryCharges, serviceCharge, String.valueOf(serviceChargeValue), fullAmount);

                                } catch (JSONException e) {
                                    Logger.e(e.toString());
                                    onPromoCodeValidationFinishedListener.getPromoCodeValidationFail(promoCode, "Communication error, Please try again");
                                } catch (NullPointerException exNull) {
                                    onPromoCodeValidationFinishedListener.getPromoCodeValidationFail(promoCode, "Communication error, Please try again");
                                }

                            } else {
                                onPromoCodeValidationFinishedListener.getPromoCodeValidationFail(promoCode, "Communication error, Please try again");
                            }
                        }
                    });

        } catch (Exception ex) {
            onPromoCodeValidationFinishedListener.getPromoCodeValidationFail(promoCode, "Communication error, Please try again");
        }
    }

    @Override
    public void getDeliveryTimeSlot(final OngetDeliveryTimeSlotFinishedListener ongetDeliveryTimeSlotFinishedListener) {

        final ArrayList<TimeSlots> timeSlotsArrayList = new ArrayList<TimeSlots>();
        try {
            apiService.getTimeSlot()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<TimeSlots>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<TimeSlots> respond) {
                            deliveryTimeSlots = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            ongetDeliveryTimeSlotFinishedListener.getDeliveryTimeSlotFail("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (deliveryTimeSlots != null) {
                                try {
                                    if (deliveryTimeSlots.isEmpty()) {
                                        ongetDeliveryTimeSlotFinishedListener.getDeliveryTimeSlotFail("No Time Slots available");
                                        Logger.e("No Time Slots available");
                                    } else {
                                        for (int i = 0; i < deliveryTimeSlots.size(); i++) {
                                            timeSlotsArrayList.add(new TimeSlots(deliveryTimeSlots.get(i).getTimeSlotId(), deliveryTimeSlots.get(i).getDeliveryTimeSlot(), deliveryTimeSlots.get(i).getDeliveryTimeFrom(), deliveryTimeSlots.get(i).getDeliveryTimeTo(), false));
                                        }
                                        ongetDeliveryTimeSlotFinishedListener.getDeliveryTimeSlotSuccessful(timeSlotsArrayList);
                                    }
                                } catch (NullPointerException exNull) {
                                    ongetDeliveryTimeSlotFinishedListener.getDeliveryTimeSlotFail("Communication error, Please try again");
                                }
                            } else {
                                ongetDeliveryTimeSlotFinishedListener.getDeliveryTimeSlotFail("Communication error, Please try again");
                            }
                        }
                    });
        } catch (Exception ex) {
            ongetDeliveryTimeSlotFinishedListener.getDeliveryTimeSlotFail("Communication error, Please try again");
        }
    }

    @Override
    public void selectedTimeSlot(int slotId, OnSelectedTimeSlotFinishedListener onSelectedTimeSlotFinishedListener) {
        onSelectedTimeSlotFinishedListener.timeSlots(slotId);
    }

    @Override
    public void validatePickupTime(final String hourtime, final String minutetime, final OnValidatePickupTimeFinishedListener onValidatePickupTimeFinishedListener) {

        int outletID = 0;

        for (CartHeader ns : realm.where(CartHeader.class).findAll()) {
            outletID = ns.getOutletID();
        }

        try {
            String time = hourtime + ":" + minutetime;
            apiService.verifyPickupTime(time, outletID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Boolean respond) {
                            validatePickupTime = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onValidatePickupTimeFinishedListener.pickupTimeFail("Pickup time validation fail,Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (validatePickupTime != null) {
                                try {
                                    if (validatePickupTime) {
                                        onValidatePickupTimeFinishedListener.pickupTime(hourtime + ":" + minutetime);
                                    } else {
                                        onValidatePickupTimeFinishedListener.pickupTimeFail("Invalid Time");
                                    }
                                } catch (NullPointerException exNull) {
                                    onValidatePickupTimeFinishedListener.pickupTimeFail("Pickup time validation fail,Please try again");
                                }

                            } else {
                                onValidatePickupTimeFinishedListener.pickupTimeFail("Pickup time validation fail,Please try again");
                            }
                        }
                    });
        } catch (Exception ex) {
            onValidatePickupTimeFinishedListener.pickupTimeFail("Pickup time validation fail,Please try again");
        }
    }

    @Override
    public void getDeliveryAddress(Context context, OnDeliveryAddressFinishedListener onDeliveryAddressFinishedListener) {


        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();

        String dispatch = encryptedPreferences.getString(DISPATCH_TYPE, "");
        String deliveryAddres;

        if (dispatch.equals("Delivery")) {
            Address address = realm.where(Address.class).findFirst();
            deliveryAddres = address.getAddress();
        } else {
            deliveryAddres = "";
        }

        onDeliveryAddressFinishedListener.deliveryAddress(deliveryAddres);

    }

    @Override
    public void orderProsess(final String orderNote, final String paymentType, final String orderCode, final Double deliveryChargers, final Double totalAmount, final String Promocode, final String pickupTime, int timeslotId, Context context, final OnOrderProsessFinishedListener onOrderProsessFinishedListener) {


        int outletId = 0;
        final String addressId;
        String dispatchType;
        double subtotal = 0.0;
        int totalQty = 0;
        int cartCount = 0;

        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
        dispatchType = encryptedPreferences.getString(DISPATCH_TYPE, "");


        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            cartCount++;
            subtotal = subtotal + ns.getPriceTotal();
            outletId = ns.getOutletID();
            totalQty = totalQty + ns.getQuantity();
        }


        if (cartCount == 0) {
            onOrderProsessFinishedListener.cartIsEmpty();
        } else if (paymentType == null || paymentType.equals("") || paymentType.isEmpty()) {
            onOrderProsessFinishedListener.paymentTypeEmpty();
        } else if (subtotal == 0.0 || subtotal <= 0.0) {
            onOrderProsessFinishedListener.orderProsessFail("Error in Subtotal,Please try again");
        } else if (deliveryChargers == null || deliveryChargers < 0.0) {
            onOrderProsessFinishedListener.orderProsessFail("Error in Delivery Chargers,Please try again");
        } else if (totalAmount == null || totalAmount <= 0.0) {
            onOrderProsessFinishedListener.orderProsessFail("Error in Total Amount,Please try again");
        } else if (totalQty <= 0) {
            onOrderProsessFinishedListener.orderProsessFail("Error in Total Quantity,Please try again");
        } else if ((dispatchType.equals("Pickup")) && (pickupTime.equals("") || pickupTime == null)) {
            onOrderProsessFinishedListener.pickupTimeEmpty();
        } else if ((dispatchType.equals("Delivery")) && (timeslotId == 0)) {
            onOrderProsessFinishedListener.deliveryTimeSlotEmpty();
        } else if ((dispatchType.equals("Dinein")) && (pickupTime.equals("") || pickupTime == null)) {
            onOrderProsessFinishedListener.dineinTimeEmpty();
        }

        else {

            User user = realm.where(User.class).findFirst();
            final String dispatchTypeForSever;


            if (dispatchType.equals("Delivery")) {
                Address address = realm.where(Address.class).findFirst();
                addressId = address.getAddressId();
                dispatchTypeForSever = "D";
            } else if (dispatchType.equals("Dinein")) {
                addressId = "0";
                dispatchTypeForSever = "T";
            } else {
                addressId = "0";
                dispatchTypeForSever = "P";
            }




            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
            final String strDate = mdformat.format(calendar.getTime());

            final JsonObject jsonObject = new JsonObject();


            jsonObject.addProperty("OrderCode", orderCode);
            jsonObject.addProperty("OrderDate", strDate);
            jsonObject.addProperty("UserID", Integer.parseInt(user.getUserId()));
            jsonObject.addProperty("AddressID", Integer.parseInt(addressId));
            jsonObject.addProperty("OutletID", outletId);
            jsonObject.addProperty("PromoCode", Promocode);
            jsonObject.addProperty("DeliveryCost", deliveryChargers);
            jsonObject.addProperty("SubTotal", subtotal);
            jsonObject.addProperty("OrderTotal", totalAmount);
            jsonObject.addProperty("OrderNote", orderNote);
            jsonObject.addProperty("PaymentTypeCode", paymentType);
            jsonObject.addProperty("PaymentRefNo", "");
            jsonObject.addProperty("OrderQty", totalQty);
            jsonObject.addProperty("DispatchType", dispatchTypeForSever);
            if (dispatchTypeForSever.equals("P")) {
                jsonObject.addProperty("DeliveryTimeSlotID", 0);
            } else {
                jsonObject.addProperty("DeliveryTimeSlotID", timeslotId);
            }
            jsonObject.addProperty("PickUpTime", pickupTime);


            JsonArray cartJsonArr = new JsonArray();

            for (CartHeader cartHeader : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {

                JsonObject ob = new JsonObject();
                ob.addProperty("OrderCode", orderCode);
                ob.addProperty("CartID", String.valueOf(cartHeader.getCartHeaderId()));
                ob.addProperty("OutletMenuTitleID", cartHeader.getOutletMenuTitleID());
                ob.addProperty("MenuQty", cartHeader.getQuantity());
                ob.addProperty("MenuPrice", cartHeader.getPriceTotal());
                ob.addProperty("MenuSizeCode", cartHeader.getSize());


                JsonArray cartDetailsJsonArr = new JsonArray();

                for (CartDetail CartDetail : realm.where(CartDetail.class)
                        .equalTo("cartHeaderId", cartHeader.getCartHeaderId())
                        .findAll()) {

                    JsonObject obdetails = new JsonObject();
                    obdetails.addProperty("CartID", String.valueOf(CartDetail.getCartHeaderId()));
                    obdetails.addProperty("OutletFoodItemID", String.valueOf(CartDetail.getFoodId()));
                    obdetails.addProperty("FoodQty", CartDetail.getQuantity());
                    obdetails.addProperty("FoodPrice", CartDetail.getFoodItemPrice());
                    obdetails.addProperty("FoodSizeCode", CartDetail.getFoodItemSizeCode());

                    cartDetailsJsonArr.add(obdetails);

                }
                ob.add("OrderMenuDetails", cartDetailsJsonArr);
                cartJsonArr.add(ob);

            }
            jsonObject.add("OrderMenus", cartJsonArr);


            System.out.println("aaa :" + jsonObject);
            try {
                final int finalOutletId = outletId;
                apiService.orderProsess(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .retry(5)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject respond) {
                                orderProsess = respond;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (orderProsess != null) {
                                    JSONObject userVerifiedResponse = null;
                                    try {
                                        userVerifiedResponse = new JSONObject(orderProsess.toString());
                                        if (userVerifiedResponse.getString("statusCode").equals("Fail")) {
                                            orderSaveFail(userVerifiedResponse.getString("orderCode"), onOrderProsessFinishedListener);
                                        } else if (userVerifiedResponse.getString("statusCode").equals("Failed from exception")) {
                                            orderSaveFail(userVerifiedResponse.getString("orderCode"), onOrderProsessFinishedListener);
                                        } else {
                                            String timeSlot = userVerifiedResponse.getJSONObject("deliveryTime").getString("timeSlot");
                                            String outletName = userVerifiedResponse.getJSONObject("orderOutlet").getString("name");
                                            String outletCity = userVerifiedResponse.getJSONObject("orderOutlet").getString("city");
                                            String outletPromoTitle = userVerifiedResponse.getString("promoTitle");
                                            String subTotal = userVerifiedResponse.getString("subTotal");
                                            OrderConfirmDetails orderConfirmDetails = new OrderConfirmDetails(userVerifiedResponse.getString("orderID"),
                                                    orderCode, strDate, Integer.parseInt(addressId), Promocode, outletPromoTitle, finalOutletId, outletName, outletCity, deliveryChargers,
                                                    totalAmount, paymentType, dispatchTypeForSever, pickupTime, timeSlot);

                                            onOrderProsessFinishedListener.orderProsessSuccess(orderConfirmDetails);
                                        }
                                    } catch (JSONException e) {
                                        Logger.e(e.toString());
                                        onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                                    } catch (NullPointerException exNull) {
                                        onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                                    }

                                } else {
                                    onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                                }
                            }
                        });

            } catch (Exception ex) {
                onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
            }
        }


    }


    public void orderSaveFail(String orderCode, final OnOrderProsessFinishedListener onOrderProsessFinishedListener) {
        final ArrayList<MenuItemsError> menuItemsError = new ArrayList<MenuItemsError>();
        try {
            apiService.getBusinessRuleViolations(orderCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<OrderErrorReturn>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<OrderErrorReturn> respond) {
                            orderSaveFail = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (orderSaveFail != null) {
                                try {
                                    if (orderSaveFail.isEmpty()) {
                                        onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");

                                    } else {
                                        for (int i = 0; i < orderSaveFail.size(); i++) {
                                            if (orderSaveFail.get(i).getErrorCode().equals("ERQNA")) {
                                                menuItemsError.add(new MenuItemsError(orderSaveFail.get(i).getMenuItemsError().getMenuId(), orderSaveFail.get(i).getMenuItemsError().getOutletMenuTitleID(),
                                                        orderSaveFail.get(i).getMenuItemsError().getMenuTitle(), orderSaveFail.get(i).getMenuItemsError().getImageUrl()));
                                            } else if (orderSaveFail.get(i).getErrorCode().equals("ERDLT")) {
                                                onOrderProsessFinishedListener.orderProsessFail("Delivery time slot error, Please try again");
                                            } else {
                                                onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                                            }

                                        }
                                        onOrderProsessFinishedListener.menuItemsErrorList(menuItemsError);

                                    }
                                } catch (NullPointerException exNull) {
                                    onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                                }

                            } else {
                                onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
                            }
                        }
                    });
        } catch (Exception ex) {
            onOrderProsessFinishedListener.orderProsessFail("Communication error, Please try again");
        }
    }

    @Override
    public void genarateOrderCode(OnGenarateOrderCodeFinishedListener onGenarateOrderCodeFinishedListener) {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        Calendar c = Calendar.getInstance();
        String numberFromTime = String.valueOf(c.get(Calendar.YEAR)).substring(1) + String.valueOf(c.get(Calendar.MONTH)) + String.valueOf(c.get(Calendar.DATE)) + String.valueOf(c.get(Calendar.HOUR)) + String.valueOf(c.get(Calendar.MINUTE)) + String.valueOf(c.get(Calendar.SECOND)) + String.valueOf(c.get(Calendar.MILLISECOND));
        if (numberFromTime.length() == 16) {
        } else {
            if (numberFromTime.length() > 16) {
                numberFromTime = numberFromTime.substring(0, 16);
            } else {
                int len = 16 - numberFromTime.length();
                for (int i = 0; i < len; i++) {
                    numberFromTime = numberFromTime + "0";
                }
            }
        }
        String OrderCode = user.getUserId() + numberFromTime;
        onGenarateOrderCodeFinishedListener.getOrderCode(OrderCode);
    }

    @Override
    public void removeOrederData() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                RealmResults<CartDetail> resultsCartDetail = bgRealm.where(CartDetail.class).findAll();
                resultsCartDetail.deleteAllFromRealm();

                RealmResults<CartHeader> resultsCartHeader = bgRealm.where(CartHeader.class).findAll();
                resultsCartHeader.deleteAllFromRealm();

                RealmResults<MenuSubItems> resultsMenuSubItems = bgRealm.where(MenuSubItems.class).findAll();
                resultsMenuSubItems.deleteAllFromRealm();

            }
        });


    }

    @Override
    public void removeFaildMenus(ArrayList<MenuItemsError> menuItemsError, OnRemoveFaildMenusFinishedListener onRemoveFaildMenusFinishedListener) {

        realm = Realm.getDefaultInstance();
        for (final MenuItemsError menuitemserror1 : menuItemsError) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<CartDetail> resultsCartDetail = realm.where(CartDetail.class).equalTo("outletMenuTitleID", menuitemserror1.getOutletMenuTitleID()).findAll();
                    resultsCartDetail.deleteAllFromRealm();
                    RealmResults<CartHeader> resultsCartHeader = realm.where(CartHeader.class).equalTo("outletMenuTitleID", menuitemserror1.getOutletMenuTitleID()).findAll();
                    resultsCartHeader.deleteAllFromRealm();


                }
            });
            onRemoveFaildMenusFinishedListener.removeFaildMenusSuccess();


        }
    }

    @Override
    public void setDataToPaymentGateway(double ammount, String orderID, String pyemtnRefNumber, String note, OnSetDataToPaymentGatewayFinishedListener onSetDataToPaymentGatewayFinishedListener) {


        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        Address address = realm.where(Address.class).findFirst();

        int totalQty = 1;

        for (CartHeader ns : realm.where(CartHeader.class).equalTo("isActive", true).findAll()) {
            totalQty = totalQty + ns.getQuantity();
        }



        InitRequest req = new InitRequest();
        req.setMerchantId("213803"); // Your Merchant ID
        req.setMerchantSecret("8m0Wm1pO6KX4uVyxW40rli4ua5fYqpw0f4p6nffEZpwb"); // Your Merchant secret
        req.setAmount(ammount); // Amount which the customer should pay
        req.setCurrency("LKR"); // Currency
        req.setOrderId(orderID); // Unique ID for your payment transaction
        req.setItemsDescription(pyemtnRefNumber);  // Item title or Order/Invoice number
        req.setCustom1("custom id" + user.getUserId());
        req.setCustom2("custom note" + note);
        req.getCustomer().setFirstName(user.getUserName());
        req.getCustomer().setLastName(user.getUserName());
        req.getCustomer().setEmail(user.getUserEmail());
        req.getCustomer().setPhone(user.getUserPhoneNumber());

        if (address == null) {

            req.getCustomer().getAddress().setAddress("Pick Up");
            req.getCustomer().getAddress().setCity("Pick Up");
            req.getCustomer().getAddress().setCountry("Sri Lanka");
            req.getCustomer().getDeliveryAddress().setAddress("Pick Up");
            req.getCustomer().getDeliveryAddress().setCity("Pick Up");
            req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");


        } else {
            req.getCustomer().getAddress().setAddress(address.getAddress());
            req.getCustomer().getAddress().setCity(address.getAddress());
            req.getCustomer().getAddress().setCountry("Sri Lanka");
            req.getCustomer().getDeliveryAddress().setAddress(address.getAddress());
            req.getCustomer().getDeliveryAddress().setCity(address.getAddress());
            req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");


        }
        req.getItems().add(new Item(null, "Smart Food", totalQty,ammount));
       // req.getItems().add(new Item(null, "Door bell wireless", 1));

        onSetDataToPaymentGatewayFinishedListener.dataSetToPaymentGateway(req, orderID);
    }

    @Override
    public void orderPaymentUpdate(String orderID, long paymentNo, final OnOrderPaymentUpdateFinishedListener onOrderPaymentUpdateFinishedListener) {

        Call<Integer> call = apiService.updatePaymentReference(Integer.parseInt(orderID), String.valueOf(paymentNo));
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                callingRefUpdateCount++;
                if (response.isSuccessful()) {
                    try {
                        Integer verifiedResponse = response.body();
                        if (verifiedResponse == 1) {
                            onOrderPaymentUpdateFinishedListener.orderPaymentUpdate();
                        } else {
                            if (callingRefUpdateCount > 5) {
                            } else {
                                call.clone().enqueue(this);
                            }
                        }
                    } catch (NullPointerException exNull) {
                        if (callingRefUpdateCount > 5) {
                        } else {
                            call.clone().enqueue(this);
                        }
                    }

                } else {
                    if (callingRefUpdateCount > 5) {
                    } else {
                        call.clone().enqueue(this);
                    }
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Logger.e(t.toString());
                if (callingRefUpdateCount > 5) {
                } else {
                    call.clone().enqueue(this);
                }

            }
        });

    }

    @Override
    public void getSubCartItems(Long cartID, OnSubCartItemLoadFinishedListener onSubCartItemLoadFinishedListener) {

        realm = Realm.getDefaultInstance();
        ArrayList<CartDetail> cartDetailArrayList = new ArrayList<CartDetail>();

        for (CartDetail cartDetail : realm.where(CartDetail.class)
                .equalTo("cartHeaderId", cartID)
                .findAll()) {
            cartDetailArrayList.add(new CartDetail(cartDetail.getCartDetailId(), cartDetail.getCartHeaderId(), cartDetail.getFoodname(), cartDetail.getImageUrl(), cartDetail.getQuantity()));


            System.out.println("pppppp  :" + cartDetail.getFoodname());

        }

        if (cartDetailArrayList.isEmpty()) {
            onSubCartItemLoadFinishedListener.getSubCartItemsEmpty();
        } else {
            onSubCartItemLoadFinishedListener.getSubCartItemsSuccessful(cartDetailArrayList);
        }


    }


}


