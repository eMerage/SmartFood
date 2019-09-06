package emerge.project.onmeal.ui.activity.favorites;


import com.luseen.logger.Logger;

import java.util.ArrayList;
import java.util.List;


import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class FavInteractorImpil implements FavInteractor {

    Realm realm = Realm.getDefaultInstance();

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
    List<HomeFavouriteItems> favouriteItems;

    @Override
    public void getFavouriteItems(final OnFavouriteItemsLoadFinishedListener onFavouriteItemsLoadFinishedListener) {

        final ArrayList<HomeFavouriteItems> favouriteItemsArrayList = new ArrayList<HomeFavouriteItems>();
        User user = realm.where(User.class).findFirst();
        try {
            apiService.getFavouriteItems(Integer.parseInt(user.getUserId()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<HomeFavouriteItems>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(List<HomeFavouriteItems> respond) {
                            favouriteItems = respond;
                        }
                        @Override
                        public void onError(Throwable e) {
                            onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
                        }
                        @Override
                        public void onComplete() {
                            if (favouriteItems != null) {
                                try {
                                    if (favouriteItems.isEmpty()) {
                                        onFavouriteItemsLoadFinishedListener.getFavouriteItemsEmpty();
                                    } else {
                                        for (int i = 0; i < favouriteItems.size(); i++) {
                                            favouriteItemsArrayList.add(new HomeFavouriteItems(favouriteItems.get(i).getFavId(),
                                                    favouriteItems.get(i).getFavName(), favouriteItems.get(i).getFavImgUrl(),
                                                    favouriteItems.get(i).getFavType(), favouriteItems.get(i).getFavTypeId()));
                                        }
                                        onFavouriteItemsLoadFinishedListener.getFavouriteItemsSuccessful(favouriteItemsArrayList);
                                    }

                                } catch (NullPointerException exNull) {
                                    onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
                                }
                            } else {
                                onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
                            }
                        }
                    });
        } catch (Exception ex) {
            onFavouriteItemsLoadFinishedListener.getFavouriteItemsFail("Something went wrong, Please try again");
        }
    }
}


