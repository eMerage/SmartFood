package emerge.project.onmeal.ui.activity.splash;


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

}
