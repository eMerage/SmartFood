package emerge.project.onmeal.ui.activity.landingsetlocation;


import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SetLocationPresenter {


    void getSellectedAddressDetails(String name, String address,LatLng latLng);
    void signOut(Context context);

}
