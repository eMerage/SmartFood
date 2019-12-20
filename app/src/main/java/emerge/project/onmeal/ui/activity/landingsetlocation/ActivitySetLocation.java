package emerge.project.onmeal.ui.activity.landingsetlocation;

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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
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
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.landingaddressmenual.ActivityMenualAddress;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.utils.entittes.AddressItems;

public class ActivitySetLocation extends FragmentActivity implements OnMapReadyCallback, SetLocationView {


    private GoogleMap mMap;

    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
   // private GeoDataClient mGeoDataClient;

    private final LatLng mDefaultLocation = new LatLng(6.890872, 79.878859);

    FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    static final int REQUEST_CHECK_SETTINGS = 2;

  //  private PlaceDetectionClient mPlaceDetectionClient;

    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    private LatLng center;

    Marker mapMarker;

    @BindView(R.id.textView_selectedaddress)
    TextView textViewSelectedAddress;

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


    SetLocationPresenter setLocationPresenter;
    AddressItems addressItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        ButterKnife.bind(this);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

      //  mGeoDataClient = Places.getGeoDataClient(this, null);
      //  mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        setLocationPresenter = new SetLocationPresenterImpli(this);

        setNavigationMenuItems();
        createLocationRequest();
        checkLocationSettings();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }




    @OnClick(R.id.relativelayout_menualy)
    public void onClicManual(View view) {
        if(center==null){
            Toast.makeText(this, "Please set the Location on map", Toast.LENGTH_LONG).show();
        }else {

            AddressItems addressItems = new AddressItems();
            addressItems.setAddressLatitude(center.latitude);
            addressItems.setAddressLongitude(center.longitude);

            Intent intentSingup = new Intent(this, ActivityMenualAddress.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
            intentSingup.putExtra("LOCATION", addressItems);
            startActivity(intentSingup, bndlanimation);
            finish();
        }






    }

    @OnClick(R.id.imageView6)
    public void onClicNext(View view) {

        if (addressItem == null) {
            Toast.makeText(this, "Location is not set", Toast.LENGTH_LONG).show();
        } else {

            Intent intentSingup = new Intent(this, ActivityLanding.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
            intentSingup.putExtra("addressItem", addressItem);
            startActivity(intentSingup, bndlanimation);
            finish();

        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));
        getLocationPermission();
        updateLocationUI();

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            getDeviceLocation();
        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


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

    private void checkLocationSettings() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(ActivitySetLocation.this, REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });

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
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not availablele.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation == null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 10));
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                                mMap.setMyLocationEnabled(false);
                            } else {

                                mLastKnownLocation = (Location) task.getResult();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), 15));
                                mMap.setMyLocationEnabled(false);
                                mMap.getUiSettings().setMyLocationButtonEnabled(false);


                                mapMarker = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()))
                                        .draggable(true)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place)));

                                if (mLastKnownLocation == null) {

                                } else {
                                    getAddressFromLocation(mLastKnownLocation.getLatitude(),  mLastKnownLocation.getLongitude());
                                }
                            }

                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 10));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                            mMap.setMyLocationEnabled(false);

                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    public void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> address;
        try {
            geocoder = new Geocoder(ActivitySetLocation.this, Locale.ENGLISH);
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

                    // System.out.println("qqqqqqqqqqqqq :"+returnAddress.getAddressLine());

                    LatLng mDefaultLocation = new LatLng(returnAddress.getLatitude(), returnAddress.getLongitude());
                    setLocationPresenter.getSellectedAddressDetails("", returnAddress.getAddressLine(0), mDefaultLocation);

                }

            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    private void setNavigationMenuItems() {
        textViewNavigationGetFood.setTextColor(getResources().getColor(R.color.colorAccent));
        textViewNavigationFavourites.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationYourOrders.setTextColor(getResources().getColor(R.color.navigationTextColor));
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

    @Override
    public void selectedAddressDetails(AddressItems addressItems) {
        addressItem = addressItems;
    }

    @Override
    public void selectedAddressDetailsFail() {


    }


    @Override
    public void onBackPressed() {
        if (addressItem == null) {
            Toast.makeText(this, "Location is not set", Toast.LENGTH_LONG).show();
        } else {

        }
        Intent intentSingup = new Intent(this, ActivityLanding.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intentSingup.putExtra("addressItem", addressItem);
        startActivity(intentSingup, bndlanimation);
        finish();

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
        setLocationPresenter.signOut(this);

    }

}
