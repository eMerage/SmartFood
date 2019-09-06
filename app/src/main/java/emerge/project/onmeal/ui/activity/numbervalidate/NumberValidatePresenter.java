package emerge.project.onmeal.ui.activity.numbervalidate;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface NumberValidatePresenter {

    void validateOTPCode(String codeFromUserReg, String codeFromSMS, String userId);
    void getNewOTPCode(String userId);

}
