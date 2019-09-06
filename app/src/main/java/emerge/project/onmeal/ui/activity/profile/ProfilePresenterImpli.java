package emerge.project.onmeal.ui.activity.profile;




/**
 * Created by Himanshu on 4/4/2017.
 */

public class ProfilePresenterImpli implements ProfilePresenter{


    private ProfileContactView profileContactView;
    ProfileInteractor profileInteractor;


    public ProfilePresenterImpli(ProfileContactView profilecontactView) {
        this.profileContactView = profilecontactView;
        this.profileInteractor = new ProfileInteractorImpil();

    }



}
