package emerge.project.onmeal.ui.activity.singup;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SingUpView {


    void showEmailAddressEmpty();
    void showEmailAddressInvalid();
    void showEmailAddressValid();



    void showFullNameEmpty();
    void showFullNameInvalid(String invalidReason);
    void showFullNameValid();



    void showPhoneNumberEmpty();
    void showPhoneNumberInvalid(String invalidReason);
    void showPhoneNumberValid();


    void showPasswordEmpty();
    void showConfirmPasswordEmpty();
    void showPasswordInvalid(String invalidReason);
    void showPasswordDoesNotMatch();
    void showPasswordValid();


    void showSingUpSuccess(String UserId,String phoneNumber, String smsVrificationCode);
    void showSingUpFail(String errorMsg);



}
