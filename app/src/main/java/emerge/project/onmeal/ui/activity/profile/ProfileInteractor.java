package emerge.project.onmeal.ui.activity.profile;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.OutletItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProfileInteractor {

    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context,OnsignOutinishedListener onsignOutinishedListener);


}
