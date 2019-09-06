package emerge.project.onmeal.ui.activity.orderddetails;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.cart.ActivityCart;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.adaptor.OrederdItemsAdapter;
import emerge.project.onmeal.utils.entittes.OrderConfirmDetails;
import io.realm.Realm;

public class OrderDetails extends Activity implements OrderDetailsView{

    @BindView(R.id.proprogressview)
    MKLoader proprogressview;


    @BindView(R.id.textview_outletname)
    TextView textviewOutletname;

    @BindView(R.id.textview_outletcity)
    TextView textviewOutletcity;

    @BindView(R.id.RecyclerView_items)
    RecyclerView RecyclerViewItems;




    @BindView(R.id.text_deliverychaege)
    TextView textDeliverychaege;


    @BindView(R.id.text_deliverychaege_cents)
    TextView textDeliverychaegeCents;


    @BindView(R.id.text_subtotal)
    TextView textSubtotal;


    @BindView(R.id.text_subtotal_cents)
    TextView textSubtotalCents;

    @BindView(R.id.textview_payment)
    TextView textviewPayment;

    @BindView(R.id.textview_address)
    TextView textviewAddress;



    @BindView(R.id.textview_time)
    TextView textviewTime;

    @BindView(R.id.textview_time_header)
    TextView textviewTimeHeader;


    OrderConfirmDetails orderConfirmDetails;
    OrderDetailsPresenter orderDetailsPresenter;
    OrederdItemsAdapter orederdItemsAdapter;

    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);


        orderConfirmDetails = (OrderConfirmDetails) getIntent().getSerializableExtra("ORDERCONFIRMDETAILS");
        orderDetailsPresenter = new OrderDetailsPresenterImpli(this);
        setAddedItemRecycal();





        orderDetailsPresenter.getCartItems();


        textviewOutletname.setText(orderConfirmDetails.getOutlet());
        textviewOutletcity.setText(orderConfirmDetails.getOutletCity());


        String sPrice = String.valueOf(orderConfirmDetails.getOrderTotal());
        String[] priseArray = sPrice.split("\\.");
        textSubtotal.setText(priseArray[0]);
        textSubtotalCents.setText("." + priseArray[1]);


        String sPriceDelivery = String.valueOf(orderConfirmDetails.getDeliveryCost());
        String[] priseArrayDelivery = sPriceDelivery.split("\\.");
        textDeliverychaege.setText(priseArrayDelivery[0]);
        textDeliverychaegeCents.setText("." + priseArrayDelivery[1]);


        if(orderConfirmDetails.getPaymentTypeCode().equals("CC")){
            textviewPayment.setText("CARD XXXX-XXXX-XXXX-XXXX");
        }else {
            textviewPayment.setText("CASH LKR");
        }

        if(orderConfirmDetails.getDispatchType().equals("D")){

            textviewTimeHeader.setText("Delivery Time");
            textviewTime.setText(orderConfirmDetails.getDeliveryTimeSlot());

            realm = Realm.getDefaultInstance();
            Address address = realm.where(Address.class).findFirst();
            textviewAddress.setText(address.getAddress());
        }else {
            textviewTimeHeader.setText("Pick Time");
            textviewTime.setText(orderConfirmDetails.getPickUpTime());

            textviewAddress.setText("Pick Up");
        }


    }

    @OnClick(R.id.img_btn_gohome)
    public void onClickBackToHome(View view) {

        orderDetailsPresenter.removeOrederData();
        Intent intentSingup = new Intent(OrderDetails.this, ActivityHome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);

    }


    @OnClick(R.id.relativelayout_menu)
    public void onClickBackMenu(View view) {

        orderDetailsPresenter.removeOrederData();
        Intent intentSingup = new Intent(OrderDetails.this, ActivityHome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);

    }


    @Override
    public void cartItems(ArrayList<CartHeader> cartHeaderArrayList) {
        orederdItemsAdapter = new OrederdItemsAdapter(this,cartHeaderArrayList);
        RecyclerViewItems.setAdapter(orederdItemsAdapter);

    }


    @Override
    public void onBackPressed() {
        orderDetailsPresenter.removeOrederData();
        Intent intentSingup = new Intent(OrderDetails.this, ActivityHome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);
    }


    private void setAddedItemRecycal() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewItems.setLayoutManager(layoutManager);
        RecyclerViewItems.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewItems.setNestedScrollingEnabled(false);

    }

}
