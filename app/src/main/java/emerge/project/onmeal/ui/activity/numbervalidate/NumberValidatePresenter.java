package emerge.project.onmeal.ui.activity.numbervalidate;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface NumberValidatePresenter {

    void validateOTPCode(String codeFromUserReg, String codeFromSMS, String userId);
    void getNewOTPCode(String userId);

    void updatePushTokenAndAppVersion(Context con);

}
