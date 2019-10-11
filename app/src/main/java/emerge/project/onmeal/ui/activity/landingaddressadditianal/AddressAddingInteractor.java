package emerge.project.onmeal.ui.activity.landingaddressadditianal;


import android.content.Context;

import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface AddressAddingInteractor {

    interface OnAddNewAddressFinishedListener {
        void addNewAddressSuccessful();
        void addNewAddressFail(String msg);
    }
    void addNewAddress(AddressItems addressItems, OnAddNewAddressFinishedListener onAddNewAddressFinishedListener);



    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context, OnsignOutinishedListener onsignOutinishedListener);



}
