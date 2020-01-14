package emerge.project.onmeal.ui.activity.landing;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import com.tuyenmonkey.mkloader.MKLoader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import emerge.project.onmeal.OnMeal;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.landingaddressadditianal.ActivityAddressAdditianal;
import emerge.project.onmeal.ui.activity.landingsetlocation.ActivitySetLocation;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.ui.adaptor.AddressListAdapter;
import emerge.project.onmeal.ui.dialog.CustomDialogOne;
import emerge.project.onmeal.ui.dialog.CustomDialogTwo;
import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.UpdateToken;
import emerge.project.onmeal.utils.entittes.VersionUpdate;


public class ActivityLanding extends FragmentActivity implements OnMapReadyCallback, LandingView {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.relativelayout_delivery)
    RelativeLayout relativelayoutDelivery;

    @BindView(R.id.relativelayout_pickup)
    RelativeLayout relativelayoutPickup;


    @BindView(R.id.relativelayout_dinein)
    RelativeLayout relativelayoutDinein;


    @BindView(R.id.imageView_ic_delivery)
    ImageView imageViewIcDelivery;
    @BindView(R.id.imageView_ic_pickup)
    ImageView imageViewIcPickup;
    @BindView(R.id.imageView_icdinein)
    ImageView imageViewIcDinein;


    @BindView(R.id.textView_ic_delivery)
    TextView textViewIcDelivery;
    @BindView(R.id.textView_ic_pickup)
    TextView textViewIcPickup;

    @BindView(R.id.textView_ic_dinein)
    TextView textViewDineIn;


    @BindView(R.id.imageView_btn)
    ImageView imageViewBtn;

    @BindView(R.id.imageView_btn_addaditional)
    ImageView imageViewBtnAddaditional;

    @BindView(R.id.textView_title)
    TextView textViewTitle;


    @BindView(R.id.textView_selectedaddress)
    TextView textViewSelectedAddress;

    @BindView(R.id.relativelayout_address_addedlist)
    RelativeLayout relativelayoutAddedlist;


    @BindView(R.id.recyclerview_address)
    RecyclerView recyclerViewAddress;


    //navigation menu
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

    //navigation menu


    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;

    static final int REQUEST_CHECK_SETTINGS = 2;
    int PLACE_PICKER_REQUEST = 1;

    //  private GeoDataClient mGeoDataClient;
    Double latitude, longitude;
    private final LatLng mDefaultLocation = new LatLng(6.890872, 79.878859);

    FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    // private PlaceDetectionClient mPlaceDetectionClient;

    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;


    boolean isSelectSaverdAddres = false;

    private LatLng center;
    int sdk;

    Marker mapMarker;

    EncryptedPreferences encryptedPreferences;
    private static final String DISPATCH_TYPE = "dispatch_type";
    int dispatchType = -1;

    AddressItems addressItem;
    LandingPresenter landingPresenter;


    AddressListAdapter addressListAdapter;
    int setmapLocationStatus = 0;

    String placeId = "INSERT_PLACE_ID_HERE";
    PlacesClient placesClient;
    List<Place.Field> placeFields;
    FetchPlaceRequest request;

    Boolean isInitial = true;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();

        landingPresenter = new LandingPresenterImpli(this);
        landingPresenter.deleteAddress();

        addressItem = (AddressItems) getIntent().getSerializableExtra("addressItem");

        if (addressItem == null) {
            isInitial = true;
        } else {
            isInitial = false;
        }
        sdk = android.os.Build.VERSION.SDK_INT;



        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        placesClient = Places.createClient(this);

        placeFields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME,Place.Field.ADDRESS,Place.Field.NAME,Place.Field.ID);

        request = FetchPlaceRequest.builder(placeId, placeFields).build();




        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        setNavigationMenuItems();


        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            unBloackUserInteraction();
            getLocationPermission();

            createLocationRequest();
            checkLocationSettings();



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



        setAddressListRecycalView();


    }


    @Override
    protected void onStart() {
        super.onStart();





    }



    @OnClick(R.id.imageView_btn)
    public void onClickNextBtn(View view) {
        if(dispatchType == -1){
            try {
                new AlertDialog.Builder(this)
                        .setMessage("To continue, select Delivery or Dine-In or Pick Up !")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .create()
                        .show();
            } catch (WindowManager.BadTokenException e) {
                Toast.makeText(this, "To continue, select Delivery or Dine-In or Pick Up !", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }else if((dispatchType == 0) || (dispatchType == 2)){
            Intent intentSingup = new Intent(this, ActivityHome.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
            startActivity(intentSingup, bndlanimation);
            finish();
        }else if(dispatchType == 1) {
            if (isSelectSaverdAddres) {

                Intent intentSingup = new Intent(this, ActivityHome.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(intentSingup, bndlanimation);
                finish();

            } else {
                if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                    if (addressItem == null) {
                        Toast.makeText(this, "Please set the location as per the availability", Toast.LENGTH_LONG).show();
                    } else {
                        proprogressview.setVisibility(View.VISIBLE);
                        bloackUserInteraction();
                        landingPresenter.addNewAddress(addressItem);
                    }
                } else {

                    Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                }

            }

        }



    }


    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.relativelayout_delivery)
    public void onClickDelivery(View view) {

        proprogressview.setVisibility(View.VISIBLE);
        bloackUserInteraction();

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutDelivery.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_left_red));
            relativelayoutPickup.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_right_white));
            relativelayoutDinein.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dinein_white));


        } else {
            relativelayoutDelivery.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_left_red));
            relativelayoutPickup.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_right_white));

            relativelayoutDinein.setBackground(getResources().getDrawable(R.drawable.bg_dinein_white));
        }


        imageViewIcDelivery.setImageResource(R.drawable.ic_delivery_bick);
        textViewIcDelivery.setTextColor(getResources().getColor(R.color.colorTextWhite));


        imageViewIcPickup.setImageResource(R.drawable.ic_pick_up_dark);
        textViewIcPickup.setTextColor(getResources().getColor(R.color.colorTextIcon));


        imageViewIcDinein.setImageResource(R.drawable.ic_dinein_dark);
        textViewDineIn.setTextColor(getResources().getColor(R.color.colorTextIcon));


        imageViewBtn.setImageResource(R.drawable.btn_continuewithdilivery);
        imageViewBtnAddaditional.setVisibility(View.VISIBLE);


        textViewTitle.setText("Delivery Location");
        dispatchType = 1;

        encryptedPreferences.edit().putString(DISPATCH_TYPE, "Delivery").apply();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            getDeviceLocation();

            showCurrentPlace();

        } else {
            proprogressview.setVisibility(View.GONE);
            unBloackUserInteraction();
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.robotoLight2)
    public void textChangededitText(View view) {

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields)
                .build(this);
        startActivityForResult(intent, PLACE_PICKER_REQUEST);


    }


    @OnClick(R.id.relativelayout_pickup)
    public void onClickPickup(View view) {

        proprogressview.setVisibility(View.GONE);

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutPickup.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_right_red));
            relativelayoutDelivery.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_left_white));

            relativelayoutDinein.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dinein_white));
        } else {
            relativelayoutPickup.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_right_red));
            relativelayoutDelivery.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_left_white));

            relativelayoutDinein.setBackground(getResources().getDrawable(R.drawable.bg_dinein_white));
        }

        imageViewIcPickup.setImageResource(R.drawable.ic_pickup);
        textViewIcPickup.setTextColor(getResources().getColor(R.color.colorTextWhite));


        imageViewIcDelivery.setImageResource(R.drawable.ic_delivery_bick_dark);
        textViewIcDelivery.setTextColor(getResources().getColor(R.color.colorTextIcon));


        imageViewIcDinein.setImageResource(R.drawable.ic_dinein_dark);
        textViewDineIn.setTextColor(getResources().getColor(R.color.colorTextIcon));

        imageViewBtn.setImageResource(R.drawable.btn_continuewithpickup);
        imageViewBtnAddaditional.setVisibility(View.GONE);

        textViewTitle.setText("Pick Up");
        textViewSelectedAddress.setText("");
        dispatchType = 0;

        encryptedPreferences.edit().putString(DISPATCH_TYPE, "Pickup").apply();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {


        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


        mMap.clear();



        if(mDefaultLocation == null){

        }else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mDefaultLocation.latitude, mDefaultLocation.longitude), 1));
        }


    }


    @OnClick(R.id.relativelayout_dinein)
    public void onClickDineIn(View view) {
        dineIN();

    }


    public void dineIN() {


        proprogressview.setVisibility(View.GONE);

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutPickup.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_right_white));
            relativelayoutDelivery.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_left_white));

            relativelayoutDinein.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dinein_red));
        } else {
            relativelayoutPickup.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_right_white));
            relativelayoutDelivery.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_left_white));

            relativelayoutDinein.setBackground(getResources().getDrawable(R.drawable.bg_dinein_red));
        }

        imageViewIcPickup.setImageResource(R.drawable.ic_pick_up_dark);
        textViewIcPickup.setTextColor(getResources().getColor(R.color.colorTextIcon));


        imageViewIcDelivery.setImageResource(R.drawable.ic_delivery_bick_dark);
        textViewIcDelivery.setTextColor(getResources().getColor(R.color.colorTextIcon));


        imageViewIcDinein.setImageResource(R.drawable.ic_dinein);
        textViewDineIn.setTextColor(getResources().getColor(R.color.colorTextWhite));

        imageViewBtn.setImageResource(R.drawable.btn_continuewithdinin);
        imageViewBtnAddaditional.setVisibility(View.GONE);

        textViewTitle.setText("Dine In");
        textViewSelectedAddress.setText("");
        dispatchType = 2;

        encryptedPreferences.edit().putString(DISPATCH_TYPE, "Dinein").apply();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {


        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }

        mMap.clear();
        if(mDefaultLocation == null){

        }else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mDefaultLocation.latitude, mDefaultLocation.longitude), 1));
        }


    }


    @OnClick(R.id.imageView_btn_addaditional)
    public void onClickAddaditional(View view) {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            if (addressItem == null) {
                Toast.makeText(this, "Please set the location as per the availability", Toast.LENGTH_LONG).show();
            } else {
                Intent intentSingup = new Intent(this, ActivityAddressAdditianal.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
                intentSingup.putExtra("addressItem", addressItem);
                startActivity(intentSingup, bndlanimation);
            }


        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.relativelayout_address)
    public void onClickLocation(View view) {
        if (dispatchType == 1) {
            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                setmapLocationStatus = 1;
                proprogressview.setVisibility(View.VISIBLE);
                bloackUserInteraction();
                landingPresenter.getAddress();

            } else {
                Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
            }

        } else {
            setmapLocationStatus = 0;
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));

        if(dispatchType == 1){
            updateLocationUI();
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }else {

        }

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {

            try {
                if (mLocationRequest.getPriority() == 100) {
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Location");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setMessage("We're having trouble locating you.To find your location faster and more accurately,turn on your device's high-accuracy mode");
                    alertDialogBuilder.setPositiveButton("Turn On",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    checkLocationSettings();

                                }
                            });
                    alertDialogBuilder.show();
                }

            }catch (Exception ex){


            }

        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }
        mapMarkerMove();



    }

    private void mapMarkerMove(){

        try {

            mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                @Override
                public void onCameraMove() {
                    mMap.clear();

                    center = mMap.getCameraPosition().target;

                    mapMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(center.latitude, center.longitude))
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));

                }
            });


            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                @Override
                public void onCameraIdle() {

                    if (center == null) {

                    } else {
                        getAddressFromLocation(center.latitude, center.longitude);
                    }

                }
            });
        }catch (Exception ex){


        }



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == -1) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                try {
                    imageViewBtnAddaditional.setVisibility(View.VISIBLE);

                    mMap.clear();

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude), 15));
                    mapMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));

                    try {
                        if (place.getAddress().length() > 30) {
                            textViewSelectedAddress.setText(place.getAddress().substring(0, 30));
                        } else {
                            textViewSelectedAddress.setText(place.getAddress());
                        }
                    } catch (ArrayIndexOutOfBoundsException aiobex) {
                        textViewSelectedAddress.setText(place.getAddress());
                    }


                    landingPresenter.getSellectedAddressDetails(place.getName(), place.getAddress(), place.getLatLng());

                    relativelayoutAddedlist.setVisibility(View.INVISIBLE);

                } catch (Exception ex) {
                    Toast.makeText(this, "Place request fail,try again", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Place request fail,try again", Toast.LENGTH_SHORT).show();
            }


        } else if (requestCode == REQUEST_CHECK_SETTINGS) {
            mapFragment.getMapAsync(this);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;

            }
        } catch (SecurityException e) {

            Log.e("Exception: %s", e.getMessage());
        }
    }


    private void checkLocationSettings() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                getDeviceLocation();

            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(ActivityLanding.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });

    }


    private void getDeviceLocation() {
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful() && task.isComplete()) {
                        mLastKnownLocation = (Location) task.getResult();
                        if (mLastKnownLocation == null) {

                        } else {
                            if (isInitial) {
                                if(dispatchType == 1){
                                    mMap.clear();
                                    mapMarker = mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 15));
                                    mMap.setMyLocationEnabled(false);
                                    mMap.getUiSettings().setMyLocationButtonEnabled(false);

                                }
                            }else {
                                mMap.clear();
                                if(dispatchType  == 1){
                                    mMap.clear();
                                    mapMarker = mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(addressItem.getAddressLatitude(), addressItem.getAddressLongitude()))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(addressItem.getAddressLatitude(), addressItem.getAddressLongitude()), 15));
                                    mMap.setMyLocationEnabled(false);
                                    mMap.getUiSettings().setMyLocationButtonEnabled(false);

                                }

                            }

                            if (dispatchType == 1) {
                                getAddressFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            } else {

                            }
                        }

                    } else {
                      /*  if (isInitial) {
                            mMap.clear();
                            mapMarker = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                            mMap.setMyLocationEnabled(false);


                        }
                        if (dispatchType == 1) {
                           getAddressFromLocation(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                        } else {

                        }*/
                    }
                }
            });


        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    public void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> address;
        try {
            geocoder = new Geocoder(this, Locale.ENGLISH);
            address = geocoder.getFromLocation(latitude, longitude, 1);
            StringBuilder str = new StringBuilder();
            if (geocoder.isPresent()) {
                Address returnAddress = address.get(0);

                if (returnAddress.getAddressLine(0).isEmpty() || returnAddress.getAddressLine(0) == null) {

                } else {
                    if (returnAddress.getAddressLine(0).length() > 30) {
                        textViewSelectedAddress.setText(returnAddress.getAddressLine(0).substring(0, 30));
                    } else {
                        textViewSelectedAddress.setText(returnAddress.getAddressLine(0));
                    }


                    LatLng mDefaultLocation = new LatLng(returnAddress.getLatitude(), returnAddress.getLongitude());

                    landingPresenter.getSellectedAddressDetails("", returnAddress.getAddressLine(0), mDefaultLocation);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }

        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {
                Place place = response.getPlace();

                textViewSelectedAddress.setText(place.getAddress());
                landingPresenter.getSellectedAddressDetails(place.getName(), place.getAddress(), place.getLatLng());

                proprogressview.setVisibility(View.GONE);
                unBloackUserInteraction();

                System.out.println("sssss  suc");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    proprogressview.setVisibility(View.GONE);
                    unBloackUserInteraction();
                }
            }
        });

    }


    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void selectedAddressDetails(AddressItems addressItems) {
        addressItem = addressItems;
    }

    @Override
    public void selectedAddressDetailsFail() {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        try {

            new AlertDialog.Builder(this)
                    .setTitle("Current Location")
                    .setMessage("we are having a issue with getting your location,Please try again")
                    .setPositiveButton("Re-Try", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                proprogressview.setVisibility(View.VISIBLE);
                                bloackUserInteraction();
                                showCurrentPlace();
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                            }


                        }
                    })
                    .setNegativeButton("No", null)
                    .create()
                    .show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, "we are having a issue with getting your location,Please try again", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    private void setAddressListRecycalView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewAddress.setLayoutManager(layoutManager);
        recyclerViewAddress.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAddress.setNestedScrollingEnabled(false);

    }


    @Override
    public void getAddressEmpty() {
        recyclerViewAddress.setVisibility(View.GONE);
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        relativelayoutAddedlist.setVisibility(View.VISIBLE);

    }

    @Override
    public void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        recyclerViewAddress.setVisibility(View.VISIBLE);
        relativelayoutAddedlist.setVisibility(View.VISIBLE);
        addressListAdapter = new AddressListAdapter(this, addressItemsArrayList, this);
        recyclerViewAddress.setAdapter(addressListAdapter);
    }

    @Override
    public void getAddressFail(String msg) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        relativelayoutAddedlist.setVisibility(View.VISIBLE);
    }

    @Override
    public void addNewAddressSuccessful() {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        Intent intentSingup = new Intent(this, ActivityHome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();

    }

    @Override
    public void addNewAddressFail(String msg) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        try {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            proprogressview.setVisibility(View.VISIBLE);
                            bloackUserInteraction();
                            landingPresenter.addNewAddress(addressItem);

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
    public void saveAddressSuccessful(AddressItems add) {

        isSelectSaverdAddres = true;
        imageViewBtnAddaditional.setVisibility(View.INVISIBLE);

        final String selectedAddress=add.getAddressNumber()+" "+add.getAddressRoad()+" "+add.getAddressCity();

        try {
            if (selectedAddress.length() > 30) {
                textViewSelectedAddress.setText(selectedAddress.substring(0, 30));
            } else {
                textViewSelectedAddress.setText(selectedAddress);
            }
        } catch (ArrayIndexOutOfBoundsException aiobex) {
            textViewSelectedAddress.setText(selectedAddress);
        }


        addressItem = add;
        try {
            mMap.clear();
            mapMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(add.getAddressLatitude(), add.getAddressLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(add.getAddressLatitude(), add.getAddressLongitude()), 15));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.setMyLocationEnabled(false);


        }catch (Exception ex){


        }


        setmapLocationStatus = 0;
        relativelayoutAddedlist.setVisibility(View.GONE);
    }


    /* @Override
    public void saveAddressSuccessful(String add) {

        isSelectSaverdAddres = true;
        imageViewBtnAddaditional.setVisibility(View.INVISIBLE);


        try {
            if (add.length() > 30) {
                textViewSelectedAddress.setText(add.substring(0, 30));
            } else {
                textViewSelectedAddress.setText(add);
            }
        } catch (ArrayIndexOutOfBoundsException aiobex) {
            textViewSelectedAddress.setText(add);
        }




        addressItem = new AddressItems();
        addressItem.setAddress(add);




      *//*  mMap.clear();
        mapMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(add, add.getLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15));
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(false);*//*

        setmapLocationStatus = 0;
        relativelayoutAddedlist.setVisibility(View.GONE);
    }
*/
    @Override
    public void saveAddressFail(String msg) {


    }

    @Override
    public void onBackPressed() {

        if (setmapLocationStatus == 1) {
            setmapLocationStatus = 0;
            relativelayoutAddedlist.setVisibility(View.GONE);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit!");
            alertDialogBuilder.setMessage("Do you really want to exit ?");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
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

    }

    private void setNavigationMenuItems() {
        textViewNavigationGetFood.setTextColor(getResources().getColor(R.color.colorAccent));
        textViewNavigationYourOrders.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationFavourites.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationProfile.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationSettings.setTextColor(getResources().getColor(R.color.navigationTextColor));

    }

    @OnClick(R.id.text_navigation_getfood)
    public void onClickNavigationGetFood(View view) {

    }


    @OnClick(R.id.text_navigation_yourorders)
    public void onClickNavigationYourOrders(View view) {
        Intent intentSingup = new Intent(this, ActivityHistory.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);

    }


    @OnClick(R.id.text_navigation_favourites)
    public void onClickNavigationFavourites(View view) {

        Intent intentSingup = new Intent(this, ActivityFavourites.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
    }


    @OnClick(R.id.text_navigation_profile)
    public void onClickNavigationProfile(View view) {
        Intent intentSingup = new Intent(this, ActivityProfile.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);


    }

    @OnClick(R.id.text_navigation_settings)
    public void onClickNavigationSettings(View view) {
        Intent intentSingup = new Intent(this, ActivitySettings.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);


    }


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    @Override
    public void signOutSuccess() {

        Intent intent = new Intent(this, ActivityLogin.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intent, bndlanimation);

    }



    @OnClick(R.id.relativelayout_logout)
    public void onSingOut(View view) {
        landingPresenter.signOut(this);

    }


}
