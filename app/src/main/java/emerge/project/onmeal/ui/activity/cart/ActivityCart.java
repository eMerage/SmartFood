package emerge.project.onmeal.ui.activity.cart;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.datetimepicker.time.RadialPickerLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.google.gson.JsonArray;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.menu.ActivityMenu;
import emerge.project.onmeal.ui.activity.orderddetails.OrderDetails;
import emerge.project.onmeal.ui.activity.personlaize.ActivityPersonlaize;
import emerge.project.onmeal.ui.adaptor.CartAdapter;
import emerge.project.onmeal.ui.adaptor.CartSubItemsAdapter;
import emerge.project.onmeal.ui.adaptor.FailOrderMenusAdaptor;
import emerge.project.onmeal.ui.adaptor.TimeSlotsAdapter;
import emerge.project.onmeal.utils.entittes.MenuItemsError;
import emerge.project.onmeal.utils.entittes.OrderConfirmDetails;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;
import emerge.project.onmeal.utils.entittes.TimeSlots;
import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

public class ActivityCart extends FragmentActivity implements OnMapReadyCallback, CartView, com.android.datetimepicker.time.TimePickerDialog.OnTimeSetListener {


    SupportMapFragment mapFragment;
    private final LatLng mDefaultLocation = new LatLng(6.890872, 79.878859);
    private GoogleMap mMap;
    Marker mapMarker;

