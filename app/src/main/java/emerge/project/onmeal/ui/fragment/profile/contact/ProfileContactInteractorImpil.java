package emerge.project.onmeal.ui.fragment.profile.contact;


import com.luseen.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressInteractor;
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

public class ProfileContactInteractorImpil implements ProfileContactInteractor {

    Realm realm = Realm.getDefaultInstance();

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    User getUserDetails;
    int updateUserDetails=-1;


    @Override
    public void getUserDetails(final OnGetUserDetailsFinishedListener onGetUserDetailsFinishedListener) {
        try {
            User user = realm.where(User.class).findFirst();
            apiService.getUserByID(Integer.parseInt(user.getUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(User userList) {
                            getUserDetails = userList;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onGetUserDetailsFinishedListener.getUserDetailsFail("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (getUserDetails != null) {
                                try {
                                    onGetUserDetailsFinishedListener.getUserDetailsSuccessful(getUserDetails);
                                } catch (NullPointerException exNull) {
                                    onGetUserDetailsFinishedListener.getUserDetailsFail("Communication error, Please try again");
                                }
                            } else {
                                onGetUserDetailsFinishedListener.getUserDetailsFail("Communication error, Please try again");
                            }
                        }
                    });
        } catch (Exception ex) {
            onGetUserDetailsFinishedListener.getUserDetailsFail("Communication error, Please try again");
        }
    }

    @Override
    public void updateUserDetails(String gender, String dob, final OnUpdateUserDetailsFinishedListener onUpdateUserDetailsFinishedListener) {

        if (gender.equals("") && dob.equals("")) {
            onUpdateUserDetailsFinishedListener.emptyInputs();
        } else if (validGender(gender)) {
            onUpdateUserDetailsFinishedListener.incorrectGenderFormat();
        } else if (validateJavaDate(dob)) {
            onUpdateUserDetailsFinishedListener.incorrectDOBFormat();
        } else {
            try {
                realm = Realm.getDefaultInstance();
                User user = realm.where(User.class).findFirst();
                apiService.updateMealTimeUser(Integer.parseInt(user.getUserId()), gender.toUpperCase(), convertDateFormat(dob))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Integer respond) {
                                updateUserDetails = respond;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onUpdateUserDetailsFinishedListener.getUpdateFail("Communication error, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (updateUserDetails != -1) {
                                    try {
                                        if (updateUserDetails == 1) {
                                            onUpdateUserDetailsFinishedListener.getUpdateSuccessful();
                                        } else {
                                            onUpdateUserDetailsFinishedListener.getUpdateFail("User Detail Update Fail, Please try again");
                                        }
                                    } catch (NullPointerException exNull) {
                                        onUpdateUserDetailsFinishedListener.getUpdateFail("Communication error, Please try again");
                                    }

                                } else {
                                    onUpdateUserDetailsFinishedListener.getUpdateFail("Communication error, Please try again");
                                }
                            }
                        });
            } catch (Exception ex) {
                onUpdateUserDetailsFinishedListener.getUpdateFail("Communication error, Please try again");
            }
        }


    }

    private boolean validGender(String gender) {

        if (gender.equalsIgnoreCase("F")) {
            return false;
        } else if (gender.equalsIgnoreCase("M")) {
            return false;
        } else if (gender.equalsIgnoreCase("O")) {
            return false;
        } else if (gender.isEmpty() || gender.equals("")) {
            return false;
        } else {
            return true;
        }


    }


    private String convertDateFormat(String userDate) {

        if (userDate.trim().equals("")) {
            return "";
        } else {
            String date = "";
            String[] spitDateOne = userDate.split("/");

            if (spitDateOne[0].length() == 1) {
                date = date + "0" + spitDateOne[0];
            } else {
                date = date + spitDateOne[0];
            }

            if (spitDateOne[1].length() == 1) {
                date = date + "/" + "0" + spitDateOne[1];
            } else {
                date = date + "/" + spitDateOne[1];
            }
            date = date + "/" + spitDateOne[2];

            return date;
        }


    }

    private boolean validateJavaDate(String strDate) {

        if (strDate.trim().equals("")) {
            return false;
        } else {

            SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy/MM/dd");
            sdfrmt.setLenient(false);
            try {
                Date javaDate = sdfrmt.parse(strDate);

                String[] spitDateOne = strDate.split("/");

                if (Integer.parseInt(spitDateOne[2]) > (Calendar.getInstance().get(Calendar.YEAR) - 1)) {
                    return true;
                } else {
                    return false;
                }

            } catch (ParseException e) {
                System.out.println(strDate + " is Invalid Date format");
                return true;
            } catch (NumberFormatException numEx) {
                return true;
            }
        }


    }


}
