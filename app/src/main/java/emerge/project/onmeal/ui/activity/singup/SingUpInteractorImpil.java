package emerge.project.onmeal.ui.activity.singup;


import android.util.Log;

import com.google.gson.JsonObject;
import com.luseen.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class SingUpInteractorImpil implements SingUpInteractor {


    private static final String TAG = "SingUpInteractorImpil";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    JsonObject registrationResponse;

    @Override
    public void checkEmailValidation(String email, OnSingInEmailValidationFinishedListener onSingInEmailValidationFinishedListener) {
        Pattern valliEmailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = valliEmailPattern.matcher(email);

        if ((email == null) || (email.isEmpty()) || (email.equals(""))) {
            onSingInEmailValidationFinishedListener.emailAddressEmpty();
        } else if (!matcher.find()) {
            onSingInEmailValidationFinishedListener.emailAddressInvalid();
        } else {
            onSingInEmailValidationFinishedListener.emailAddressValid();
        }

    }

    @Override
    public void checkFullNameValidation(String fullName, OnFullNameValidationFinishedListener onFullNameValidationFinishedListener) {
        if ((fullName == null) || (fullName.isEmpty()) || (fullName.equals(""))) {
            onFullNameValidationFinishedListener.fullNameEmpty();
        } else if (fullName.matches(".*[0-9].*")) {
            onFullNameValidationFinishedListener.fullNameInvalid("Name cannot contain numbers !");
        } else if (fullName.matches(".*[&@!#+].*")) {
            onFullNameValidationFinishedListener.fullNameInvalid("Name cannot contain special character !");
        } else {
            onFullNameValidationFinishedListener.fullNameValid();

        }

    }

    @Override
    public void checkPhoneNumberValidation(String phoneNumber, OnPhoneNumberValidationFinishedListener onPhoneNumberValidationFinishedListener) {
        int phoneNumberByInt;
        try {
            phoneNumberByInt = Integer.parseInt(phoneNumber);
            if (phoneNumber.length() == 10) {
                onPhoneNumberValidationFinishedListener.phoneNumberValid();
            } else {
                onPhoneNumberValidationFinishedListener.phoneNumberInvalid("Must have 10 numbers !");
            }

        } catch (NumberFormatException numberformatexception) {
            Log.w(TAG, numberformatexception);
            if ((phoneNumber == null) || (phoneNumber.isEmpty()) || (phoneNumber.equals(""))) {
                onPhoneNumberValidationFinishedListener.phoneNumberEmpty();
            } else {
                onPhoneNumberValidationFinishedListener.phoneNumberInvalid("Please enter valid phone number !");
            }

        }

    }

    @Override
    public void checkPasswordValidation(String password, String confrimPassword, OnPasswordValidationFinishedListener onPasswordValidationFinishedListener) {
        if ((password == null) || (password.isEmpty()) || (password.equals(""))) {
            onPasswordValidationFinishedListener.passwordEmpty();
        } else if (password.length() < 6) {
            onPasswordValidationFinishedListener.passwordInvalid("Password is too short. Needs to have 6 characters !");
        } else if (password.length() > 12) {
            onPasswordValidationFinishedListener.passwordInvalid("Password is too long. Maximum is 12 characters !");
        } else if ((confrimPassword == null) || (confrimPassword.isEmpty()) || (confrimPassword.equals(""))) {
            onPasswordValidationFinishedListener.confirmPasswordEmpty();
        } else if (!password.matches(confrimPassword)) {
            onPasswordValidationFinishedListener.passwordDoesNotMatch();
        } else {
            onPasswordValidationFinishedListener.passwordValid();
        }

    }

    @Override
    public void singUp(final User user, final OnSingUpListener onSingUpListener) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", user.getUserName());
        jsonObject.addProperty("Password", user.getUserPassword());
        jsonObject.addProperty("userLoginTypeCode", user.getSocialMediaType());
        jsonObject.addProperty("userTypeCode", user.getUserTypeID());
        jsonObject.addProperty("gender", user.getUserGender());
        jsonObject.addProperty("eMail", user.getUserEmail());
        jsonObject.addProperty("mobileNo", user.getUserPhoneNumber());
        jsonObject.addProperty("name", user.getuName());
        jsonObject.addProperty("SocialMediaTokenId", user.getUserSocialMediaTokenId());
        jsonObject.addProperty("PushTokenId", user.getUserPushTokenId());

        try {
            apiService.userRegistration(jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JsonObject>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(JsonObject response) {
                            registrationResponse = response;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onSingUpListener.singUpFail("Communication error, Please try again");
                        }

                        @Override
                        public void onComplete() {
                            if (registrationResponse != null) {
                                try {
                                    JSONObject registration = null;
                                    registration = new JSONObject(registrationResponse.toString());
                                    onSingUpListener.singUpSuccess(registration.getString("id"), user.getUserPhoneNumber(), registration.getString("verificationCode"));
                                } catch (NullPointerException exNull) {
                                    onSingUpListener.singUpFail("Communication error, Please try again");
                                } catch (JSONException e) {
                                    onSingUpListener.singUpFail("Communication error, Please try again");
                                    Logger.e(e.toString());
                                }
                            } else {
                                onSingUpListener.singUpFail("Communication error, Please try again");
                            }

                        }
                    });

        } catch (Exception ex) {
            onSingUpListener.singUpFail("Communication error, Please try again");
        }


     /*   try {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            final Call<JsonObject> call = apiService.userRegistration(jsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JSONObject registrationResponse = null;
                    if (response.isSuccessful()) {
                        try {
                            registrationResponse = new JSONObject(response.body().toString());
                            onSingUpListener.singUpSuccess(registrationResponse.getString("id"), user.getUserPhoneNumber(), registrationResponse.getString("verificationCode"));

                        } catch (NullPointerException exNull) {
                            onSingUpListener.singUpFail("Communication error, Please try again");
                        } catch (JSONException e) {
                            onSingUpListener.singUpFail("Communication error, Please try again");
                            Logger.e(e.toString());
                        }
                    } else {
                        onSingUpListener.singUpFail("Communication error, Please try again");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    onSingUpListener.singUpFail("Communication error, Please try again");
                    Logger.e(t.toString());
                }
            });
        } catch (Exception ex) {
            onSingUpListener.singUpFail("Communication error, Please try again");
        }*/

    }


}
