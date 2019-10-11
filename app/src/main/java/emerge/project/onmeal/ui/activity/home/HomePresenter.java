package emerge.project.onmeal.ui.activity.home;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomePresenter {
    void getFavouriteItems();
    void getMainFood(Context mContext,String serachtext);
    void getOutlet(Context mContext,String serachtext);
    void signOut(Context context);
}
