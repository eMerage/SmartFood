package emerge.project.onmeal.ui.activity.singin;


import emerge.project.onmeal.data.table.User;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SingInInteractor {

    interface OnLoginValidationFinishedListener {
        void emailAddressEmpty();
        void emailAddressInvalid();
        void passwordEmpty();
        void passwordInvalid(String invalidReason);
        void loginValidationSuccessful();
        void loginValidationFail(String msg);
    }
    void checkLoginValidation(String email,String password,OnLoginValidationFinishedListener  onLoginValidationFinishedListener);





}
