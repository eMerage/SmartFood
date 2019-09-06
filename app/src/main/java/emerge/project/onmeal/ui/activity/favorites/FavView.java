package emerge.project.onmeal.ui.activity.favorites;


import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface FavView {


        void getFavouriteItemsEmpty();
        void getFavouriteItemsSuccessful(ArrayList<HomeFavouriteItems> favouriteItemsArrayList);
        void getFavouriteItemsFail(String msg);


}
