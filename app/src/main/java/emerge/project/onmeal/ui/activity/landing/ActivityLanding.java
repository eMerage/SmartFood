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
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import com.tuyenmonkey.mkloader.MKLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.ui.adaptor.AddressListAdapter;
import emerge.project.onmeal.ui.dialog.CustomDialogOne;
import emerge.project.onmeal.ui.dialog.CustomDialogTwo;
import emerge.project.onmeal.utils.entittes.AddressItems;


public class ActivityLanding extends FragmentActivity implements OnMapReadyCallback, LandingView {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.relativelayout_delivery)
    RelativeLayout relativelayoutDelivery;

    @BindView(R.id.relativelayout_pickup)
    RelativeLayout relativelayoutPickup;


    @BindView(R.id.imageView_ic_delivery)
    ImageView imageViewIcDelivery;
    @BindView(R.id.imageView_ic_pickup)
    ImageView imageViewIcPickup;


    @BindView(R.id.textView_ic_delivery)
    TextView textViewIcDelivery;
    @BindView(R.id.textView_ic_pickup)
    TextView textViewIcPickup;


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


    int sdk;

    Marker mapMarker;

    EncryptedPreferences encryptedPreferences;
    private static final String DISPATCH_TYPE = "dispatch_type";
    int dispatchType = 0;

    AddressItems addressItem;
    LandingPresenter landingPresenter;


    AddressListAdapter addressListAdapter;
    int setmapLocationStatus = 0;

    String placeId = "INSERT_PLACE_ID_HERE";
    PlacesClient placesClient;
    List<Place.Field> placeFields;
    FetchPlaceRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();

        landingPresenter = new LandingPresenterImpli(this);
        landingPresenter.deleteAddress();

        addressItem = (AddressItems) getIntent().getSerializableExtra("addressItem");


        sdk = android.os.Build.VERSION.SDK_INT;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Places.initialize(getApplicationContext(),getResources().getString(R.string.google_maps_key));
         placesClient = Places.createClient(this);

         placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

         request = FetchPlaceRequest.builder(placeId, placeFields).build();

       // mGeoDataClient = Places.getGeoDataClient(this, null);
      //  mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        setNavigationMenuItems();


        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {


            unBloackUserInteraction();
            getLocationPermission();

            createLocationRequest();
            checkLocationSettings();


            if (addressItem == null) {
                encryptedPreferences.edit().putString(DISPATCH_TYPE, "Pickup").apply();
                dispatchType = 0;
            } else {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    relativelayoutDelivery.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_left_red));
                    relativelayoutPickup.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_right_white));

                } else {
                    relativelayoutDelivery.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_left_red));
                    relativelayoutPickup.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_right_white));
                }
                imageViewIcDelivery.setImageResource(R.drawable.ic_delivery_bick);
                textViewIcDelivery.setTextColor(getResources().getColor(R.color.colorTextWhite));


                imageViewIcPickup.setImageResource(R.drawable.ic_pick_up_dark);
                textViewIcPickup.setTextColor(getResources().getColor(R.color.colorTextIcon));

                imageViewBtn.setImageResource(R.drawable.btn_continuewithdilivery);
                imageViewBtnAddaditional.setVisibility(View.VISIBLE);


                textViewTitle.setText("Delivery Location");
                dispatchType = 1;
                encryptedPreferences.edit().putString(DISPATCH_TYPE, "Delivery").apply();


                try {
                    if (addressItem.getAddress().length() > 30) {
                        textViewSelectedAddress.setText(addressItem.getAddress().substring(0, 30));
                    } else {
                        textViewSelectedAddress.setText(addressItem.getAddress());
                    }
                }catch (ArrayIndexOutOfBoundsException aiobex){
                    textViewSelectedAddress.setText(addressItem.getAddress());
                }




            }


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


    @OnClick(R.id.robotoRegular4)
    public void onClicSetLocationOnMap(View view) {

        isSelectSaverdAddres = false;
        imageViewBtnAddaditional.setVisibility(View.VISIBLE);

        Intent intentSingup = new Intent(this, ActivitySetLocation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();


    }


    @OnClick(R.id.imageView_btn)
    public void onClickNextBtn(View view) {

        if (dispatchType == 1) {

            if (isSelectSaverdAddres) {

                Intent intentSingup = new Intent(this, ActivityHome.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(intentSingup, bndlanimation);
                finish();

            } else {

                if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                    if (addressItem == null) {
                        Toast.makeText(this, "Please set the location", Toast.LENGTH_LONG).show();
                    } else {
                        proprogressview.setVisibility(View.VISIBLE);
                        bloackUserInteraction();
                        landingPresenter.addNewAddress(addressItem);
                    }
                } else {

                    Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                }

            }


        } else {

            Intent intentSingup = new Intent(this, ActivityHome.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
            startActivity(intentSingup, bndlanimation);
            finish();


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

        } else {
            relativelayoutDelivery.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_left_red));
            relativelayoutPickup.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_right_white));
        }
        imageViewIcDelivery.setImageResource(R.drawable.ic_delivery_bick);
        textViewIcDelivery.setTextColor(getResources().getColor(R.color.colorTextWhite));


        imageViewIcPickup.setImageResource(R.drawable.ic_pick_up_dark);
        textViewIcPickup.setTextColor(getResources().getColor(R.color.colorTextIcon));

        imageViewBtn.setImageResource(R.drawable.btn_continuewithdilivery);
        imageViewBtnAddaditional.setVisibility(View.VISIBLE);


        textViewTitle.setText("Delivery Location");
        dispatchType = 1;
        encryptedPreferences.edit().putString(DISPATCH_TYPE, "Delivery").apply();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {


            if (mLastKnownLocation == null) {

            } else {
                mapMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));

            }


            showCurrentPlace();

        } else {
            proprogressview.setVisibility(View.GONE);
            unBloackUserInteraction();
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.robotoLight2)
    public void textChangededitText(View view) {

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields).build(this);
        startActivityForResult(intent, PLACE_PICKER_REQUEST);


    }


    @OnClick(R.id.relativelayout_pickup)
    public void onClickPickup(View view) {

        proprogressview.setVisibility(View.GONE);

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            relativelayoutPickup.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_right_red));
            relativelayoutDelivery.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_dispatchtype_left_white));

        } else {
            relativelayoutPickup.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_right_red));
            relativelayoutDelivery.setBackground(getResources().getDrawable(R.drawable.bg_dispatchtype_left_white));
        }

        imageViewIcPickup.setImageResource(R.drawable.ic_pickup);
        textViewIcPickup.setTextColor(getResources().getColor(R.color.colorTextWhite));


        imageViewIcDelivery.setImageResource(R.drawable.ic_delivery_bick_dark);
        textViewIcDelivery.setTextColor(getResources().getColor(R.color.colorTextIcon));

        imageViewBtn.setImageResource(R.drawable.btn_continuewithpickup);
        imageViewBtnAddaditional.setVisibility(View.GONE);

        textViewTitle.setText("Pick Up");
        textViewSelectedAddress.setText("");
        dispatchType = 0;

        encryptedPreferences.edit().putString(DISPATCH_TYPE, "Pickup").apply();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {

            if (mapMarker != null) {
                mapMarker.remove();
            } else {

            }

        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


    }


    @OnClick(R.id.imageView_btn_addaditional)
    public void onClickAddaditional(View view) {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            if (addressItem == null) {
                Toast.makeText(this, "Please set the location", Toast.LENGTH_LONG).show();
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
                imageViewBtnAddaditional.setVisibility(View.INVISIBLE);
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
        updateLocationUI();

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {

            if (addressItem == null) {

            } else {

                mapMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(addressItem.getAddressLatitude(), addressItem.getAddressLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));
            }


            if (mLocationRequest.getPriority() == 100) {
                getDeviceLocation();
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

        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



      /*  if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {


                Place place = PlaceAutocomplete.getPlace(this, data);


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude), 15));
                mapMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));


                try {
                    if (place.getAddress().toString().length() > 30) {
                        textViewSelectedAddress.setText(place.getAddress().toString().substring(0, 30));
                    } else {
                        textViewSelectedAddress.setText(place.getAddress().toString());
                    }
                }catch (ArrayIndexOutOfBoundsException aiobex){
                    textViewSelectedAddress.setText(place.getAddress().toString());
                }


                landingPresenter.getSellectedAddressDetails(place.getName().toString(), place.getAddress().toString(), place.getLatLng());

                relativelayoutAddedlist.setVisibility(View.INVISIBLE);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);


            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        } else if (requestCode == REQUEST_CHECK_SETTINGS) {
            mapFragment.getMapAsync(this);
        }*/


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

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 15));
                            mMap.setMyLocationEnabled(false);
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);

                        }

                    } else {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        mMap.setMyLocationEnabled(false);

                    }


                }
            });


        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }

        // Add a listener to handle the response.
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {
                Place place = response.getPlace();

                textViewSelectedAddress.setText(place.getAddress());

               /* if (mLikelyPlaceAddresses[4].length() > 30) {
                    textViewSelectedAddress.setText(place.getAddress());
                } else {
                    textViewSelectedAddress.setText(place.getAddress());
                }*/

                landingPresenter.getSellectedAddressDetails(place.getName(),place.getAddress(),place.getLatLng());

                proprogressview.setVisibility(View.GONE);
                unBloackUserInteraction();

                System.out.println("sssss  suc");
                //Log.i(TAG, "Place found: " + place.getName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    System.out.println("sssss  fail :"+exception);
                    System.out.println("sssss  fail statusCode :"+statusCode);
                    System.out.println("sssss  fail ApiException :"+apiException);
                    proprogressview.setVisibility(View.GONE);
                    unBloackUserInteraction();
                }
            }
        });
