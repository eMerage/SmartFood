package emerge.project.onmeal.ui.activity.home;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.OutletItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeInteractor {

    interface OnFavouriteItemsLoadFinishedListener {
        void getFavouriteItemsEmpty();
        void getFavouriteItemsSuccessful(ArrayList<HomeFavouriteItems> favouriteItemsArrayList);
        void getFavouriteItemsFail(String msg);
    }
    void getFavouriteItems(OnFavouriteItemsLoadFinishedListener  onFavouriteItemsLoadFinishedListener);


    interface OnMainFoodLoadFinishedListener {
        void getMainFoodEmpty();
        void getMainFoodSuccessful(ArrayList<Foodtems> foodtems);
        void getMainFoodFail(String msg);
    }
    void getMainFood(Context mContext,String serachtext, OnMainFoodLoadFinishedListener  onMainFoodLoadFinishedListener);




    interface OnOutletLoadFinishedListener {
        void getOutletEmpty();
        void getOutletSuccessful(ArrayList<OutletItems> outletItems);
        void getOutletFail(String msg);
    }
    void getOutlet(Context mContext,String serachtext,OnOutletLoadFinishedListener  onOutletLoadFinishedListener);

    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context,OnsignOutinishedListener onsignOutinishedListener);






}
