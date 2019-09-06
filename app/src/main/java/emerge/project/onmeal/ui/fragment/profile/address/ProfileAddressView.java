package emerge.project.onmeal.ui.fragment.profile.address;


import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface ProfileAddressView {

        void getAddressEmpty();
        void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList);
        void getAddressFail(String msg);


        void deleteAddressStart();
        void deleteAddressSuccessful();
        void deleteAddressFail(String addressID,String msg);



}
