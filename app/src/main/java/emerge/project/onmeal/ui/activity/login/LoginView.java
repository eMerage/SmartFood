package emerge.project.onmeal.ui.activity.login;


import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.utils.entittes.UpdateToken;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginView {



    void showLoginEmailAddressEmpty();
    void showLoginEmailAddressInvalid();
    void showEmailExist(User user);
    void showEmailExistSocial();
    void showEmailNotExist(User user);
    void localSingInValidationErorr(String error);
    void userNotValidateLocal(User user);


    void showAlreadySingUpWithFacebook();
    void showNewSingUpWithFacebook(User user);
    void showFacebookSingInValidationErorr(String error);
    void userNotValidateFacebook(User user);


    void showAlreadySingUpWithGoogle();
    void showNewSingUpWithGoogle(User user);
    void showGoogleSingInValidationErorr(String error);
    void userNotValidateGoogle(User user);


    void updateStatus(Boolean status, UpdateToken updateToken);




}
