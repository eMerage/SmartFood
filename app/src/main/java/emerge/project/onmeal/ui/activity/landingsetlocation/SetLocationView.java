package emerge.project.onmeal.ui.activity.landingsetlocation;


import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.AddressItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SetLocationView {
    void selectedAddressDetails(AddressItems addressItems);
    void selectedAddressDetailsFail();
}
