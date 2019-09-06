package emerge.project.onmeal.ui.activity.login;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;

import emerge.project.onmeal.data.table.User;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginInteractor {



    interface OnLocalSingInValidationFinishedListener {
        void loginEmailAddressEmpty();
        void loginEmailAddressInvalid();
        void emailExist(User user);
        void emailExistSocial();
        void emailNotExist(User user);

        void localSingInValidationErorr(String error);
        void userNotValidateLocal(User user);
    }
    void checkLocalSingInValidation(Context context,String email, OnLocalSingInValidationFinishedListener onLocalSingInValidationFinishedListener);



    interface OnFacebookSingInValidationFinishedListener {
        void alreadySingUpWithFacebook();
        void newSingUpWithFacebook(User user);
        void facebookSingInValidationErorr(String error);
        void userNotValidateFacebook(User user);
    }
    void checkFacebookSingInValidation(Context context,JSONObject json, OnFacebookSingInValidationFinishedListener onFacebookSingInValidationFinishedListener);



    interface OnGoogleSingInValidationFinishedListener {
        void alreadySingUpWithGoogle();
        void newSingUpWithGoogle(User user);
        void googleSingInValidationErorr(String error);
        void userNotValidateGoogle(User user);
    }
    void checkGoogleSingInValidation(Context context,GoogleSignInAccount account, OnGoogleSingInValidationFinishedListener onGoogleSingInValidationFinishedListener);




}
