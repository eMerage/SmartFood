package emerge.project.onmeal.ui.activity.landing;


import com.google.android.gms.maps.model.LatLng;

import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LandingPresenter {
    void getSellectedAddressDetails(String name,String address,LatLng latLng);
    void getAddress();

    void addNewAddress(AddressItems addressItems);

    void saveAddress(String addresID,String address);

    void deleteAddress();

}
