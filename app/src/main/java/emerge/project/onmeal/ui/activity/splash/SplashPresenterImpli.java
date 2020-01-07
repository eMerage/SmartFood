package emerge.project.onmeal.ui.activity.splash;


import android.content.Context;

import emerge.project.onmeal.utils.entittes.UpdateToken;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SplashPresenterImpli implements SplashPresenter, SplashInteractor.OnCheckUserFinishedListener,
        SplashInteractor.OnDeleteLocalOrderDataFinishedListener,SplashInteractor.OnUpdatePushTokenAndAppVersionFinishedListener {


    private SplashView splashView;
    SplashInteractor landingInteractor;


    public SplashPresenterImpli(SplashView splashView) {
        this.splashView = splashView;
        this.landingInteractor = new SplashInteractorImpil();

    }


    @Override
    public void checkUser() {
        landingInteractor.checkUser(this);
    }



    @Override
    public void userAvailable() {
        splashView.userAvailable();
    }

    @Override
    public void userNotAvailable() {
        splashView.userNotAvailable();
    }

    @Override
    public void userSingOut() {
        splashView.userSingOut();
    }




    @Override
    public void deleteLocalOrderData() {
        landingInteractor.deleteLocalOrderData(this);
    }



    @Override
    public void deletetedData() {
        splashView.deletetedData();
    }



    @Override
    public void updatePushTokenAndAppVersion(Context con) {
        landingInteractor.updatePushTokenAndAppVersion( con,this);
    }



    @Override
    public void updateStatus(Boolean status, UpdateToken updateToken) {
        splashView.updateStatus(status,updateToken);
    }


}
