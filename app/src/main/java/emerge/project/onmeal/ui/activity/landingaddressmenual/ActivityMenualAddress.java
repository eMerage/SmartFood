package emerge.project.onmeal.ui.activity.landingaddressmenual;

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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.tuyenmonkey.mkloader.MKLoader;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.landingsetlocation.ActivitySetLocation;
import emerge.project.onmeal.utils.entittes.AddressItems;

public class ActivityMenualAddress extends FragmentActivity implements OnMapReadyCallback, AddressMenualView {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;


    private GoogleMap mMap;

    SupportMapFragment mapFragment;
    LocationRequest mLocationRequest;
  //  private GeoDataClient mGeoDataClient;

    private final LatLng mDefaultLocation = new LatLng(6.890872, 79.878859);

    FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    static final int REQUEST_CHECK_SETTINGS = 2;


    private LatLng center;
    Marker mapMarker;


    @BindView(R.id.text_addres_name_hint)
    TextView textViewHintName;
    @BindView(R.id.text_addres_city_hint)
    TextView textViewHintCity;
    @BindView(R.id.text_addres_mainroad_hint)
    TextView textViewHintMainroad;
    @BindView(R.id.text_addres_subroad_hint)
    TextView textViewHintSubroad;
    @BindView(R.id.text_addres_number_hint)
    TextView textViewHintNumber;
    @BindView(R.id.text_addres_floor_hint)
    TextView textViewHintFloor;
    @BindView(R.id.text_addres_department_hint)
    TextView textViewHintDepartment;
    @BindView(R.id.text_addres_landmark_hint)
    TextView textViewHintLandmark;
    @BindView(R.id.text_addres_instraction_hint)
    TextView textViewHintInstraction;


    @BindView(R.id.edittext_addres_name)
    EditText edittextAddresName;
    @BindView(R.id.edittext_addres_city)
    EditText edittextAddresCity;
    @BindView(R.id.edittext_addres_mainroad)
    EditText edittextAddresMainraod;
    @BindView(R.id.edittext_addres_subroad)
    EditText edittextAddresSubroad;
    @BindView(R.id.edittext_addres_number)
    EditText edittextAddresNumber;
    @BindView(R.id.edittext_addres_floor)
    EditText edittextAddresFloor;
    @BindView(R.id.edittext_addres_department)
    EditText edittextAddresDepartment;
    @BindView(R.id.edittext_addres_landmark)
    EditText edittextAddresLandmark;
    @BindView(R.id.edittext_addres_instructions)
    EditText edittextAddresInstructions;


    private static final String location = "LOCATION";

