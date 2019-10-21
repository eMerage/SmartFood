package emerge.project.onmeal.ui.activity.landingaddressadditianal;


import android.content.Context;

import com.google.gson.JsonObject;
import com.luseen.logger.Logger;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class AddressAddingInteractorImpil implements AddressAddingInteractor {

    Realm realm;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    String newAddressRespons;

    @Override
    public void addNewAddress(AddressItems addressItems, final OnAddNewAddressFinishedListener onAddNewAddressFinishedListener) {

        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();

        JsonObject jsonObject = new JsonObject();

        final String fullAddress = addressItems.getAddressFloor() + " " + addressItems.getAddressApartmentName() + " " + addressItems.getAddressCompanyName();

        jsonObject.addProperty("userAddressID", "0");
        jsonObject.addProperty("AddressName", addressItems.getAddressName());
        jsonObject.addProperty("AddressNo", addressItems.getAddressNumber());
        jsonObject.addProperty("AddressRoad", addressItems.getMainRoad() + addressItems.getSubRoad());
        jsonObject.addProperty("Address", fullAddress);
        jsonObject.addProperty("city", addressItems.getAddressCity());
        jsonObject.addProperty("cityID", 5);
        jsonObject.addProperty("districtID", 1);
        jsonObject.addProperty("countryID", 1);
        jsonObject.addProperty("latitude", addressItems.getAddressLatitude());
        jsonObject.addProperty("longitude", addressItems.getAddressLongitude());
        jsonObject.addProperty("contactName", "");
        jsonObject.addProperty("contactNo", "");
        jsonObject.addProperty("id", Integer.parseInt(user.getUserId()));
        jsonObject.addProperty("DepartmentName", addressItems.getAddressCompanyDepartment());
        jsonObject.addProperty("LandMark", addressItems.getAddressLandmark());
        jsonObject.addProperty("DeliveryInstruction", addressItems.getAddressDeliveryInstructions());
        try {
            apiService.addNewAddress(jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String response) {
                            newAddressRespons = response;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onAddNewAddressFinishedListener.addNewAddressFail("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (newAddressRespons != null) {
                                try {
                                    if (newAddressRespons.equals("0")) {
                                        onAddNewAddressFinishedListener.addNewAddressFail("Address adding fail,please try again");
                                    } else {
                                        addAddress(newAddressRespons, fullAddress, onAddNewAddressFinishedListener);
                                    }
                                } catch (NullPointerException exNull) {
                                    onAddNewAddressFinishedListener.addNewAddressFail("Communication error, Please try again");
                                }
                            } else {
                                onAddNewAddressFinishedListener.addNewAddressFail("Communication error, Please try again");
                            }

                        }
                    });
        } catch (Exception ex) {
            onAddNewAddressFinishedListener.addNewAddressFail("Communication error, Please try again");
        }

    }


    public void addAddress(final String addressId, final String fullAddress, final OnAddNewAddressFinishedListener onAddNewAddressFinishedListener) {

        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                final Long addressList = bgRealm.where(Address.class).count();
                if (addressList.intValue() == 0) {
                    Address address = bgRealm.createObject(Address.class, 1);
                    address.setAddressId(addressId);
                    address.setAddress(fullAddress);
                } else {
                    Address address = bgRealm.where(Address.class).equalTo("rowId", 1).findFirst();
                    address.setAddressId(addressId);
                    address.setAddress(fullAddress);
                }
                onAddNewAddressFinishedListener.addNewAddressSuccessful();
            }
        });


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
