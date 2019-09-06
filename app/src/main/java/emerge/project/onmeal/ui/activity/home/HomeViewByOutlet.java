package emerge.project.onmeal.ui.activity.home;


import java.util.ArrayList;


import emerge.project.onmeal.utils.entittes.OutletItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeViewByOutlet {


        void getOutletEmpty();
        void getOutletSuccessful(ArrayList<OutletItems> outletItems);
        void getOutletFail(String msg);




}
