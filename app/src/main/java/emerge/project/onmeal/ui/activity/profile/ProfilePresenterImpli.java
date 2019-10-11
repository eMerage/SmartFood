package emerge.project.onmeal.ui.activity.profile;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class ProfilePresenterImpli implements ProfilePresenter,
        ProfileInteractor.OnsignOutinishedListener {


    private ProfileContactView profileContactView;
    ProfileInteractor profileInteractor;


    public ProfilePresenterImpli(ProfileContactView profilecontactView) {
        this.profileContactView = profilecontactView;
        this.profileInteractor = new ProfileInteractorImpil();

    }


    @Override
    public void signOut(Context context) {
        profileInteractor.signOut(context, this);
    }


    @Override
    public void signOutSuccess() {
        profileContactView.signOutSuccess();

    }

}