package emerge.project.onmeal.ui.activity.favorites;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface FavPresenter {


    void getFavouriteItems(Context appContext);

    void signOut(Context context);
}
