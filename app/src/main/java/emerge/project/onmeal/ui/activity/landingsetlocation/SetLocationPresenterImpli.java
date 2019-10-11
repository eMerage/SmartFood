package emerge.project.onmeal.ui.activity.landingsetlocation;


import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.ui.activity.landing.LandingInteractorImpil;
import emerge.project.onmeal.ui.activity.landing.LandingPresenter;
import emerge.project.onmeal.ui.activity.landing.LandingView;
import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SetLocationPresenterImpli implements
        SetLocationPresenter,
        SetLocationInteractor.OnGetSellectedAddressDetailsFinishedListener,
        SetLocationInteractor.OnsignOutinishedListener{


    private SetLocationView setLocationView;
    SetLocationInteractor setLocationInteractor;


    public SetLocationPresenterImpli(SetLocationView setlocationView) {
        this.setLocationView = setlocationView;
        this.setLocationInteractor = new SetLocationInteractorImpil();

    }


    @Override
    public void signOut(Context context) {
        setLocationInteractor.signOut(context,this);
    }


    @Override
    public void signOutSuccess() {
        setLocationView.signOutSuccess();
    }


    @Override
    public void getSellectedAddressDetails(String name, String address, LatLng latLng) {
        setLocationInteractor.getSellectedAddressDetails(name,address,latLng,this);
    }

    @Override
    public void selectedAddressDetails(AddressItems addressItems) {
        setLocationView.selectedAddressDetails(addressItems);
    }

    @Override
    public void selectedAddressDetailsFail() {
        setLocationView.selectedAddressDetailsFail();
    }
}
