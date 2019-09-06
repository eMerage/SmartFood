package emerge.project.onmeal.ui.fragment.profile.contact;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.User;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProfileContactInteractor {
    interface OnGetUserDetailsFinishedListener {
        void getUserDetailsSuccessful(User user);

        void getUserDetailsFail(String msg);
    }

    void getUserDetails(OnGetUserDetailsFinishedListener onGetUserDetailsFinishedListener);


    interface OnUpdateUserDetailsFinishedListener {
        void incorrectDOBFormat();
        void incorrectGenderFormat();
        void emptyInputs();
        void getUpdateSuccessful();
        void getUpdateFail(String msg);
    }
    void updateUserDetails(String gender,String dob,OnUpdateUserDetailsFinishedListener onUpdateUserDetailsFinishedListener);


}
