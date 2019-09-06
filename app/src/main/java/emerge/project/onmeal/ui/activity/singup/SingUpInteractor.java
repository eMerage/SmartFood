package emerge.project.onmeal.ui.activity.singup;


import emerge.project.onmeal.data.table.User;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SingUpInteractor {

    interface OnSingInEmailValidationFinishedListener {
        void emailAddressEmpty();
        void emailAddressInvalid();
        void emailAddressValid();
    }
    void checkEmailValidation(String email, OnSingInEmailValidationFinishedListener onSingInEmailValidationFinishedListener);


    interface OnFullNameValidationFinishedListener {
        void fullNameEmpty();
        void fullNameInvalid(String invalidReason);
        void fullNameValid();

    }
    void checkFullNameValidation(String fullName, OnFullNameValidationFinishedListener onFullNameValidationFinishedListener);


    interface OnPhoneNumberValidationFinishedListener {
        void phoneNumberEmpty();
        void phoneNumberInvalid(String invalidReason);
        void phoneNumberValid();

    }
    void checkPhoneNumberValidation(String phoneNumber, OnPhoneNumberValidationFinishedListener onPhoneNumberValidationFinishedListener);


    interface OnPasswordValidationFinishedListener {

        void passwordEmpty();
        void confirmPasswordEmpty();
        void passwordInvalid(String invalidReason);
        void passwordDoesNotMatch();
        void passwordValid();

    }
    void checkPasswordValidation(String password, String confrimPassword, OnPasswordValidationFinishedListener onPasswordValidationFinishedListener);


    interface OnSingUpListener {
        void singUpSuccess(String UserId,String phoneNumber, String smsVrificationCode);
        void singUpFail(String errorMsg);

    }
    void singUp(User user, OnSingUpListener onSingUpListener);




}
