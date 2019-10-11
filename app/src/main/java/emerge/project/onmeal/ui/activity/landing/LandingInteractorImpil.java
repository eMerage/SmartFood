package emerge.project.onmeal.ui.activity.landing;


import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.luseen.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
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

public class LandingInteractorImpil implements LandingInteractor {

    Realm realm = Realm.getDefaultInstance();

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    String newAddressRespons;
    List<AddressItems> addressList;

    @Override
    public void getSellectedAddressDetails(String planeName, String address, LatLng latLng, OnGetSellectedAddressDetailsFinishedListener onGetSellectedAddressDetailsFinishedListener) {
        if (latLng.longitude == 0.0) {
            onGetSellectedAddressDetailsFinishedListener.selectedAddressDetailsFail();
        } else {
            AddressItems addressItems = new AddressItems();
            try {


                String mapAddressNum = "", mapCity = "", mapMainroad = "", mapSubroad = "", mapCompanyName;
                String planeaddress = address.replace(",,", ",");

                if ((planeName.contains("°")) || (planeName.contains("'")) || (planeName.contains("."))) {
                    mapCompanyName = "";
                } else {
                    mapCompanyName = planeName.trim();
                }

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

            } catch (ArrayIndexOutOfBoundsException aiobex) {

                addressItems.setAddressName("");
                addressItems.setAddressNumber("");
                addressItems.setAddressCity("");

                addressItems.setAddress(address);
                addressItems.setAddressNumber("");
                addressItems.setAddressCity("");
                addressItems.setMainRoad("");
                addressItems.setAddressCompanyName("");
                addressItems.setSubRoad("");
                addressItems.setAddressLatitude(latLng.latitude);
                addressItems.setAddressLongitude(latLng.longitude);
                addressItems.setAddressCompanyDepartment("");
                addressItems.setAddressLandmark("");
                addressItems.setAddressDeliveryInstructions("");


            }


            onGetSellectedAddressDetailsFinishedListener.selectedAddressDetails(addressItems);

        }


    }

    @Override
    public void getAddress(final OnAddressLoadFinishedListener onAddressLoadFinishedListener) {
        final ArrayList<AddressItems> addressItemsArrayList = new ArrayList<AddressItems>();


        User user = realm.where(User.class).findFirst();

        try {
            apiService.getAddress(Integer.parseInt(user.getUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<AddressItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<AddressItems> respond) {
                            addressList = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onAddressLoadFinishedListener.getAddressFail(String.valueOf(R.string.server_error_msg));
                        }
                        @Override
                        public void onComplete() {
                            if (addressList != null) {
                                try {
                                    if (addressList.isEmpty()) {
                                        onAddressLoadFinishedListener.getAddressEmpty();
                                    } else {
                                        for (int i = 0; i < addressList.size(); i++) {
                                            if (addressList.get(i).getAddressName().equals("") || addressList.get(i).getAddressName().isEmpty()) {
                                                                //didn't show the address which doesn't have address name
                                            } else {
                                                addressItemsArrayList.add(new AddressItems(addressList.get(i).getAddressId(),
                                                        addressList.get(i).getAddressName(), addressList.get(i).getAddressCity(), addressList.get(i).getAddressNumber(), addressList.get(i).getAddressRoad(), false));
                                            }
                                        }
                                        onAddressLoadFinishedListener.getAddressSuccessful(addressItemsArrayList);
                                    }

                                } catch (NullPointerException exNull) {
                                    onAddressLoadFinishedListener.getAddressFail(String.valueOf(R.string.server_error_msg));
                                }


                            } else {
                                onAddressLoadFinishedListener.getAddressFail(String.valueOf(R.string.server_error_msg));
                            }

                        }
                    });

        } catch (Exception ex) {
            onAddressLoadFinishedListener.getAddressFail(String.valueOf(R.string.server_error_msg));
        }
    }

    @Override
    public void addNewAddress(AddressItems addressItems, final OnAddNewAddressFinishedListener onAddNewAddressFinishedListener) {

        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();
        JsonObject jsonObject = new JsonObject();

        final String fullAddress = addressItems.getAddressNumber() + " " + addressItems.getMainRoad() + addressItems.getSubRoad() + " " + addressItems.getAddressCity();

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
                            onAddNewAddressFinishedListener.addNewAddressFail(String.valueOf(R.string.server_error_msg));
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
                                    onAddNewAddressFinishedListener.addNewAddressFail(String.valueOf(R.string.server_error_msg));
                                }
                            } else {
                                onAddNewAddressFinishedListener.addNewAddressFail(String.valueOf(R.string.server_error_msg));
                            }
                        }
                    });

        } catch (Exception ex) {
            onAddNewAddressFinishedListener.addNewAddressFail(String.valueOf(R.string.server_error_msg));
        }
    }

    @Override
    public void saveAddress(final String addresID, final String selectedAddress, OnsaveAddressFinishedListener onsaveAddressFinishedListener) {

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                final Long addressList = bgRealm.where(Address.class).count();
                if (addressList.intValue() == 0) {
                    Address address = bgRealm.createObject(Address.class, 1);
                    address.setAddressId(addresID);
                    address.setAddress(selectedAddress);
                } else {
                    Address address = bgRealm.where(Address.class).equalTo("rowId", 1).findFirst();
                    address.setAddressId(addresID);
                    address.setAddress(selectedAddress);
                }

            }
        });

        onsaveAddressFinishedListener.saveAddressSuccessful(selectedAddress);


    }

    @Override
    public void deleteAddress() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmResults<Address> resultsAddress = bgRealm.where(Address.class).findAll();
                resultsAddress.deleteAllFromRealm();

                RealmResults<CartDetail> resultsCartDetail = bgRealm.where(CartDetail.class).findAll();
                resultsCartDetail.deleteAllFromRealm();

                RealmResults<CartHeader> resultsCartHeader = bgRealm.where(CartHeader.class).findAll();
                resultsCartHeader.deleteAllFromRealm();

                RealmResults<MenuSubItems> resultsMenuSubItems = bgRealm.where(MenuSubItems.class).findAll();
                resultsMenuSubItems.deleteAllFromRealm();

            }
        });


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
