package emerge.project.onmeal.ui.activity.landing;


import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LandingView {

    void selectedAddressDetails(AddressItems addressItems);
    void selectedAddressDetailsFail();

    void getAddressEmpty();

    void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList);

    void getAddressFail(String msg);


    void addNewAddressSuccessful();

    void addNewAddressFail(String msg);


    void saveAddressSuccessful(String add);

    void saveAddressFail(String msg);


}
