package emerge.project.onmeal.ui.activity.history;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.ui.adaptor.CartSubItemsAdapter;
import emerge.project.onmeal.ui.adaptor.HistoryCurrentAdapter;
import emerge.project.onmeal.ui.adaptor.HistoryMenuAdapter;
import emerge.project.onmeal.ui.adaptor.HistoryPastAdapter;
import emerge.project.onmeal.ui.adaptor.HomeOutletImagesAdaptor;
import emerge.project.onmeal.utils.entittes.OrderHistoryItems;
import emerge.project.onmeal.utils.entittes.OrderHistoryMenu;
import emerge.project.onmeal.utils.entittes.OutletItems;

public class ActivityHistory extends Activity implements ActivtyHistorytView, OnMapReadyCallback {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.recyclerview_current)
    RecyclerView recyclerviewCurrent;


    @BindView(R.id.recyclerview_past)
    RecyclerView recyclerviewPast;


    @BindView(R.id.relativelayout_title_bar)
    RelativeLayout relativelayoutTitleBar;

    @BindView(R.id.textview_msg)
    TextView textviewMsg;

    // navigation menu
    @BindView(R.id.text_navigation_getfood)
    TextView textViewNavigationGetFood;

    @BindView(R.id.text_navigation_yourorders)
    TextView textViewNavigationYourOrders;


    @BindView(R.id.text_navigation_favourites)
    TextView textViewNavigationFavourites;

    @BindView(R.id.text_navigation_profile)
    TextView textViewNavigationProfile;


    @BindView(R.id.text_navigation_settings)
    TextView textViewNavigationSettings;

    @BindView(R.id.relativelayout_logout)
    RelativeLayout relativelayoutNavigationLogout;
    // navigation menu


   @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    HistoryCurrentAdapter historyCurrentAdapter;
    HistoryPastAdapter historyPastAdapter;


    ActivtyHistoryPresenter activtyHistoryPresenter;

    HistoryMenuAdapter historyMenuAdapter;



    double latitude = 0.0,longitude = 0.0;
    GoogleMap mMap ;

    MapView mapView;
    TextView textNoLocation;
    ImageView ImageView_googlemap;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);


        activtyHistoryPresenter = new ActivtyHistoryPresenterImpli(this);
        setRecycalCurrentView();
        setRecycalPastView();
        setNavigationMenuItems();


        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            bloackUserInteraction();
            activtyHistoryPresenter.getOrderHistory();
        } else {
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

        pullToRefresh();
    }


    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }


    @OnClick(R.id.text_navigation_getfood)
    public void onClickNavigationGetFood(View view) {
        Intent intentSingup = new Intent(this, ActivityHome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }


    @OnClick(R.id.text_navigation_yourorders)
    public void onClickNavigationYourOrders(View view) {

    }


    @OnClick(R.id.text_navigation_favourites)
    public void onClickNavigationFavourites(View view) {

        Intent intentSingup = new Intent(this, ActivityFavourites.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();


    }


    @OnClick(R.id.text_navigation_profile)
    public void onClickNavigationProfile(View view) {
        Intent intentSingup = new Intent(this, ActivityProfile.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }

    @OnClick(R.id.text_navigation_settings)
    public void onClickNavigationSettings(View view) {
        Intent intentSingup = new Intent(this, ActivitySettings.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }


    @Override
    public void getOrderHistoryEmpty() {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        swipeRefreshLayout.setRefreshing(false);
        relativelayoutTitleBar.setBackgroundColor(getResources().getColor(R.color.colorRedDark));
        textviewMsg.setText("No Order for you");

    }

    @Override
    public void getOrderHistoryCurrent(ArrayList<OrderHistoryItems> orderHistoryItemsArrayList) {



        historyCurrentAdapter = new HistoryCurrentAdapter(this, orderHistoryItemsArrayList,this);
        recyclerviewCurrent.setAdapter(historyCurrentAdapter);

    }

    @Override
    public void getOrderHistoryPAst(ArrayList<OrderHistoryItems> orderHistoryItemsArrayList) {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        swipeRefreshLayout.setRefreshing(false);

        relativelayoutTitleBar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        textviewMsg.setText("Your Past Orders");


        historyPastAdapter = new HistoryPastAdapter(this, orderHistoryItemsArrayList,this);
        recyclerviewPast.setAdapter(historyPastAdapter);


    }

    @Override
    public void getOrderHistoryFail(String msg) {
        proprogressview.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        unBloackUserInteraction();
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
                                activtyHistoryPresenter.getOrderHistory();
                            } else {
                                Toast.makeText(ActivityHistory.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

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
    public void getOrderHistoryDetailsStart() {
        proprogressview.setVisibility(View.VISIBLE);
        bloackUserInteraction();

    }

    @Override
    public void getOrderHistoryDetails(ArrayList<OrderHistoryMenu> cartHeader,int level,OutletItems out) {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        swipeRefreshLayout.setRefreshing(false);


        if(level==0){
            Dialog dialogBox = new Dialog(this);
            dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialogBox.setContentView(R.layout.dialog_cart_subitems);
            dialogBox.setCancelable(true);


            RecyclerView  recyclerViewOrderSubitems = (RecyclerView) dialogBox.findViewById(R.id.recyclerview_subcart_items);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerViewOrderSubitems.setLayoutManager(layoutManager);
            recyclerViewOrderSubitems.setItemAnimator(new DefaultItemAnimator());
            recyclerViewOrderSubitems.setNestedScrollingEnabled(true);


            historyMenuAdapter = new HistoryMenuAdapter(this,cartHeader);
            recyclerViewOrderSubitems.setAdapter(historyMenuAdapter);


            dialogBox.show();
        }else {
            showOutletMoreDetails(out);
        }




    }




    private void showOutletMoreDetails(final OutletItems outlet){

        final Dialog dialogOutletMoreDetails = new Dialog(this);
        dialogOutletMoreDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOutletMoreDetails.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogOutletMoreDetails.setContentView(R.layout.dialog_outlet_more_details);
        dialogOutletMoreDetails.setCancelable(true);



        RecyclerView  recyclerViewOutletImages= dialogOutletMoreDetails.findViewById(R.id.recyclerView_outletimages);
        TextView  text3= dialogOutletMoreDetails.findViewById(R.id.text3);
        ImageView_googlemap= dialogOutletMoreDetails.findViewById(R.id.ImageView_googlemap);

        textNoLocation =  dialogOutletMoreDetails.findViewById(R.id.textview_nolocations);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOutletImages.setLayoutManager(layoutManager);
        recyclerViewOutletImages.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOutletImages.setNestedScrollingEnabled(true);


        mapView= dialogOutletMoreDetails.findViewById(R.id.mapView_outlet);


        latitude = outlet.getOutletLatitude();
        longitude = outlet.getOutletLongitude();

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }


        if(outlet.getOutletImages().isEmpty()){
            text3.setVisibility(View.VISIBLE);
            recyclerViewOutletImages.setVisibility(View.INVISIBLE);

        }else {
            text3.setVisibility(View.GONE);
            recyclerViewOutletImages.setVisibility(View.VISIBLE);

            HomeOutletImagesAdaptor homeOutletImagesAdaptor = new HomeOutletImagesAdaptor(this,outlet.getOutletImages());
            recyclerViewOutletImages.setAdapter(homeOutletImagesAdaptor);
        }



        TextView textView_outletdetails_name= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_name);
        textView_outletdetails_name.setText(outlet.getOutletName());

        TextView textView_outletdetails_city= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_city);
        textView_outletdetails_city.setText(outlet.getOutletCity());

        TextView textView_outletdetails_ownersname= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_ownersname);
        textView_outletdetails_ownersname.setText(outlet.getOutletOwnersName());

        TextView textView_outletdetails_contactnumber= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_contactnumber);
        textView_outletdetails_contactnumber.setText(outlet.getPhoneNUmber());


        TextView textView_outletdetails_email= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_email);
        textView_outletdetails_email.setText(outlet.geteMail());




        ImageView_googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(latitude==0.0){

                }else {
                    String latit = String.valueOf(latitude);
                    String longi = String.valueOf(longitude);


                    String label = outlet.getOutletName();
                    String uriBegin = "geo:" + latit + "," + longi;
                    String query = latit + "," + longi + "(" + label + ")";
                    String encodedQuery = Uri.encode(query);
                    String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";

                    Uri uri = Uri.parse(uriString);

                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    try{
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    }catch (NullPointerException e){
                    }


                }



            }
        });





        dialogOutletMoreDetails.show();
    }

    private void pullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                    proprogressview.setVisibility(View.VISIBLE);
                    bloackUserInteraction();
                    activtyHistoryPresenter.getOrderHistory();
                } else {
                    Toast.makeText(ActivityHistory.this, "yNo Internet Access, Please try again", Toast.LENGTH_LONG).show();

                }
            }
        });

    }


    @Override
    public void getOrderHistoryDetailsFail(String msg, final String orderID, final int level) {


        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        swipeRefreshLayout.setRefreshing(false);

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
                                activtyHistoryPresenter.getOrderHistoryDetails(orderID,level);
                            } else {
                                Toast.makeText(ActivityHistory.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

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


    private void setRecycalCurrentView() {



        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewCurrent.setLayoutManager(layoutManager);
        recyclerviewCurrent.setItemAnimator(new DefaultItemAnimator());
        recyclerviewCurrent.setNestedScrollingEnabled(false);

    }

    private void setRecycalPastView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewPast.setLayoutManager(layoutManager);
        recyclerviewPast.setItemAnimator(new DefaultItemAnimator());
        recyclerviewPast.setNestedScrollingEnabled(false);

    }

    private void setNavigationMenuItems() {
        textViewNavigationGetFood.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationFavourites.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationYourOrders.setTextColor(getResources().getColor(R.color.colorAccent));
        textViewNavigationProfile.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationSettings.setTextColor(getResources().getColor(R.color.navigationTextColor));

    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);



        mMap = googleMap;



        LatLng locationMap = new LatLng(latitude, longitude);
        if (latitude == 0.0) {
            textNoLocation.setVisibility(View.VISIBLE);
            mapView.setVisibility(View.INVISIBLE);
            ImageView_googlemap.setVisibility(View.GONE);


        } else {
            textNoLocation.setVisibility(View.GONE);
            mapView.setVisibility(View.VISIBLE);
            ImageView_googlemap.setVisibility(View.VISIBLE);

            mMap.addMarker(new MarkerOptions().position(locationMap));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMap, 17));

        }
    }


    @OnClick(R.id.relativelayout_logout)
    public void onSingOut(View view) {
        activtyHistoryPresenter.signOut(this);

    }


    @Override
    public void signOutSuccess() {

        Intent intent = new Intent(this, ActivityLogin.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intent, bndlanimation);

    }

}
