package emerge.project.onmeal.ui.activity.login;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONObject;

import emerge.project.onmeal.data.table.User;


/**
 * Created by Himanshu on 4/4/2017.
 */

public class LoginPresenterImpli implements LoginPresenter,
        LoginInteractor.OnLocalSingInValidationFinishedListener,
        LoginInteractor.OnFacebookSingInValidationFinishedListener,
        LoginInteractor.OnGoogleSingInValidationFinishedListener{


    private LoginView loginView;
    LoginInteractor loginInteractor;


    public LoginPresenterImpli(LoginView loginview) {
        this.loginView = loginview;
        this.loginInteractor = new LoginInteractorImpil();

    }


    //...........................................

    @Override
    public void checkLocalSingInValidation(Context context, String email) {
        loginInteractor.checkLocalSingInValidation(context,email,this);
    }


    @Override
    public void loginEmailAddressEmpty() {
        loginView.showLoginEmailAddressEmpty();
    }

    @Override
    public void loginEmailAddressInvalid() {
        loginView.showLoginEmailAddressInvalid();
    }

    @Override
    public void emailExist(User user) {

        loginView.showEmailExist(user);
    }

    @Override
    public void emailExistSocial() {
        loginView.showEmailExistSocial();
    }

    @Override
    public void emailNotExist(User user) {
        loginView.showEmailNotExist(user);
    }

    @Override
    public void localSingInValidationErorr(String error) {
        loginView.localSingInValidationErorr(error);
    }

    @Override
    public void userNotValidateLocal(User user) {
        loginView.userNotValidateLocal(user);
    }


    //.........................................
    @Override
    public void checkFacebookSingInValidation(Context context,JSONObject json) {
        loginInteractor.checkFacebookSingInValidation(context,json,this);
    }

    @Override
    public void alreadySingUpWithFacebook() { loginView.showAlreadySingUpWithFacebook(); }
    @Override
    public void newSingUpWithFacebook(User user) { loginView.showNewSingUpWithFacebook(user); }
    @Override
    public void facebookSingInValidationErorr(String error) { loginView.showFacebookSingInValidationErorr(error);
    }

    @Override
    public void userNotValidateFacebook(User user) {
        loginView.userNotValidateFacebook(user);
    }









    @Override
    public void checkGoogleSingInValidation(Context context,GoogleSignInAccount account) {
        loginInteractor.checkGoogleSingInValidation(context,account,this);
    }

    @Override
    public void alreadySingUpWithGoogle() {
        loginView.showAlreadySingUpWithGoogle();
    }

    @Override
    public void newSingUpWithGoogle(User user) {
        loginView.showNewSingUpWithGoogle(user);
    }

    @Override
    public void googleSingInValidationErorr(String error) {
        loginView.showGoogleSingInValidationErorr(error);
    }

    @Override
    public void userNotValidateGoogle(User user) {
        loginView.userNotValidateGoogle(user);
    }
}