    AddressItems addressItem;
    AddressMenualPresenter addressMenualPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menual_address);
        ButterKnife.bind(this);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        addressItem = (AddressItems) getIntent().getSerializableExtra("LOCATION");


        addressMenualPresenter = new AddressMenaulPresenterImpli(this);


    }

    @OnClick(R.id.relativelayout_menu)
    public void onClickBackMenu(View view) {


        Intent intentSingup = new Intent(this, ActivitySetLocation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();


    }


    @Override
    public void onBackPressed() {

        Intent intentSingup = new Intent(this, ActivitySetLocation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();


    }


    @OnClick(R.id.imageView_add)
    public void onClickAdd(View view) {

        if (edittextAddresName.getText().toString().isEmpty() || edittextAddresName.getText().toString().equals("")) {
            Toast.makeText(this, "Address Name is empty", Toast.LENGTH_SHORT).show();
        } else if (edittextAddresCity.getText().toString().isEmpty() || edittextAddresCity.getText().toString().equals("")) {
            Toast.makeText(this, "Address City is empty", Toast.LENGTH_SHORT).show();
        } else if (edittextAddresMainraod.getText().toString().isEmpty() || edittextAddresMainraod.getText().toString().equals("")) {
            Toast.makeText(this, "Address Main Road is empty", Toast.LENGTH_SHORT).show();
        } else if (edittextAddresNumber.getText().toString().isEmpty() || edittextAddresNumber.getText().toString().equals("")) {
            Toast.makeText(this, "Address Number is empty", Toast.LENGTH_SHORT).show();
        } else {
            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                proprogressview.setVisibility(View.VISIBLE);
                bloackUserInteraction();

                addressMenualPresenter.addNewAddress(addAddressDetailsToList());
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

        }


    }


    private AddressItems addAddressDetailsToList() {
        AddressItems userAddressItems = new AddressItems();
        userAddressItems.setAddressName(edittextAddresName.getText().toString());
        userAddressItems.setAddressCity(edittextAddresCity.getText().toString());
        userAddressItems.setMainRoad(edittextAddresMainraod.getText().toString());
        userAddressItems.setSubRoad(edittextAddresSubroad.getText().toString());
        userAddressItems.setAddressNumber(edittextAddresNumber.getText().toString());
        userAddressItems.setAddressFloor(edittextAddresFloor.getText().toString());
        userAddressItems.setAddressLandmark(edittextAddresLandmark.getText().toString());
        userAddressItems.setAddressCompanyDepartment(edittextAddresDepartment.getText().toString());
        userAddressItems.setAddressDeliveryInstructions(edittextAddresInstructions.getText().toString());
        userAddressItems.setAddressLongitude(addressItem.getAddressLongitude());
        userAddressItems.setAddressLatitude(addressItem.getAddressLatitude());
        return userAddressItems;
    }


    @OnTextChanged(value = R.id.edittext_addres_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedName(CharSequence text) {
        if (text.length() == 0) {
            textViewHintName.setVisibility(View.INVISIBLE);
            textViewHintName.setEnabled(false);
        } else {
            if (textViewHintName.isEnabled()) {
            } else {
                textViewHintName.setVisibility(View.VISIBLE);
                textViewHintName.setEnabled(true);
            }
        }
    }

    @OnTextChanged(value = R.id.edittext_addres_city, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedCity(CharSequence text) {
        if (text.length() == 0) {
            textViewHintCity.setVisibility(View.INVISIBLE);
            textViewHintCity.setEnabled(false);
        } else {
            if (textViewHintCity.isEnabled()) {
            } else {
                textViewHintCity.setVisibility(View.VISIBLE);
                textViewHintCity.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_mainroad, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedMainroad(CharSequence text) {
        if (text.length() == 0) {
            textViewHintMainroad.setVisibility(View.INVISIBLE);
            textViewHintMainroad.setEnabled(false);
        } else {
            if (textViewHintMainroad.isEnabled()) {
            } else {
                textViewHintMainroad.setVisibility(View.VISIBLE);
                textViewHintMainroad.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_subroad, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedSubroad(CharSequence text) {
        if (text.length() == 0) {
            textViewHintSubroad.setVisibility(View.INVISIBLE);
            textViewHintSubroad.setEnabled(false);
        } else {
            if (textViewHintSubroad.isEnabled()) {
            } else {
                textViewHintSubroad.setVisibility(View.VISIBLE);
                textViewHintSubroad.setEnabled(true);
            }
        }
    }

    @OnTextChanged(value = R.id.edittext_addres_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedNumber(CharSequence text) {
        if (text.length() == 0) {
            textViewHintNumber.setVisibility(View.INVISIBLE);
            textViewHintNumber.setEnabled(false);
        } else {
            if (textViewHintNumber.isEnabled()) {
            } else {
                textViewHintNumber.setVisibility(View.VISIBLE);
                textViewHintNumber.setEnabled(true);
            }
        }
    }

    @OnTextChanged(value = R.id.edittext_addres_floor, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedFloor(CharSequence text) {
        if (text.length() == 0) {
            textViewHintFloor.setVisibility(View.INVISIBLE);
            textViewHintFloor.setEnabled(false);
        } else {
            if (textViewHintFloor.isEnabled()) {
            } else {
                textViewHintFloor.setVisibility(View.VISIBLE);
                textViewHintFloor.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_department, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedDepartment(CharSequence text) {
        if (text.length() == 0) {
            textViewHintDepartment.setVisibility(View.INVISIBLE);
            textViewHintDepartment.setEnabled(false);
        } else {
            if (textViewHintDepartment.isEnabled()) {
            } else {
                textViewHintDepartment.setVisibility(View.VISIBLE);
                textViewHintDepartment.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_landmark, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedLandmark(CharSequence text) {
        if (text.length() == 0) {
            textViewHintLandmark.setVisibility(View.INVISIBLE);
            textViewHintLandmark.setEnabled(false);
        } else {
            if (textViewHintLandmark.isEnabled()) {
            } else {
                textViewHintLandmark.setVisibility(View.VISIBLE);
                textViewHintLandmark.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_instructions, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedInstructions(CharSequence text) {
        if (text.length() == 0) {
            textViewHintInstraction.setVisibility(View.INVISIBLE);
            textViewHintInstraction.setEnabled(false);
        } else {
            if (textViewHintInstraction.isEnabled()) {
            } else {
                textViewHintInstraction.setVisibility(View.VISIBLE);
                textViewHintInstraction.setEnabled(true);
            }
        }
    }

    @Override
    public void addNewAddressSuccessful() {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        Toast.makeText(this, "Set Location successfully", Toast.LENGTH_SHORT).show();

       Intent intentSingup = new Intent(this, ActivityHome.class);
       Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
       startActivity(intentSingup, bndlanimation);
       finish();


    }

    @Override
    public void addNewAddressFail(String msg) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();


        new AlertDialog.Builder(this)
                .setTitle("Current Location")
                .setMessage(msg)
                .setPositiveButton("Re-Try", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            proprogressview.setVisibility(View.VISIBLE);
                            bloackUserInteraction();

                            if (edittextAddresName.getText().toString().isEmpty() || edittextAddresName.getText().toString().equals("")) {
                                Toast.makeText(ActivityMenualAddress.this, "Address Name is empty", Toast.LENGTH_SHORT).show();
                            } else if (edittextAddresCity.getText().toString().isEmpty() || edittextAddresCity.getText().toString().equals("")) {
                                Toast.makeText(ActivityMenualAddress.this, "Address City is empty", Toast.LENGTH_SHORT).show();
                            } else if (edittextAddresMainraod.getText().toString().isEmpty() || edittextAddresMainraod.getText().toString().equals("")) {
                                Toast.makeText(ActivityMenualAddress.this, "Address Main Road is empty", Toast.LENGTH_SHORT).show();
                            } else if (edittextAddresNumber.getText().toString().isEmpty() || edittextAddresNumber.getText().toString().equals("")) {
                                Toast.makeText(ActivityMenualAddress.this, "Address Number is empty", Toast.LENGTH_SHORT).show();
                            } else {
                                if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                    proprogressview.setVisibility(View.VISIBLE);
                                    bloackUserInteraction();

                                    addressMenualPresenter.addNewAddress(addAddressDetailsToList());
                                } else {
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityMenualAddress.this);
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

                            }


                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                        }


                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();


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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 10));
        mMap.getUiSettings().setMyLocationButtonEnabled(false);


        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(false);
        }


    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
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

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


}
