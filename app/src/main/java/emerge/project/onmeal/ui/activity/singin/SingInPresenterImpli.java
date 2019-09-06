package emerge.project.onmeal.ui.activity.singin;


import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.ui.activity.singup.SingUpInteractor;
import emerge.project.onmeal.ui.activity.singup.SingUpInteractorImpil;
import emerge.project.onmeal.ui.activity.singup.SingUpPresenter;
import emerge.project.onmeal.ui.activity.singup.SingUpView;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SingInPresenterImpli implements SingInPresenter,SingInInteractor.OnLoginValidationFinishedListener {


    private SingInView singInView;
    SingInInteractor singInInteractor;


    public SingInPresenterImpli(SingInView singinView) {
        this.singInView = singinView;
        this.singInInteractor = new SingInInteractorImpil();

    }


    @Override
    public void checkLoginValidation(String email, String password) {
        singInInteractor.checkLoginValidation(email,password,this);
    }

    @Override
    public void emailAddressEmpty() {
        singInView.ShowEmailAddressEmpty();
    }

    @Override
    public void emailAddressInvalid() {
        singInView.ShowEmailAddressInvalid();
    }

    @Override
    public void passwordEmpty() {
        singInView.ShowPasswordEmpty();
    }

    @Override
    public void passwordInvalid(String invalidReason) {
        singInView.ShowPasswordInvalid(invalidReason);
    }

    @Override
    public void loginValidationSuccessful() {
        singInView.ShowLoginValidationSuccessful();
    }

    @Override
    public void loginValidationFail(String msg) {
        singInView.ShowLoginValidationFail(msg);
    }

}
