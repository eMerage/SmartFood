package emerge.project.onmeal.ui.fragment.profile.address;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProfileAddressInteractor {

    interface OnAddressLoadFinishedListener {
        void getAddressEmpty();
        void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList);
        void getAddressFail(String msg);
    }
    void getAddress(OnAddressLoadFinishedListener onAddressLoadFinishedListener);



    interface OnDeleteAddressFinishedListener {
        void deleteAddressStart();
        void deleteAddressSuccessful();
        void deleteAddressFail(String addressID,String msg);
    }
    void deleteAddress(String addressID,OnDeleteAddressFinishedListener onDeleteAddressFinishedListener);


}
