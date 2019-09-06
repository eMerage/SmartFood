package emerge.project.onmeal.ui.activity.splash;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class SplashPresenterImpli implements SplashPresenter, SplashInteractor.OnCheckUserFinishedListener,
        SplashInteractor.OnDeleteLocalOrderDataFinishedListener {


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
}
