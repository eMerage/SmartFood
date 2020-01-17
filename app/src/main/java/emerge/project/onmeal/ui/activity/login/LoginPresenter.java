package emerge.project.onmeal.ui.activity.login;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginPresenter {

    void checkLocalSingInValidation(Context context, String email);

    void checkFacebookSingInValidation(Context context,JSONObject json);

    void checkGoogleSingInValidation(Context context,GoogleSignInAccount account);



    void updatePushTokenAndAppVersion(Context con);


}
