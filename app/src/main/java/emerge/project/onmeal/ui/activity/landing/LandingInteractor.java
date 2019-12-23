package emerge.project.onmeal.ui.activity.landing;



import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.AddressItems;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LandingInteractor {

    interface OnGetSellectedAddressDetailsFinishedListener {
        void selectedAddressDetails(AddressItems addressItems);
        void selectedAddressDetailsFail();
    }
    void getSellectedAddressDetails(String name, String address,LatLng latLng, OnGetSellectedAddressDetailsFinishedListener onGetSellectedAddressDetailsFinishedListener);



    interface OnAddressLoadFinishedListener {
        void getAddressEmpty();
        void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList);
        void getAddressFail(String msg);
    }
    void getAddress(OnAddressLoadFinishedListener  onAddressLoadFinishedListener);


    interface OnAddNewAddressFinishedListener {
        void addNewAddressSuccessful();
        void addNewAddressFail(String msg);
    }
    void addNewAddress(AddressItems addressItems, OnAddNewAddressFinishedListener onAddNewAddressFinishedListener);



    interface OnsaveAddressFinishedListener {
        void saveAddressSuccessful(String add);
        void saveAddressFail(String msg);
    }
    void saveAddress(String addresID,String address, OnsaveAddressFinishedListener onsaveAddressFinishedListener);

    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context, OnsignOutinishedListener onsignOutinishedListener);




    void deleteAddress();

}
