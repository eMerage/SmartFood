package emerge.project.onmeal.ui.activity.singup;


import emerge.project.onmeal.data.table.User;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SingUpPresenter {

    void checkEmailValidation(String emailAddress);
    void checkFullNameValidation(String fullName);
    void checkPhoneNumberValidation(String phoneNumber);
    void checkPasswordValidation(String password, String confrimPassword);
    void singUp(User user);



}
