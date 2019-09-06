package emerge.project.onmeal.ui.activity.landingaddressadditianal;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.utils.entittes.AddressItems;

public class ActivityAddressAdditianal extends FragmentActivity implements OnMapReadyCallback, AddressAddingView {


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;
    SupportMapFragment mapFragment;

    @BindView(R.id.proprogressview)
    MKLoader proprogressview;


    @BindView(R.id.robotoMedium0)
    TextView textView0;
    @BindView(R.id.robotoMedium1)
    TextView textView1;
    @BindView(R.id.robotoMedium2)
    TextView textView2;
    @BindView(R.id.robotoMedium3)
    TextView textView3;
    @BindView(R.id.robotoMedium4)
    TextView textView4;
    @BindView(R.id.robotoMedium5)
    TextView textView5;
    @BindView(R.id.robotoMedium6)
    TextView textView6;
    @BindView(R.id.robotoMedium7)
    TextView textView7;


    @BindView(R.id.edittext_addres_name)
    EditText edittextAddresName;

    @BindView(R.id.edittext_addres_subroad)
    EditText edittextAddresSubroad;
    @BindView(R.id.edittext_addres_number)
    EditText edittextAddresNumber;
    @BindView(R.id.edittext_addres_apartment_name)
    EditText edittextAddresApartmentName;
    @BindView(R.id.edittext_addres_floor)
    EditText edittextAddresFloor;
    @BindView(R.id.edittext_addres_department)
    EditText edittextAddresDepartment;
    @BindView(R.id.edittext_addres_landmark)
    EditText edittextAddresLandmark;
    @BindView(R.id.edittext_addres_instructions)
    EditText edittextAddresInstructions;

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



    @BindView(R.id.textView_selectedaddress)
    TextView textViewSelectedAddress;


    private GoogleMap mMap;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    AddressItems addressItem;
    AddressAddingPresenter addressAddingPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_additianal);
        ButterKnife.bind(this);


        addressAddingPresenter = new AddressAddingPresenterImpli(this);

        addressItem = (AddressItems) getIntent().getSerializableExtra("addressItem");
        textViewSelectedAddress.setText(addressItem.getAddress());
        setNavigationMenuItems();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @OnTextChanged(value = R.id.edittext_addres_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedName(CharSequence text) {
        if (text.length() == 0) {
            textView0.setVisibility(View.INVISIBLE);
            textView0.setEnabled(false);
        } else {
            if (textView0.isEnabled()) {
            } else {
                textView0.setVisibility(View.VISIBLE);
                textView0.setEnabled(true);
            }
        }
    }

    @OnTextChanged(value = R.id.edittext_addres_subroad, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedSubroad(CharSequence text) {
        if (text.length() == 0) {
            textView1.setVisibility(View.INVISIBLE);
            textView1.setEnabled(false);
        } else {
            if (textView1.isEnabled()) {
            } else {
                textView1.setVisibility(View.VISIBLE);
                textView1.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_number, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedNumber(CharSequence text) {
        if (text.length() == 0) {
            textView2.setVisibility(View.INVISIBLE);
            textView2.setEnabled(false);
        } else {
            if (textView2.isEnabled()) {
            } else {
                textView2.setVisibility(View.VISIBLE);
                textView2.setEnabled(true);
            }
        }
    }

    @OnTextChanged(value = R.id.edittext_addres_apartment_name, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedApartmentName(CharSequence text) {
        if (text.length() == 0) {
            textView3.setVisibility(View.INVISIBLE);
            textView3.setEnabled(false);
        } else {
            if (textView3.isEnabled()) {
            } else {
                textView3.setVisibility(View.VISIBLE);
                textView3.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_floor, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedFloor(CharSequence text) {
        if (text.length() == 0) {
            textView4.setVisibility(View.INVISIBLE);
            textView4.setEnabled(false);
        } else {
            if (textView4.isEnabled()) {
            } else {
                textView4.setVisibility(View.VISIBLE);
                textView4.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_addres_department, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedDepartment(CharSequence text) {
        if (text.length() == 0) {
            textView5.setVisibility(View.INVISIBLE);
            textView5.setEnabled(false);
        } else {
            if (textView5.isEnabled()) {
            } else {
                textView5.setVisibility(View.VISIBLE);
                textView5.setEnabled(true);
            }
        }
    }

    @OnTextChanged(value = R.id.edittext_addres_landmark, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedLandmark(CharSequence text) {
        if (text.length() == 0) {
            textView6.setVisibility(View.INVISIBLE);
            textView6.setEnabled(false);
        } else {
            if (textView6.isEnabled()) {
            } else {
                textView6.setVisibility(View.VISIBLE);
                textView6.setEnabled(true);
            }
        }
    }

    @OnTextChanged(value = R.id.edittext_addres_instructions, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedInstructions(CharSequence text) {
        if (text.length() == 0) {
            textView7.setVisibility(View.INVISIBLE);
            textView7.setEnabled(false);
        } else {
            if (textView7.isEnabled()) {
            } else {
                textView7.setVisibility(View.VISIBLE);
                textView7.setEnabled(true);
            }
        }
    }


    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.imageView_add)
    public void onClickConfrim(View view) {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            bloackUserInteraction();
            addressAddingPresenter.addNewAddress(addAddressDetailsToList());
        } else {
            Toast.makeText(this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }

    }


    private AddressItems addAddressDetailsToList() {
        AddressItems userAddressItems = new AddressItems();

        userAddressItems.setAddressName(edittextAddresName.getText().toString());
        userAddressItems.setAddressCity(addressItem.getAddressCity());
        userAddressItems.setMainRoad(addressItem.getMainRoad());
        userAddressItems.setSubRoad(edittextAddresSubroad.getText().toString());
        userAddressItems.setAddressNumber(edittextAddresNumber.getText().toString());
        userAddressItems.setAddressFloor(edittextAddresFloor.getText().toString());
        userAddressItems.setAddressApartmentName(edittextAddresApartmentName.getText().toString());
        userAddressItems.setAddressLandmark(edittextAddresLandmark.getText().toString());
        userAddressItems.setAddressCompanyName(addressItem.getAddressCompanyName());
        userAddressItems.setAddressCompanyDepartment(edittextAddresDepartment.getText().toString());
        userAddressItems.setAddressDeliveryInstructions(edittextAddresInstructions.getText().toString());
        userAddressItems.setAddressLatitude(addressItem.getAddressLatitude());
        userAddressItems.setAddressLongitude(addressItem.getAddressLongitude());

        return userAddressItems;
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean success = mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map));
        getLocationPermission();
        LatLng curentPlace = new LatLng(addressItem.getAddressLatitude(), addressItem.getAddressLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(curentPlace, 15));

    }


    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void addNewAddressSuccessful() {
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Address added successfully", Toast.LENGTH_LONG).show();
        unBloackUserInteraction();
        finish();
    }

    @Override
    public void addNewAddressFail(String msg) {
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        unBloackUserInteraction();
    }
    private void setNavigationMenuItems(){
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

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
