package emerge.project.onmeal.ui.activity.singup;


import emerge.project.onmeal.data.table.User;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SingUpPresenterImpli implements SingUpPresenter,
        SingUpInteractor.OnSingInEmailValidationFinishedListener,
        SingUpInteractor.OnFullNameValidationFinishedListener,
        SingUpInteractor.OnPhoneNumberValidationFinishedListener,
        SingUpInteractor.OnPasswordValidationFinishedListener,
        SingUpInteractor.OnSingUpListener
       {


    private SingUpView singUpView;
    SingUpInteractor singUpInteractor;


    public SingUpPresenterImpli(SingUpView singUpView) {
        this.singUpView = singUpView;
        this.singUpInteractor = new SingUpInteractorImpil();

    }




    @Override
    public void checkEmailValidation(String emailAddress) {
        singUpInteractor.checkEmailValidation(emailAddress, this);
    }

    @Override
    public void checkFullNameValidation(String fullName) {
        singUpInteractor.checkFullNameValidation(fullName,this);
    }

    @Override
    public void checkPhoneNumberValidation(String phoneNumber) {
        singUpInteractor.checkPhoneNumberValidation(phoneNumber,this);
    }

    @Override
    public void checkPasswordValidation(String password, String confrimPassword) {
        singUpInteractor.checkPasswordValidation(password,confrimPassword,this);
    }

    @Override
    public void singUp(User user) {
        singUpInteractor.singUp(user,this);
    }







    @Override
    public void emailAddressEmpty() {
        singUpView.showEmailAddressEmpty();
    }
    @Override
    public void emailAddressInvalid() {
        singUpView.showEmailAddressInvalid();
    }
    @Override
    public void emailAddressValid() {
        singUpView.showEmailAddressValid();
    }




    @Override
    public void fullNameEmpty() {
        singUpView.showFullNameEmpty();
    }
    @Override
    public void fullNameInvalid(String invalidReason) { singUpView.showFullNameInvalid(invalidReason); }
    @Override
    public void fullNameValid() {
        singUpView.showFullNameValid();
    }



    @Override
    public void phoneNumberEmpty() { singUpView.showPhoneNumberEmpty(); }
    @Override
    public void phoneNumberInvalid(String invalidReason) { singUpView.showPhoneNumberInvalid(invalidReason); }
    @Override
    public void phoneNumberValid() { singUpView.showPhoneNumberValid(); }

    @Override
    public void passwordEmpty() {
        singUpView.showPasswordEmpty();
    }

    @Override
    public void confirmPasswordEmpty() {
        singUpView.showConfirmPasswordEmpty();
    }

    @Override
    public void passwordInvalid(String invalidReason) {
        singUpView.showPasswordInvalid(invalidReason);
    }

    @Override
    public void passwordDoesNotMatch() {
        singUpView.showPasswordDoesNotMatch();
    }

    @Override
    public void passwordValid() {
        singUpView.showPasswordValid();
    }







    @Override
    public void singUpSuccess(String UserId,String phoneNumber, String smsVrificationCode) {
        singUpView.showSingUpSuccess(UserId,phoneNumber,smsVrificationCode);
    }

    @Override
    public void singUpFail(String errorMsg) {
        singUpView.showSingUpFail(errorMsg);
    }



}
