package emerge.project.onmeal.ui.fragment.profile.address;


import com.google.android.gms.maps.model.LatLng;
import com.luseen.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.landing.LandingInteractor;
import emerge.project.onmeal.ui.activity.landingsetlocation.SetLocationInteractor;
import emerge.project.onmeal.utils.entittes.AddressItems;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class ProfileAddressInteractorImpil implements ProfileAddressInteractor {

    Realm  realm = Realm.getDefaultInstance();
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    List<AddressItems> addressList;
    int deleteAddress=-1;


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
    public void deleteAddress(final String addressID, final OnDeleteAddressFinishedListener onDeleteAddressFinishedListener) {
        onDeleteAddressFinishedListener.deleteAddressStart();
        int addressid = 0;
        try {
            addressid = Integer.parseInt(addressID);
        } catch (NumberFormatException numexp) {

        }

        try {
            apiService.deleteMealTimeUserAddress(addressid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Integer respond) {
                            deleteAddress = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onDeleteAddressFinishedListener.deleteAddressFail(addressID, String.valueOf(R.string.server_error_msg));
                        }

                        @Override
                        public void onComplete() {
                            if (deleteAddress != -1) {
                                try {
                                    if (deleteAddress == 0) {
                                        onDeleteAddressFinishedListener.deleteAddressSuccessful();
                                    } else {
                                        onDeleteAddressFinishedListener.deleteAddressFail(addressID, "Address Deleting Fail, Please try again");
                                    }
                                } catch (NullPointerException exNull) {
                                    onDeleteAddressFinishedListener.deleteAddressFail(addressID, String.valueOf(R.string.server_error_msg));
                                }

                            } else {
                                onDeleteAddressFinishedListener.deleteAddressFail(addressID, String.valueOf(R.string.server_error_msg));
                            }
                        }
                    });

        } catch (Exception ex) {
            onDeleteAddressFinishedListener.deleteAddressFail(addressID, String.valueOf(R.string.server_error_msg));
        }
    }
}
