package emerge.project.onmeal.ui.activity.landingsetlocation;


import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SetLocationInteractor {

    interface OnGetSellectedAddressDetailsFinishedListener {
        void selectedAddressDetails(AddressItems addressItems);
        void selectedAddressDetailsFail();
    }
    void getSellectedAddressDetails(String name, String address,LatLng latLng, OnGetSellectedAddressDetailsFinishedListener onGetSellectedAddressDetailsFinishedListener);


    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context, OnsignOutinishedListener onsignOutinishedListener);




}
