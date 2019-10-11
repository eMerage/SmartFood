package emerge.project.onmeal.ui.activity.home;


import android.content.Context;

import java.util.ArrayList;

import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.OutletItems;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class HomePresenterImpli implements HomePresenter,HomeInteractor.OnFavouriteItemsLoadFinishedListener,
        HomeInteractor.OnMainFoodLoadFinishedListener,HomeInteractor.OnOutletLoadFinishedListener,
        HomeInteractor.OnsignOutinishedListener{


    private HomeView homeView;
    HomeViewByOutlet homeViewByOutlet;
    HomeViewByFood homeViewByFood;
    HomeInteractor homeInteractor;


    public HomePresenterImpli(HomeViewByFood homeViewByFood) {
        this.homeViewByFood = homeViewByFood;
        this.homeInteractor = new HomeInteractorImpil();

    }


    public HomePresenterImpli(HomeViewByOutlet homeViewByOutlet) {
        this.homeViewByOutlet = homeViewByOutlet;
        this.homeInteractor = new HomeInteractorImpil();

    }

    public HomePresenterImpli(HomeView homeView) {
        this.homeView = homeView;
        this.homeInteractor = new HomeInteractorImpil();

    }


    @Override
    public void signOut(Context context) {
        homeInteractor.signOut(context,this);
    }


    @Override
    public void signOutSuccess() {
        homeView.signOutSuccess();
    }


    @Override
    public void getFavouriteItems() {
        homeInteractor.getFavouriteItems(this);
    }



    @Override
    public void getFavouriteItemsEmpty() {
        homeView.getFavouriteItemsEmpty();
    }

    @Override
    public void getFavouriteItemsSuccessful(ArrayList<HomeFavouriteItems> favouriteItemsArrayList) {
        homeView.getFavouriteItemsSuccessful(favouriteItemsArrayList);
    }

    @Override
    public void getFavouriteItemsFail(String msg) {
        homeView.getFavouriteItemsFail(msg);
    }





    @Override
    public void getMainFood(Context mContext,String serachtext) {
        homeInteractor.getMainFood(mContext,serachtext,this);
    }




    @Override
    public void getMainFoodEmpty() {
        homeViewByFood.getMainFoodEmpty();
    }

    @Override
    public void getMainFoodSuccessful(ArrayList<Foodtems> foodtems) {
        homeViewByFood.getMainFoodSuccessful(foodtems);
    }

    @Override
    public void getMainFoodFail(String msg) {
        homeViewByFood.getMainFoodFail(msg);
    }




    @Override
    public void getOutlet(Context mContext,String serachtext) {
        homeInteractor.getOutlet(mContext,serachtext,this);
    }

    @Override
    public void getOutletEmpty() {
        homeViewByOutlet.getOutletEmpty();
    }

    @Override
    public void getOutletSuccessful(ArrayList<OutletItems> outletItems) {
        homeViewByOutlet.getOutletSuccessful(outletItems);
    }

    @Override
    public void getOutletFail(String msg) {
        homeViewByOutlet.getOutletFail(msg);
    }
}
