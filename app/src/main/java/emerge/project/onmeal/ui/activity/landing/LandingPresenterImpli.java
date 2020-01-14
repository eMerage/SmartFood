package emerge.project.onmeal.ui.activity.landing;


import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.UpdateToken;
import emerge.project.onmeal.utils.entittes.VersionUpdate;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class LandingPresenterImpli implements LandingPresenter,
        LandingInteractor.OnGetSellectedAddressDetailsFinishedListener,
        LandingInteractor.OnAddressLoadFinishedListener,
        LandingInteractor.OnAddNewAddressFinishedListener,
        LandingInteractor.OnsaveAddressFinishedListener,
        LandingInteractor.OnsignOutinishedListener
{


    private LandingView landingView;
    LandingInteractor landingInteractor;


    public LandingPresenterImpli(LandingView landingView) {
        this.landingView = landingView;
        this.landingInteractor = new LandingInteractorImpil();

    }


    @Override
    public void getSellectedAddressDetails(String name, String address,LatLng latLng) {
        landingInteractor.getSellectedAddressDetails(name,address,latLng,this);
    }


    @Override
    public void signOut(Context context) {
        landingInteractor.signOut(context,this);
    }


    @Override
    public void signOutSuccess() {
        landingView.signOutSuccess();
    }


    @Override
    public void selectedAddressDetails(AddressItems addressItems) {
        landingView.selectedAddressDetails(addressItems);
    }

    @Override
    public void selectedAddressDetailsFail() {
        landingView.selectedAddressDetailsFail();
    }


    @Override
    public void getAddress() {
        landingInteractor.getAddress(this);
    }



    @Override
    public void getAddressEmpty() {
        landingView.getAddressEmpty();
    }

    @Override
    public void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList) {
        landingView.getAddressSuccessful(addressItemsArrayList);
    }

    @Override
    public void getAddressFail(String msg) {
        landingView.getAddressFail(msg);
    }




    @Override
    public void addNewAddress(AddressItems addressItems) {
        landingInteractor.addNewAddress(addressItems,this);
    }



    @Override
    public void addNewAddressSuccessful() {
        landingView.addNewAddressSuccessful();
    }

    @Override
    public void addNewAddressFail(String msg) {
        landingView.addNewAddressFail(msg);
    }


    @Override
    public void saveAddress(AddressItems addressItems) {
        landingInteractor.saveAddress(addressItems,this);
    }

    @Override
    public void deleteAddress() {
        landingInteractor.deleteAddress();
    }


    @Override
    public void saveAddressSuccessful(AddressItems add) {
        landingView.saveAddressSuccessful( add);
    }

    @Override
    public void saveAddressFail(String msg) {
        landingView.saveAddressFail(msg);
    }




}
