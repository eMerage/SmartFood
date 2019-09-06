package emerge.project.onmeal.ui.activity.landingaddressmenual;


import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface AddressMeunalInteractor {

    interface OnAddNewAddressFinishedListener {
        void addNewAddressSuccessful();
        void addNewAddressFail(String msg);
    }
    void addNewAddress(AddressItems addressItems, OnAddNewAddressFinishedListener onAddNewAddressFinishedListener);




}
