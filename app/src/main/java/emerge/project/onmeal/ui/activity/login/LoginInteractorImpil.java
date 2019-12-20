package emerge.project.onmeal.ui.activity.login;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.JsonObject;
import com.luseen.logger.Logger;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

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
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class LoginInteractorImpil implements LoginInteractor {

    Realm realm;
    EncryptedPreferences encryptedPreferences;
    private static final String PUSH_TOKEN = "pushToken";
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


    User localSingInValidation;
    User facebookSingInValidation;
    User googleSingInValidation;

    @Override
    public void checkLocalSingInValidation(Context context, String email, final OnLocalSingInValidationFinishedListener onLocalSingInValidationFinishedListener) {

        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
        String userPushTokenId = encryptedPreferences.getString(PUSH_TOKEN, "");


        try {

            Pattern valliEmailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = valliEmailPattern.matcher(email);
            if ((email == null) || (email.isEmpty()) || (email.equals(""))) {
                onLocalSingInValidationFinishedListener.loginEmailAddressEmpty();
            } else if (!matcher.find()) {
                onLocalSingInValidationFinishedListener.loginEmailAddressInvalid();
            } else {
                final User user = new User();
                user.setUserEmail(email);

                apiService.getUserByEmail(email, userPushTokenId)
                 .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<User>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(User response) {
                                localSingInValidation = response;
                            }

                            @Override
                            public void onError(Throwable e) {
                                onLocalSingInValidationFinishedListener.localSingInValidationErorr("User Saving Fail.Please try again !");
                            }
                            @Override
                            public void onComplete() {
                                if (localSingInValidation != null) {
                                    try {
                                        if (localSingInValidation.getUserName() == null) {
                                            user.setSocialMediaType("L");
                                            onLocalSingInValidationFinishedListener.emailNotExist(user);
                                        } else if (localSingInValidation.isVerified()) {
                                            if (localSingInValidation.getSocialMediaType().equals("L")) {
                                                onLocalSingInValidationFinishedListener.emailExist(user);
                                            } else {
                                                saveUser(localSingInValidation, onLocalSingInValidationFinishedListener);
                                            }
                                        } else {
                                            user.setUserId(localSingInValidation.getUserId());
                                            onLocalSingInValidationFinishedListener.userNotValidateLocal(user);
                                        }
                                    } catch (NullPointerException exNull) {
                                        onLocalSingInValidationFinishedListener.localSingInValidationErorr("User Saving Fail.Please try again !");
                                    }
                                } else {
                                    onLocalSingInValidationFinishedListener.localSingInValidationErorr("User Saving Fail.Please try again !");
                                }

                            }
                        });

            }

        } catch (Exception ex) {
            onLocalSingInValidationFinishedListener.localSingInValidationErorr("Communication error, Please try again");
        }
    }

    @Override
    public void checkFacebookSingInValidation(Context context, JSONObject json, final OnFacebookSingInValidationFinishedListener onFacebookSingInValidationFinishedListener) {
        //login Type
        /* 1 -local     2 -google     3 -facebook*/
        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
        String userPushTokenId = encryptedPreferences.getString(PUSH_TOKEN, "");

        System.out.println("xxxxxxxxxxxxxxxxxxxx checkFacebookSingInValidation: "+json);

        try {

            System.out.println("xxxxxxxxxxxxxxxxxxxx : "+json.getString("email"));


            final String emailAddress = json.getString("email");
            final String userName = json.getString("name");
            final String userSocialMediaTokenId = json.getString("id");

            apiService.getUserByEmail(emailAddress, userPushTokenId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(User response) {
                            facebookSingInValidation = response;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onFacebookSingInValidationFinishedListener.facebookSingInValidationErorr("Communication error, Please try again");
                        }
                        @Override
                        public void onComplete() {
                            if (facebookSingInValidation != null) {
                                try {
                                    if (facebookSingInValidation.getUserName() == null) {
                                        User user = new User();
                                        user.setUserName(userName);
                                        user.setUserEmail(emailAddress);
                                        user.setUserSocialMediaTokenId(userSocialMediaTokenId);
                                        user.setSocialMediaType("F");
                                        onFacebookSingInValidationFinishedListener.newSingUpWithFacebook(user);
                                    } else if (facebookSingInValidation.isVerified()) {
                                        saveUserFacebook(facebookSingInValidation, onFacebookSingInValidationFinishedListener);
                                    } else {
                                        User user = new User();
                                        user.setUserId(facebookSingInValidation.getUserId());
                                        user.setSmsCode(facebookSingInValidation.getSmsCode());
                                        onFacebookSingInValidationFinishedListener.userNotValidateFacebook(user);
                                    }
                                } catch (NullPointerException exNull) {
                                    onFacebookSingInValidationFinishedListener.facebookSingInValidationErorr("Communication error, Please try again");
                                }
                            } else {
                                onFacebookSingInValidationFinishedListener.facebookSingInValidationErorr("Communication error, Please try again");
                            }

                        }
                    });

        } catch (JSONException e) {
            onFacebookSingInValidationFinishedListener.facebookSingInValidationErorr("Communication error, Please try again");
            Logger.e(e.toString());
        } catch (Exception ex) {
            onFacebookSingInValidationFinishedListener.facebookSingInValidationErorr("Communication error, Please try again");
        }

    }


    @Override
    public void checkGoogleSingInValidation(Context context, GoogleSignInAccount account, final OnGoogleSingInValidationFinishedListener onGoogleSingInValidationFinishedListener) {
        encryptedPreferences = new EncryptedPreferences.Builder(context).withEncryptionPassword("122547895511").build();
        String userPushTokenId = encryptedPreferences.getString(PUSH_TOKEN, "");
        final String emailAddress = account.getEmail();
        final String userName = account.getDisplayName();
        final String userSocialMediaTokenId = account.getIdToken();

        //google
        try {
            apiService.getUserByEmail(emailAddress, userPushTokenId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<User>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(User response) {
                            googleSingInValidation = response;
                        }
                        @Override
                        public void onError(Throwable e) {
                            onGoogleSingInValidationFinishedListener.googleSingInValidationErorr("Communication error, Please try again");
                        }
                        @Override
                        public void onComplete() {
                            if (googleSingInValidation != null) {
                                try {
                                    if (googleSingInValidation.getUserName() == null) {
                                        User user = new User();
                                        user.setUserName(userName);
                                        user.setUserEmail(emailAddress);
                                        user.setUserSocialMediaTokenId(userSocialMediaTokenId);
                                        user.setSocialMediaType("G");
                                        onGoogleSingInValidationFinishedListener.newSingUpWithGoogle(user);
                                    } else if (googleSingInValidation.isVerified()) {
                                        saveUserGoogle(googleSingInValidation, onGoogleSingInValidationFinishedListener);
                                    } else {
                                        User user = new User();
                                        user.setUserId(googleSingInValidation.getUserId());
                                        user.setSmsCode(googleSingInValidation.getSmsCode());
                                        onGoogleSingInValidationFinishedListener.userNotValidateGoogle(user);
                                    }
                                } catch (NullPointerException exNull) {
                                    onGoogleSingInValidationFinishedListener.googleSingInValidationErorr("Communication error, Please try again");
                                }

                            } else {
                                onGoogleSingInValidationFinishedListener.googleSingInValidationErorr("Communication error, Please try again");
                            }

                        }
                    });

        } catch (Exception ex) {
            onGoogleSingInValidationFinishedListener.googleSingInValidationErorr("Communication error, Please try again");
        }

    }


    private void saveUser(final User userArrayList, final OnLocalSingInValidationFinishedListener onLocalSingInValidationFinishedListener) {

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                final Long userList = bgRealm.where(User.class).count();
                User user = bgRealm.createObject(User.class, (userList.intValue() + 1));
                user.setUserId(userArrayList.getUserId());
                user.setUserName(userArrayList.getUserName());
                user.setUserEmail(userArrayList.getUserEmail());
                user.setSocialMediaType(userArrayList.getSocialMediaType());
                user.setDateOfBirth(userArrayList.getDateOfBirth());
                user.setUserPhoneNumber(userArrayList.getUserPhoneNumber());
                user.setUserGender(userArrayList.getUserGender());
                onLocalSingInValidationFinishedListener.emailExistSocial();
            }
        });

    }

    private void saveUserGoogle(final User userArrayList, final OnGoogleSingInValidationFinishedListener onGoogleSingInValidationFinishedListener) {

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                final Long userList = bgRealm.where(User.class).count();
                User user = bgRealm.createObject(User.class, (userList.intValue() + 1));
                user.setUserId(userArrayList.getUserId());
                user.setUserName(userArrayList.getUserName());
                user.setUserEmail(userArrayList.getUserEmail());
                user.setSocialMediaType(userArrayList.getSocialMediaType());
                user.setDateOfBirth(userArrayList.getDateOfBirth());
                user.setUserPhoneNumber(userArrayList.getUserPhoneNumber());
                user.setUserGender(userArrayList.getUserGender());
                onGoogleSingInValidationFinishedListener.alreadySingUpWithGoogle();

            }
        });

    }

    private void saveUserFacebook(final User userArrayList, final OnFacebookSingInValidationFinishedListener onFacebookSingInValidationFinishedListener) {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                final Long userList = bgRealm.where(User.class).count();
                User user = bgRealm.createObject(User.class, (userList.intValue() + 1));
                user.setUserId(userArrayList.getUserId());
                user.setUserName(userArrayList.getUserName());
                user.setUserEmail(userArrayList.getUserEmail());
                user.setSocialMediaType(userArrayList.getSocialMediaType());
                user.setDateOfBirth(userArrayList.getDateOfBirth());
                user.setUserPhoneNumber(userArrayList.getUserPhoneNumber());
                user.setUserGender(userArrayList.getUserGender());
                onFacebookSingInValidationFinishedListener.alreadySingUpWithFacebook();
            }
        });

    }

}
