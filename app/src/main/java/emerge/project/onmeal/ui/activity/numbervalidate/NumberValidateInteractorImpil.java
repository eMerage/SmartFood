package emerge.project.onmeal.ui.activity.numbervalidate;


import com.google.gson.JsonObject;
import com.luseen.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.List;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;

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

public class NumberValidateInteractorImpil implements NumberValidateInteractor {

    Realm realm;
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    User getUserById;
    JsonObject validateOTPCode;
    JsonObject newOTPCode;

    @Override
    public void validateOTPCode(String codeFromUserReg, String codeFromSMS, final String userId, final OnOTPCodeValidationFinishedListener onOTPCodeValidationFinishedListener) {
        if (codeFromUserReg.length() != 4) {
            onOTPCodeValidationFinishedListener.oTPCodeMissing();
        } else if (codeFromUserReg.equals(codeFromSMS)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", Integer.parseInt(userId));
            jsonObject.addProperty("VerificationCode", Integer.parseInt(codeFromSMS));
            try {
                apiService.userVerified(jsonObject)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JsonObject>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(JsonObject response) {
                                validateOTPCode = response;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
                            }

                            @Override
                            public void onComplete() {
                                if (validateOTPCode != null) {
                                    JSONObject userVerifiedResponse = null;
                                    try {
                                        userVerifiedResponse = new JSONObject(validateOTPCode.toString());
                                        if (userVerifiedResponse.getString("status").equals("Successful")) {
                                            getUserById(userId, onOTPCodeValidationFinishedListener);
                                        } else {
                                            onOTPCodeValidationFinishedListener.oTPCodeExpired(userVerifiedResponse.getString("VerificationCode"));
                                            Logger.e("Code Expired");
                                        }
                                    } catch (JSONException e) {
                                        onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
                                        Logger.e(e.toString());
                                    } catch (NullPointerException exNull) {
                                        onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
                                    }

                                } else {
                                    onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
                                }
                            }
                        });

            } catch (Exception ex) {
                onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
            }

        } else {
            onOTPCodeValidationFinishedListener.oTPCodeInvalid();

        }

    }

    @Override
    public void getNewOTPCode(final String userId, final OnNewOTPCodeFinishedListener onNewOTPCodeFinishedListener) {


        try {
            apiService.getNewValidationCode(Integer.parseInt(userId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject response) {
                            newOTPCode = response;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onNewOTPCodeFinishedListener.newOTPCodeServerError("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (newOTPCode != null) {
                                JSONObject userVerifiedResponse = null;
                                try {
                                    userVerifiedResponse = new JSONObject(newOTPCode.toString());
                                    onNewOTPCodeFinishedListener.newOTPCode(userVerifiedResponse.getString("verificationCode"));
                                } catch (JSONException e) {
                                    onNewOTPCodeFinishedListener.newOTPCodeServerError("Communication error, Please try again");
                                    Logger.e(e.toString());
                                } catch (NullPointerException exNull) {
                                    onNewOTPCodeFinishedListener.newOTPCodeServerError("Communication error, Please try again");
                                }
                            } else {
                                onNewOTPCodeFinishedListener.newOTPCodeServerError("Communication error, Please try again");
                            }

                        }
                    });


        } catch (Exception ex) {
            onNewOTPCodeFinishedListener.newOTPCodeServerError("Communication error, Please try again");
        }

    }

    @Override
    public void messageReceived(String message) {

    }


    private void getUserById(String userId, final OnOTPCodeValidationFinishedListener onOTPCodeValidationFinishedListener) {
        try {
            apiService.getUserByID(Integer.parseInt(userId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(User userList) {
                            getUserById = userList;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (getUserById != null) {
                                try {
                                    saveUser(getUserById, onOTPCodeValidationFinishedListener);
                                } catch (NullPointerException exNull) {
                                    onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
                                }
                            } else {
                                onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
                            }

                        }
                    });
        } catch (Exception ex) {
            onOTPCodeValidationFinishedListener.oTPCodeServerError("Communication error, Please try again");
        }


    }


    private void saveUser(final User userDetails, final OnOTPCodeValidationFinishedListener onOTPCodeValidationFinishedListener) {

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                final Long userList = bgRealm.where(User.class).count();

                User user = bgRealm.createObject(User.class, (userList.intValue() + 1));
                user.setUserId(userDetails.getUserId());
                user.setUserName(userDetails.getUserName());
                user.setUserEmail(userDetails.getUserEmail());
                user.setSocialMediaType(userDetails.getSocialMediaType());
                user.setDateOfBirth(userDetails.getDateOfBirth());
                user.setUserPhoneNumber(userDetails.getUserPhoneNumber());
                user.setUserGender(userDetails.getUserGender());

                onOTPCodeValidationFinishedListener.oTPCodeValid();
            }
        });

    }


}