    EncryptedPreferences encryptedPreferences;
    private static final String DISPATCH_TYPE = "dispatch_type";
    static final int PAYHERE_REQUEST = 100;


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.editText_promocode)
    EditText editTextPromocode;


    @BindView(R.id.edittext_note)
    EditText edittextNote;


    @BindView(R.id.imageView_payment_expain)
    ImageView imageViewPaymentExpain;


    @BindView(R.id.text_deliverychaege)
    TextView textDeliverychaege;

    @BindView(R.id.textView_selectedaddress)
    TextView textViewSelectedaddress;

    @BindView(R.id.relativeLayout_delivery)
    RelativeLayout layoutDeliverychaege;


    @BindView(R.id.text_deliverychaege_cents)
    TextView textDeliverychaegeCents;


    @BindView(R.id.text_subtotal)
    TextView textSubtotal;
    @BindView(R.id.text_subtotal_cents)
    TextView textSubtotalCents;


    @BindView(R.id.text_fulltotal)
    TextView textFulltotal;
    @BindView(R.id.text_fulltotal_cents)
    TextView textFulltotalCents;


    @BindView(R.id.text_servicechaege)
    TextView textServicechaege;
    @BindView(R.id.text_servicechaege_cents)
    TextView textServicechaegecents;


    @BindView(R.id.text_discountchaege)
    TextView textDiscountchaege;
    @BindView(R.id.text_discountchaege_cents)
    TextView textDiscountchaegeCents;


    @BindView(R.id.robotoRegular113)
    TextView textServicechaegeValue;


    @BindView(R.id.recyclerview_added_item)
    RecyclerView recyclerviewAddedItem;


    @BindView(R.id.relativelayout_peymenttype)
    RelativeLayout relativelayoutPeymenttype;


    @BindView(R.id.checkBox_ondelivery)
    CheckBox checkBoxPaymentOnDilivery;


    @BindView(R.id.checkBox_card)
    CheckBox checkBoxPaymentByCard;


    @BindView(R.id.relativelayout_peymenttypeone)
    RelativeLayout relativelayoutPeymentOndelivery;

    @BindView(R.id.relativelayout_peymenttypetwo)
    RelativeLayout relativelayoutPeymentCard;


    CartPresenter cartPresenter;
    CartAdapter cartAdapter;

    String dispatchType;

    boolean isPyementTypeVisibale = false;

    TimeSlotsAdapter timeSlotsAdapter;


    int selectedTimeSlots;
    private Calendar calendar;
    String selectedPickupTime = "";
    String paymentType = "";
    String orderCODE;
    String promoCode = "";


    String webOrderId;

    OrderConfirmDetails orderconfirmdetails;
    CartSubItemsAdapter cartSubItemsAdapter;


    SelectedMenuDetails selectedMenuDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);


        selectedMenuDetails = (SelectedMenuDetails) getIntent().getSerializableExtra("SELECTEDMENU");

        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();
        dispatchType = encryptedPreferences.getString(DISPATCH_TYPE, "");


        cartPresenter = new CartPresenterImpli(this);
        setAddedItemRecycal();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        cartPresenter.getCartItems();


        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            cartPresenter.genarateOrderCode();
            cartPresenter.getDeliveryAddress(this);

        } else {
            cartPresenter.genarateOrderCode();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("No Internet Access, Please try again ");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();

        }
        calendar = Calendar.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if ((dispatchType.equals("Dinein"))) {
            relativelayoutPeymentOndelivery.setVisibility(View.GONE);
            relativelayoutPeymentCard.setVisibility(View.VISIBLE);
        } else {
            relativelayoutPeymentOndelivery.setVisibility(View.VISIBLE);
            relativelayoutPeymentCard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mDefaultLocation.latitude, mDefaultLocation.longitude), 15));
        mapMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mDefaultLocation.latitude, mDefaultLocation.longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));
    }


    @OnClick(R.id.relativelayout_menu)
    public void onClickBackMenu(View view) {
        finish();

    }


    @OnClick(R.id.img_btn_time)
    public void onClickTime(View view) {

        if ((dispatchType.equals("Pickup")) || (dispatchType.equals("Dinein"))) {
            com.android.datetimepicker.time.TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");

        } else {
            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                bloackUserInteraction();
                proprogressview.setVisibility(View.VISIBLE);
                cartPresenter.getDeliveryTimeSlot();
            } else {
                Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

            }

        }


    }


    @OnClick(R.id.img_btn_confirm)
    public void onClickConfrim(View view) {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            bloackUserInteraction();
            if(!editTextPromocode.getText().toString().equals(promoCode)){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Promo Code");
                alertDialogBuilder.setMessage("Press Add button to claim your promotion");
                alertDialogBuilder.setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                unBloackUserInteraction();
                                promoCode = editTextPromocode.getText().toString();

                                return;
                            }
                        });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callOrder();
                        return;
                    }
                });
                alertDialogBuilder.show();

            }else {
                callOrder();

            }

        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

        }

    }


    @OnClick(R.id.checkBox_ondelivery)
    public void onClickPaymentOnDeliveryCheckBtn(View view) {
        checkBoxPaymentByCard.setChecked(false);
        checkBoxPaymentOnDilivery.setChecked(true);
        paymentType = "CH";
    }


    @OnClick(R.id.checkBox_card)
    public void onClickPaymentOnCardCheckBtn(View view) {
        checkBoxPaymentOnDilivery.setChecked(false);
        checkBoxPaymentByCard.setChecked(true);
        paymentType = "CC";
    }


    @OnClick(R.id.img_btn_addnote)
    public void onClickAddNote(View view) {
        edittextNote.setVisibility(View.VISIBLE);


    }

    @OnClick(R.id.relativeLayout_promo_add)
    public void onClickPromoAdd(View view) {

        if (editTextPromocode.getText().toString().isEmpty() || editTextPromocode.getText().toString().equals("")) {
            Toast.makeText(this, "Please add the code", Toast.LENGTH_SHORT).show();
        } else {
            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                promoCode = editTextPromocode.getText().toString();
                bloackUserInteraction();
                proprogressview.setVisibility(View.VISIBLE);
                cartPresenter.getPromoCodeValidation(this, promoCode,orderCODE);
            } else {
                Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

            }
        }


    }

    @OnClick(R.id.relativeLayout_payment_expain)
    public void onClickPaymentType(View view) {

        if (isPyementTypeVisibale) {
            Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_payment_expan);
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
            Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(), matrix, true);
            imageViewPaymentExpain.setImageBitmap(rotated);

            isPyementTypeVisibale = false;
            relativelayoutPeymenttype.setVisibility(View.GONE);
        } else {
            Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_payment_expan);
            Matrix matrix = new Matrix();
            matrix.postRotate(180);
            Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(), matrix, true);
            imageViewPaymentExpain.setImageBitmap(rotated);
            isPyementTypeVisibale = true;
            relativelayoutPeymenttype.setVisibility(View.VISIBLE);

        }


    }


    private void setAddedItemRecycal() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewAddedItem.setLayoutManager(layoutManager);
        recyclerviewAddedItem.setItemAnimator(new DefaultItemAnimator());
        recyclerviewAddedItem.setNestedScrollingEnabled(false);

    }


    public void getPriceAfterRemoveItem() {

        bloackUserInteraction();
        proprogressview.setVisibility(View.VISIBLE);

        cartPresenter.getPromoCodeValidation(this,promoCode ,orderCODE);
        cartPresenter.getCartItems();
    }


    @Override
    public void cartItems(ArrayList<CartHeader> cartHeaderArrayList) {

        cartAdapter = new CartAdapter(this, cartHeaderArrayList, this);
        recyclerviewAddedItem.setAdapter(cartAdapter);

    }


    @Override
    public void getPromoCodeValidationSuccessful(String promoTitle,String code, Double discount, Double subtotal, String image, Double deliveryCharges, String service, String serviceChage, Double total) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        if ((dispatchType.equals("Pickup")) || (dispatchType.equals("Dinein"))) {
            layoutDeliverychaege.setVisibility(View.GONE);
        } else {
            layoutDeliverychaege.setVisibility(View.VISIBLE);
        }


        String sPrice = String.valueOf(subtotal);
        String[] priseArray = sPrice.split("\\.");
        textSubtotal.setText(priseArray[0]);
        textSubtotalCents.setText("." + priseArray[1]);


        String sPriceDelivery = String.valueOf(deliveryCharges);
        String[] priseArrayDelivery = sPriceDelivery.split("\\.");

        textDeliverychaege.setText(priseArrayDelivery[0]);
        textDeliverychaegeCents.setText("." + priseArrayDelivery[1]);


        String sPriceFulltotal = String.valueOf(total);
        String[] priseArrayFulltotal = sPriceFulltotal.split("\\.");
        textFulltotal.setText(priseArrayFulltotal[0]);
        textFulltotalCents.setText("." + priseArrayFulltotal[1]);


        String[] priseArrayService = serviceChage.split("\\.");
        textServicechaege.setText(priseArrayService[0]);
        textServicechaegecents.setText("." + priseArrayService[1]);


        textServicechaegeValue.setText("Service Charges (" + service + " % )");


        String sPriceDiscount = String.valueOf(discount);
        String[] priseArrayDiscount = sPriceDiscount.split("\\.");
        textDiscountchaege.setText(priseArrayDiscount[0]);
        textDiscountchaegeCents.setText("." + priseArrayDiscount[1]);

        if (code.equals("")) {
            if(promoTitle.equals("")){

            }else {
                Toast.makeText(this, "Promo code successfully redeemed", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, code, Toast.LENGTH_LONG).show();
        }


        if (image.equals("null") || image.isEmpty() || image == null || image.equals("")) {

        } else {
            showPromoImage(image);
        }


    }

    @Override
    public void getPromoCodeValidationFail(final String promoCode, final String orderCode , String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        try {
            if (msg.equals("No Order in your cart")) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder.setMessage(msg + ",do you want add items");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        textSubtotal.setText("00");
                        textSubtotalCents.setText(".00");


                        textDeliverychaege.setText("00");
                        textDeliverychaegeCents.setText(".00");


                        textFulltotal.setText("00");
                        textFulltotalCents.setText(".00");

                        textServicechaege.setText("00");
                        textServicechaegecents.setText(".00");


                        textServicechaegeValue.setText("Service Charges (" + 00.0 + " % )");


                        textDiscountchaege.setText("00");
                        textDiscountchaegeCents.setText(".00");


                        return;
                    }
                });
                alertDialogBuilder.show();


            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder.setMessage(msg);
                alertDialogBuilder.setPositiveButton("Re-Try",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                    proprogressview.setVisibility(View.VISIBLE);
                                    bloackUserInteraction();
                                    cartPresenter.getPromoCodeValidation(ActivityCart.this,promoCode,orderCode);
                                } else {
                                    Toast.makeText(ActivityCart.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                alertDialogBuilder.show();
            }


        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    @Override
    public void getDeliveryTimeSlotSuccessful(ArrayList<TimeSlots> timeSlotsItems) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);


        final Dialog dialogTimeSlots = new Dialog(this);
        dialogTimeSlots.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTimeSlots.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogTimeSlots.setContentView(R.layout.dialog_time_slots);
        dialogTimeSlots.setCancelable(true);


        RecyclerView timeSlotList = dialogTimeSlots.findViewById(R.id.recyclerview_timeslots);
        ImageView btnTimeSlots = dialogTimeSlots.findViewById(R.id.img_btn_timeslots);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        timeSlotList.setLayoutManager(layoutManager);
        timeSlotList.setItemAnimator(new DefaultItemAnimator());
        timeSlotList.setNestedScrollingEnabled(true);


        timeSlotsAdapter = new TimeSlotsAdapter(this, timeSlotsItems, this);
        timeSlotList.setAdapter(timeSlotsAdapter);
        dialogTimeSlots.show();


        btnTimeSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogTimeSlots != null) {
                    dialogTimeSlots.dismiss();
                } else {

                }

            }
        });


    }

    @Override
    public void getDeliveryTimeSlotFail(String msg) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);


        try {


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                proprogressview.setVisibility(View.VISIBLE);
                                bloackUserInteraction();
                                cartPresenter.getDeliveryTimeSlot();
                            } else {
                                Toast.makeText(ActivityCart.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();

        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    @Override
    public void timeSlots(int slotId) {
        selectedTimeSlots = slotId;
    }

    @Override
    public void pickupTime(String pickupTime) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        selectedPickupTime = pickupTime;

    }

    @Override
    public void pickupTimeFail(String msg) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void deliveryAddress(String delveryAddress) {
        textViewSelectedaddress.setText(delveryAddress);
    }

    @Override
    public void cartIsEmpty() {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        Toast.makeText(this, "Your cat is empty", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void paymentTypeEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(this, "Please select the payment type", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void pickupTimeEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(this, "Please select the Pickup Time", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderProsessPickupTimeFail(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deliveryTimeSlotEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(this, "Please select the Delivery Time Slot", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderProsessDeliveryTimeSlotFail(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderProsessFail(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);


        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                callOrder();
                            } else {
                                Toast.makeText(ActivityCart.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();


        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void orderProsessSuccess(OrderConfirmDetails orderConfirmDetails) {



        webOrderId = orderConfirmDetails.getOrderID();
        proprogressview.setVisibility(View.GONE);

        System.out.println("aaaaaaaaaaaa sss "+webOrderId);
        orderconfirmdetails = orderConfirmDetails;

        if (orderConfirmDetails.getPaymentTypeCode().equals("CC")) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);

            String totalAmount = textSubtotal.getText().toString() + textSubtotalCents.getText().toString();
            cartPresenter.setDataToPaymentGateway(Double.parseDouble(totalAmount), webOrderId, orderCODE, edittextNote.getText().toString());

        } else {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            //ref for cash
            long cashRef=111111;
            cartPresenter.orderPaymentUpdate(webOrderId, cashRef);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO process response
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            String msg;
            if (response.isSuccess()) {
                msg = "Activity result:" + response.getData().toString();
            } else {
                msg = "Result:" + response.toString();
            }

            if (response.getData().getStatus() == 2) {

                bloackUserInteraction();
                proprogressview.setVisibility(View.VISIBLE);
                cartPresenter.orderPaymentUpdate(webOrderId, response.getData().getPaymentNo());

            } else {

                try {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Warning");
                    alertDialogBuilder.setMessage("Payment Fail Please try again");
                    alertDialogBuilder.setPositiveButton("Re-Try",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                        String totalAmount = textSubtotal.getText().toString() + textSubtotalCents.getText().toString();
                                        cartPresenter.setDataToPaymentGateway(Double.parseDouble(totalAmount), webOrderId, orderCODE, edittextNote.getText().toString());

                                    } else {
                                        Toast.makeText(ActivityCart.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                    alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    alertDialogBuilder.show();


                } catch (WindowManager.BadTokenException e) {
                    Toast.makeText(this, "Payment Fail Please try again", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public void menuItemsErrorList(final ArrayList<MenuItemsError> menuItemsError) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);


        final Dialog dialogFailsItems = new Dialog(this);
        dialogFailsItems.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogFailsItems.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogFailsItems.setContentView(R.layout.dialog_faild_menus);
        dialogFailsItems.setCancelable(false);


        RecyclerView failItemsList = dialogFailsItems.findViewById(R.id.recyclerview_timeslots);

        Button btnProsess = dialogFailsItems.findViewById(R.id.button2);
        Button btnAdd = dialogFailsItems.findViewById(R.id.button3);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        failItemsList.setLayoutManager(layoutManager);
        failItemsList.setItemAnimator(new DefaultItemAnimator());
        failItemsList.setNestedScrollingEnabled(true);


        FailOrderMenusAdaptor failOrderMenusAdaptor = new FailOrderMenusAdaptor(this, menuItemsError);
        failItemsList.setAdapter(failOrderMenusAdaptor);
        dialogFailsItems.show();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cartPresenter.removeOrederData();

                Intent intentSingup = new Intent(ActivityCart.this, ActivityHome.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
                finish();
                startActivity(intentSingup, bndlanimation);

            }
        });


        btnProsess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFailsItems.dismiss();
                cartPresenter.removeFaildMenus(menuItemsError);


            }
        });


    }

    @Override
    public void dineinTimeEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(this, "Please select the Dine-In Time", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getOrderCode(String orderCode) {
        orderCODE = orderCode;
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            if((orderCODE.equals("")) || (orderCODE == null)){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder.setMessage("Order can not be process,please try again");
                alertDialogBuilder.setPositiveButton("Try-again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                cartPresenter.genarateOrderCode();
                            }
                        });
                alertDialogBuilder.show();
            }else {
                cartPresenter.getPromoCodeValidation(this,promoCode,orderCODE);
            }

        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("No Internet Access, Please try again");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();

        }




    }


    @Override
    public void removeFaildMenusSuccess() {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            cartPresenter.getPromoCodeValidation(this, promoCode,orderCODE);
            cartPresenter.getCartItems();
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("No Internet Access, Please try again");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();

        }

    }

    @Override
    public void dataSetToPaymentGateway(InitRequest req, String orderID) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        Intent intent = new Intent(this, PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
        PHConfigs.setBaseUrl(PHConfigs.LIVE_URL);
        startActivityForResult(intent, PAYHERE_REQUEST);


    }

    @Override
    public void orderPaymentUpdate() {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        Toast.makeText(this, "Thank you!,Your order has been successfully processed", Toast.LENGTH_SHORT).show();

        Intent intentSingup = new Intent(ActivityCart.this, OrderDetails.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intentSingup.putExtra("ORDERCONFIRMDETAILS", orderconfirmdetails);
        finish();
        startActivity(intentSingup, bndlanimation);


    }


    @Override
    public void getSubCartItemsEmpty() {

    }

    @Override
    public void getSubCartItemsSuccessful(ArrayList<CartDetail> cartDetails) {


        Dialog dialogBox = new Dialog(this);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dialog_cart_subitems);
        dialogBox.setCancelable(true);


        RecyclerView recyclerViewOrderSubitems = (RecyclerView) dialogBox.findViewById(R.id.recyclerview_subcart_items);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewOrderSubitems.setLayoutManager(layoutManager);
        recyclerViewOrderSubitems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOrderSubitems.setNestedScrollingEnabled(true);


        cartSubItemsAdapter = new CartSubItemsAdapter(this, cartDetails);
        recyclerViewOrderSubitems.setAdapter(cartSubItemsAdapter);
        dialogBox.show();


    }


    private void showPromoImage(String image) {

        Dialog dialogBox = new Dialog(this);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dialog_promo_imageviver);
        dialogBox.setCancelable(true);


        ImageView fullImage = (ImageView) dialogBox.findViewById(R.id.imageView_fullimage);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_default);
        requestOptions.error(R.drawable.ic_image_default);


        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        };

        Glide.with(this)
                .asBitmap()
                .load(image)
                .apply(requestOptions)
                .listener(requestListener)
                .into(fullImage);

        dialogBox.show();
    }


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            bloackUserInteraction();

            cartPresenter.validatePickupTime(String.valueOf(hourOfDay), String.valueOf(minute));
        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }

    }


    private void callOrder() {




        String deliver = textDeliverychaege.getText().toString() + textDeliverychaegeCents.getText().toString();
        String totalAmount = textSubtotal.getText().toString() + textSubtotalCents.getText().toString();

        cartPresenter.orderProsess(edittextNote.getText().toString(), paymentType, orderCODE, Double.parseDouble(deliver), Double.parseDouble(totalAmount),    promoCode, selectedPickupTime, selectedTimeSlots, this);


    }

    @Override
    public void onBackPressed() {
        finish();

    }

}
