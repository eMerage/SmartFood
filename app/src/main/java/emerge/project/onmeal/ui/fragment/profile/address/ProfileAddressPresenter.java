package emerge.project.onmeal.ui.fragment.profile.address;


import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProfileAddressPresenter {

    void getAddress();
    void deleteAddress(String addressID);

}
