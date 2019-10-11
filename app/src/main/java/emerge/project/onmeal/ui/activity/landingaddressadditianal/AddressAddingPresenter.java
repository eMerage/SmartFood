package emerge.project.onmeal.ui.activity.landingaddressadditianal;


import android.content.Context;

import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface AddressAddingPresenter {
    void addNewAddress(AddressItems addressItems);

    void signOut(Context context);
}
