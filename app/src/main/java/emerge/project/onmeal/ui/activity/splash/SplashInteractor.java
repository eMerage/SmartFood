package emerge.project.onmeal.ui.activity.splash;


import android.content.Context;

import emerge.project.onmeal.utils.entittes.UpdateToken;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SplashInteractor {

    interface OnCheckUserFinishedListener {
        void userAvailable();
        void userNotAvailable();
        void userSingOut();
    }
    void checkUser(OnCheckUserFinishedListener onCheckUserFinishedListener);


    interface OnDeleteLocalOrderDataFinishedListener {
        void deletetedData();
    }
    void deleteLocalOrderData(OnDeleteLocalOrderDataFinishedListener onDeleteLocalOrderDataFinishedListener);



    interface OnUpdatePushTokenAndAppVersionFinishedListener {
        void updateStatus(Boolean status,UpdateToken updateToken);
    }
    void updatePushTokenAndAppVersion(Context con, OnUpdatePushTokenAndAppVersionFinishedListener onUpdatePushTokenAndAppVersionFinishedListener);



}
