package emerge.project.onmeal.ui.activity.landingsetlocation;


import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.luseen.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class SetLocationInteractorImpil implements SetLocationInteractor {

    Realm realm = Realm.getDefaultInstance();
    @Override
    public void getSellectedAddressDetails(String name, String address, LatLng latLng, OnGetSellectedAddressDetailsFinishedListener onGetSellectedAddressDetailsFinishedListener) {


        AddressItems addressItems = new AddressItems();


        String mapAddressNum = "", mapCity = "", mapMainroad = "", mapSubroad = "",mapCompanyName = "";

        String planeaddress = address.replace(",,", ",");

        

        String[] separated = planeaddress.split(",");
        String[] separatedNum = separated[0].split(" ");

        if (separatedNum.length == 1) {
            if (separatedNum[0].matches(".*[0-9].*")) {
                mapAddressNum = separatedNum[0].trim();
                mapSubroad = "";
            } else {
                mapAddressNum = "";
                mapSubroad = separatedNum[0].trim();
            }
        } else {
            if (separatedNum[0].matches(".*[0-9].*")) {
                mapAddressNum = separatedNum[0].trim();
            } else {
                mapSubroad = separated[0].trim();
            }
        }

        if (separated.length <= 3) {
            mapCity = separated[1].trim();
        } else {
            if (separated.length <= 4) {
                mapMainroad = separated[1].trim();
                mapCity = separated[2].trim();
            } else {
                mapSubroad = separated[1].trim();
                mapMainroad = separated[2].trim();
                mapCity = separated[3].trim();
            }
        }



        addressItems.setAddressName("");
        addressItems.setAddressNumber("");
        addressItems.setAddressCity("");

        addressItems.setAddress(address);
        addressItems.setAddressNumber(mapAddressNum);
        addressItems.setAddressCity(mapCity);
        addressItems.setMainRoad(mapMainroad);
        addressItems.setAddressCompanyName(mapCompanyName);
        addressItems.setSubRoad(mapSubroad);
        addressItems.setAddressLatitude(latLng.latitude);
        addressItems.setAddressLongitude(latLng.longitude);
        addressItems.setAddressCompanyDepartment("");
        addressItems.setAddressLandmark("");
        addressItems.setAddressDeliveryInstructions("");



        onGetSellectedAddressDetailsFinishedListener.selectedAddressDetails(addressItems);
        
        
    }
    @Override
    public void signOut(Context context, final OnsignOutinishedListener onsignOutinishedListener) {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> resultsAddress = realm.where(User.class).findAll();
                resultsAddress.deleteAllFromRealm();


                onsignOutinishedListener.signOutSuccess();
            }
        });

    }

}
