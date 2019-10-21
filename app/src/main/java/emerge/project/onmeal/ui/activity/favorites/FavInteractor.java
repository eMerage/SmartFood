package emerge.project.onmeal.ui.activity.favorites;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.ui.activity.home.HomeInteractor;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface FavInteractor {


    interface OnFavouriteItemsLoadFinishedListener {
        void getFavouriteItemsEmpty();
        void getFavouriteItemsSuccessful(ArrayList<HomeFavouriteItems> favouriteItemsArrayList);
        void getFavouriteItemsFail(String msg);
    }
    void getFavouriteItems(Context appContext,OnFavouriteItemsLoadFinishedListener onFavouriteItemsLoadFinishedListener);


    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context, OnsignOutinishedListener onsignOutinishedListener);





}
