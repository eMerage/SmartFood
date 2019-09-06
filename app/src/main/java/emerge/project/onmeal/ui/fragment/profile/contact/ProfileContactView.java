package emerge.project.onmeal.ui.fragment.profile.contact;


import java.util.ArrayList;

import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProfileContactView {


    void getUserDetailsSuccessful(User user);

    void getUserDetailsFail(String msg);


    void incorrectDOBFormat();

    void incorrectGenderFormat();

    void emptyInputs();

    void getUpdateSuccessful();

    void getUpdateFail(String msg);


}
