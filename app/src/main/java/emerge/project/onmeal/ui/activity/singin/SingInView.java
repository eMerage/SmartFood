package emerge.project.onmeal.ui.activity.singin;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SingInView {


    void ShowEmailAddressEmpty();
    void ShowEmailAddressInvalid();
    void ShowPasswordEmpty();
    void ShowPasswordInvalid(String invalidReason);
    void ShowLoginValidationSuccessful();
    void ShowLoginValidationFail(String msg);



}
