package emerge.project.onmeal.ui.activity.singin;


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
import emerge.project.onmeal.ui.activity.singup.SingUpInteractor;
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

public class SingInInteractorImpil implements SingInInteractor {

    Realm realm;

    User userValidation;

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void checkLoginValidation(String email, String password, OnLoginValidationFinishedListener onLoginValidationFinishedListener) {
        Pattern valliEmailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = valliEmailPattern.matcher(email);

        if ((email == null) || (email.isEmpty()) || (email.equals(""))) {
            onLoginValidationFinishedListener.emailAddressEmpty();
        } else if (!matcher.find()) {
            onLoginValidationFinishedListener.emailAddressInvalid();
        } else if ((password == null) || (password.isEmpty()) || (password.equals(""))) {
            onLoginValidationFinishedListener.passwordEmpty();
        } else if (password.length() < 6) {
            onLoginValidationFinishedListener.passwordInvalid("Password is too short. Needs to have 6 characters !");
        } else if (password.length() > 12) {
            onLoginValidationFinishedListener.passwordInvalid("Password is too long. Maximum is 12 characters !");
        } else {

            userValidation(email, password, onLoginValidationFinishedListener);

        }
    }

    private void userValidation(final String email, String password, final OnLoginValidationFinishedListener onLoginValidationFinishedListener) {
        try {
            apiService.userLoginValidation(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(User response) {
                            userValidation = response;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onLoginValidationFinishedListener.loginValidationFail(String.valueOf(R.string.server_error_msg));
                        }

                        @Override
                        public void onComplete() {
                            if (userValidation != null) {
                                try {
                                    if (userValidation.getUserName() == null) {
                                        onLoginValidationFinishedListener.loginValidationFail("Invalid login details,Please try again");
                                        Logger.e("Invalid login details,Please try again");
                                    } else {
                                        saveUser(userValidation, onLoginValidationFinishedListener);
                                    }
                                } catch (NullPointerException exNull) {
                                    onLoginValidationFinishedListener.loginValidationFail(String.valueOf(R.string.server_error_msg));
                                }

                            } else {
                                onLoginValidationFinishedListener.loginValidationFail(String.valueOf(R.string.server_error_msg));
                            }

                        }
                    });
            
        } catch (Exception ex) {
            onLoginValidationFinishedListener.loginValidationFail(String.valueOf(R.string.server_error_msg));
        }

    }

    private void saveUser(final User userArrayList, final OnLoginValidationFinishedListener onLoginValidationFinishedListener) {

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                final Long userList = bgRealm.where(User.class).count();

                User user = bgRealm.createObject(User.class, (userList.intValue() + 1));
                user.setUserId(userArrayList.getUserId());
                user.setUserName(userArrayList.getUserName());
                user.setUserEmail(userArrayList.getUserEmail());
                user.setDateOfBirth(userArrayList.getDateOfBirth());
                user.setUserPhoneNumber(userArrayList.getUserPhoneNumber());
                user.setUserGender(userArrayList.getUserGender());

                onLoginValidationFinishedListener.loginValidationSuccessful();
            }
        });


    }

}
