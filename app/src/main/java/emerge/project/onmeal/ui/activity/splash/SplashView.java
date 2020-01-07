package emerge.project.onmeal.ui.activity.splash;


import emerge.project.onmeal.utils.entittes.UpdateToken;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SplashView {
    void userAvailable();
    void userNotAvailable();
    void userSingOut();

    void deletetedData();

    void updateStatus(Boolean status,UpdateToken updateToken);




}