/*
        if (mLocationPermissionGranted) {
            try {
                @SuppressWarnings("MissingPermission") final Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
                placeResult.addOnCompleteListener
                        (new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                            @Override
                            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();

                                    // Set the count, handling cases where less than 5 entries are returned.
                                    int count;
                                    if (likelyPlaces.getCount() < 5) {
                                        count = likelyPlaces.getCount();
                                    } else {
                                        count = 5;
                                    }

                                    int i = 0;
                                    mLikelyPlaceNames = new String[count];
                                    mLikelyPlaceAddresses = new String[count];
                                    mLikelyPlaceAttributions = new String[count];
                                    mLikelyPlaceLatLngs = new LatLng[count];
                                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                        // Build a list of likely places to show the user.
                                        mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                                        mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace().getAddress();
                                        mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace().getAttributions();
                                        mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
                                        i++;
                                        if (i > (count - 1)) {
                                            break;
                                        }


                                    }
                                    likelyPlaces.release();


                                    if (mLikelyPlaceAddresses[4].length() > 30) {
                                        textViewSelectedAddress.setText(mLikelyPlaceAddresses[4].substring(0, 30));
                                    } else {
                                        textViewSelectedAddress.setText(mLikelyPlaceAddresses[4]);
                                    }

                                    landingPresenter.getSellectedAddressDetails(mLikelyPlaceNames[4], mLikelyPlaceAddresses[4], mLikelyPlaceLatLngs[4]);

                                    proprogressview.setVisibility(View.GONE);
                                    unBloackUserInteraction();

                                } else {
                                    proprogressview.setVisibility(View.GONE);
                                    unBloackUserInteraction();

                                }
                            }
                        });
            } catch (ArrayIndexOutOfBoundsException e) {
                Toast.makeText(this, "we are having a issue with getting your current location", Toast.LENGTH_SHORT).show();
            }

        } else {
            getLocationPermission();
        }*/
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
    public void saveAddressSuccessful(String add) {

        isSelectSaverdAddres = true;
        imageViewBtnAddaditional.setVisibility(View.INVISIBLE);

        try {
            if (add.length() > 30) {
                textViewSelectedAddress.setText(add.substring(0, 30));
            } else {
                textViewSelectedAddress.setText(add);
            }
        }catch (ArrayIndexOutOfBoundsException aiobex){
            textViewSelectedAddress.setText(add);
        }


        addressItem = new AddressItems();
        addressItem.setAddress(add);

        setmapLocationStatus = 0;
        relativelayoutAddedlist.setVisibility(View.GONE);
    }

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


}
