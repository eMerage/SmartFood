package emerge.project.onmeal.ui.fragment.profile.address;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.ui.activity.landingsetlocation.SetLocationInteractor;
import emerge.project.onmeal.ui.activity.landingsetlocation.SetLocationInteractorImpil;
import emerge.project.onmeal.ui.activity.landingsetlocation.SetLocationPresenter;
import emerge.project.onmeal.ui.activity.landingsetlocation.SetLocationView;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class ProfileAddressPresenterImpli implements ProfileAddressPresenter,
        ProfileAddressInteractor.OnAddressLoadFinishedListener,
ProfileAddressInteractor.OnDeleteAddressFinishedListener{


    private ProfileAddressView profileAddressView;
    ProfileAddressInteractor profileAddressInteractor;


    public ProfileAddressPresenterImpli(ProfileAddressView setlocationView) {
        this.profileAddressView = setlocationView;
        this.profileAddressInteractor = new ProfileAddressInteractorImpil();

    }



    @Override
    public void getAddress() {
        profileAddressInteractor.getAddress(this);
    }



    @Override
    public void getAddressEmpty() {
        profileAddressView.getAddressEmpty();
    }

    @Override
    public void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList) {
        profileAddressView.getAddressSuccessful(addressItemsArrayList);
    }

    @Override
    public void getAddressFail(String msg) {
        profileAddressView.getAddressFail(msg);
    }





    @Override
    public void deleteAddress(String addressID) {
        profileAddressInteractor.deleteAddress(addressID,this);
    }

    @Override
    public void deleteAddressStart() {
        profileAddressView.deleteAddressStart();
    }

    @Override
    public void deleteAddressSuccessful() {
        profileAddressView.deleteAddressSuccessful();
    }

    @Override
    public void deleteAddressFail(String addressID, String msg) {
        profileAddressView.deleteAddressFail(addressID,msg);
    }
}
