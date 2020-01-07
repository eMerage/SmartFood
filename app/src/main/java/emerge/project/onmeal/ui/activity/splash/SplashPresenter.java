package emerge.project.onmeal.ui.activity.splash;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SplashPresenter {
    void checkUser();

    void deleteLocalOrderData();

    void updatePushTokenAndAppVersion(Context con);

}
