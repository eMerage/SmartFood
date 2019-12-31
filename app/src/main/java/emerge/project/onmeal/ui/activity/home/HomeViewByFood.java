package emerge.project.onmeal.ui.activity.home;


import java.util.ArrayList;


import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.VersionUpdate;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeViewByFood {

    void getMainFoodEmpty();
    void getMainFoodSuccessful(ArrayList<Foodtems> foodtems);
    void getMainFoodFail(String msg);


    void updateAppVersionAndPushSuccessful(VersionUpdate versionUpdate);

    void updateAppVersionAndPushFail(String msg);

}
