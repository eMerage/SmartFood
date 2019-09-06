package emerge.project.onmeal.ui.activity.numbervalidate;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface NumberValidateView {



    void showOTPCodeMissing();
    void showOTPCodeInvalid();
    void showOTPCodeValid();
    void showOTPCodeExpired(String code);
    void showOTPCodeServerError(String error);



    void showNewOTPCode(String code);
    void showNewOTPCodeServerError(String error);


}
