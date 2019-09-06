package emerge.project.onmeal.ui.fragment.profile.contact;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressInteractor;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressInteractorImpil;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressPresenter;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressView;
import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class ProfileContactPresenterImpli implements ProfileContactPresenter,
        ProfileContactInteractor.OnGetUserDetailsFinishedListener,ProfileContactInteractor.OnUpdateUserDetailsFinishedListener{


    private ProfileContactView profileAddressView;
    ProfileContactInteractor profileContactInteractor;


    public ProfileContactPresenterImpli(ProfileContactView ProfileContactView) {
        this.profileAddressView = ProfileContactView;
        this.profileContactInteractor = new ProfileContactInteractorImpil();

    }



    @Override
    public void getUserDetails() {
        profileContactInteractor.getUserDetails(this);
    }



    @Override
    public void getUserDetailsSuccessful(User user) {
        profileAddressView.getUserDetailsSuccessful(user);
    }

    @Override
    public void getUserDetailsFail(String msg) {
        profileAddressView.getUserDetailsFail(msg);
    }





    @Override
    public void updateUserDetails(String gender, String dob) {
        profileContactInteractor.updateUserDetails(gender,dob,this);
    }


    @Override
    public void incorrectDOBFormat() {
        profileAddressView.incorrectDOBFormat();
    }

    @Override
    public void incorrectGenderFormat() {
        profileAddressView.incorrectGenderFormat();
    }

    @Override
    public void emptyInputs() {
        profileAddressView.emptyInputs();
    }

    @Override
    public void getUpdateSuccessful() {
        profileAddressView.getUpdateSuccessful();
    }

    @Override
    public void getUpdateFail(String msg) {
        profileAddressView.getUpdateFail(msg);
    }
}
