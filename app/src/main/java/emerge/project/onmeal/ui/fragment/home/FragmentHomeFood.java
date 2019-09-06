package emerge.project.onmeal.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.home.HomePresenter;
import emerge.project.onmeal.ui.activity.home.HomePresenterImpli;
import emerge.project.onmeal.ui.activity.home.HomeViewByFood;
import emerge.project.onmeal.ui.adaptor.HomeFoodAdapter;
import emerge.project.onmeal.utils.entittes.Foodtems;


public class FragmentHomeFood extends Fragment implements HomeViewByFood {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.recyclerviewe_fragment_food)
    RecyclerView recyclerriceFragmentFood;

    @BindView(R.id.relativelayout_btn_retry)
    RelativeLayout relativelayoutBtnRetry;

    @BindView(R.id.textView_recyclerview_empty)
    TextView textViewRecyclerviewEmpty;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    HomePresenter homePresenter;

    HomeFoodAdapter homeFoodAdapter;

    ArrayList<Foodtems> foodItems;


    public FragmentHomeFood() {
        homePresenter = new HomePresenterImpli(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmenmt_home_food, container, false);
        ButterKnife.bind(this, rootView);

        homePresenter = new HomePresenterImpli(this);
        foodItems = new ArrayList<Foodtems>();


        ((ActivityHome) getActivity()).setHomeViewByFoodView(this);

        if (NetworkAvailability.isNetworkAvailable(getContext())) {

            proprogressview.setVisibility(View.VISIBLE);

            homePresenter.getMainFood(getContext(), "");

            if (NetworkAvailability.isConnectionFast(getContext()) == 1) {
                Toast.makeText(getContext(), "your internet connection speed is very low,can't load images", Toast.LENGTH_LONG).show();
            } else if (NetworkAvailability.isConnectionFast(getContext()) == 2) {
                Toast.makeText(getContext(), "your internet connection speed is low, Please wait for the images", Toast.LENGTH_LONG).show();
            } else {

            }
        } else {
            Toast.makeText(getContext(), "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


        setMenuRecycalView();
        pullToRefresh();

        return rootView;
    }


    @OnClick(R.id.relativelayout_btn_retry)
    public void onClickReTry(View view) {

        if (NetworkAvailability.isNetworkAvailable(getContext())) {
            relativelayoutBtnRetry.setVisibility(View.GONE);
            textViewRecyclerviewEmpty.setVisibility(View.GONE);
            proprogressview.setVisibility(View.VISIBLE);
            homePresenter.getMainFood(getContext(), "");

        } else {
            Toast.makeText(getContext(), "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


    }


    private void setMenuRecycalView() {

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerriceFragmentFood.setLayoutManager(mLayoutManager);
        recyclerriceFragmentFood.setItemAnimator(new DefaultItemAnimator());
        recyclerriceFragmentFood.setNestedScrollingEnabled(true);


    }


    private void pullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                proprogressview.setVisibility(View.VISIBLE);
                homePresenter.getMainFood(getContext(), "");

                if (NetworkAvailability.isConnectionFast(getContext()) == 1) {
                    Toast.makeText(getContext(), "your internet connection speed is very low,can't load images", Toast.LENGTH_LONG).show();
                } else if (NetworkAvailability.isConnectionFast(getContext()) == 2) {
                    Toast.makeText(getContext(), "your internet connection speed is low, Please wait for the images", Toast.LENGTH_LONG).show();
                } else {

                }
            }
        });

    }

    @Override
    public void getMainFoodEmpty() {

        relativelayoutBtnRetry.setVisibility(View.GONE);
        proprogressview.setVisibility(View.GONE);
        textViewRecyclerviewEmpty.setText("No Menu");
        textViewRecyclerviewEmpty.setVisibility(View.VISIBLE);
        foodItems.clear();

        homeFoodAdapter = new HomeFoodAdapter(getContext(), foodItems);
        recyclerriceFragmentFood.setAdapter(homeFoodAdapter);
    }

    @Override
    public void getMainFoodSuccessful(ArrayList<Foodtems> foodtems) {

        foodItems.clear();
        foodItems = foodtems;
        recyclerriceFragmentFood.setVisibility(View.VISIBLE);
        relativelayoutBtnRetry.setVisibility(View.GONE);
        proprogressview.setVisibility(View.GONE);
        textViewRecyclerviewEmpty.setVisibility(View.GONE);

        homeFoodAdapter = new HomeFoodAdapter(getContext(), foodtems);
        recyclerriceFragmentFood.setAdapter(homeFoodAdapter);


        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getMainFoodFail(String msg) {

        recyclerriceFragmentFood.setVisibility(View.GONE);
        proprogressview.setVisibility(View.GONE);
        relativelayoutBtnRetry.setVisibility(View.VISIBLE);
        textViewRecyclerviewEmpty.setText(msg);
        textViewRecyclerviewEmpty.setVisibility(View.VISIBLE);

    }


}
