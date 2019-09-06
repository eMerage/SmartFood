package emerge.project.onmeal.ui.fragment.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import emerge.project.onmeal.ui.activity.home.HomeViewByOutlet;
import emerge.project.onmeal.ui.adaptor.HomeOutletAdapter;
import emerge.project.onmeal.utils.entittes.OutletItems;


public class FragmentHomeOutlet extends Fragment implements HomeViewByOutlet {



    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.recyclerviewe_fragment_outlet)
    RecyclerView recyclerriceFragmentOutlet;

    @BindView(R.id.textView_recyclerview_empty)
    TextView textViewRecyclerviewEmpty;

    @BindView(R.id.relativelayout_btn_retry)
    RelativeLayout relativelayoutBtnRetry;


    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    HomeOutletAdapter homeOutletAdapter;

    ArrayList<OutletItems> outletItems;
    HomePresenter homePresenter;



    public FragmentHomeOutlet() {
        homePresenter = new HomePresenterImpli(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmenmt_home_outlet, container, false);
        ButterKnife.bind(this, rootView);

        outletItems = new ArrayList<OutletItems>();

        ((ActivityHome)getActivity()).setHomeViewByOutletView(this);

        homePresenter = new HomePresenterImpli(this);
        setMenuRecycalView();


        if (NetworkAvailability.isNetworkAvailable(getContext())) {

            proprogressview.setVisibility(View.VISIBLE);
            homePresenter.getOutlet(getContext(),"");

            if(NetworkAvailability.isConnectionFast(getContext())==1){
                Toast.makeText(getContext(), "your internet connection speed is very low,can't load images", Toast.LENGTH_LONG).show();
            }else if(NetworkAvailability.isConnectionFast(getContext())==2){
                Toast.makeText(getContext(), "your internet connection speed is low, Please wait for the images", Toast.LENGTH_LONG).show();
            }else {

            }
        } else {
            Toast.makeText(getContext(), "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


        pullToRefresh();
        return rootView;
    }

    @OnClick(R.id.relativelayout_btn_retry)
    public void onClickReTry(View view) {
        if (NetworkAvailability.isNetworkAvailable(getContext())) {

            relativelayoutBtnRetry.setVisibility(View.GONE);
            textViewRecyclerviewEmpty.setVisibility(View.GONE);
            proprogressview.setVisibility(View.VISIBLE);
            homePresenter.getOutlet(getContext(),"");

        } else {
            Toast.makeText(getContext(), "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
        }


    }


    private void setMenuRecycalView() {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerriceFragmentOutlet.setLayoutManager(mLayoutManager);
        recyclerriceFragmentOutlet.setItemAnimator(new DefaultItemAnimator());
        recyclerriceFragmentOutlet.setNestedScrollingEnabled(true);
    }



    private void pullToRefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkAvailability.isNetworkAvailable(getContext())) {
                    homePresenter.getOutlet(getContext(),"");

                    if(NetworkAvailability.isConnectionFast(getContext())==1){
                        Toast.makeText(getContext(), "your internet connection speed is very low,can't load images", Toast.LENGTH_LONG).show();
                    }else if(NetworkAvailability.isConnectionFast(getContext())==2){
                        Toast.makeText(getContext(), "your internet connection speed is low, Please wait for the images", Toast.LENGTH_LONG).show();
                    }else {

                    }


                } else {
                    Toast.makeText(getContext(), "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void getOutletEmpty() {
        relativelayoutBtnRetry.setVisibility(View.GONE);
        proprogressview.setVisibility(View.GONE);
        textViewRecyclerviewEmpty.setText("No Outlet");
        textViewRecyclerviewEmpty.setVisibility(View.VISIBLE);

        outletItems.clear();

        homeOutletAdapter = new HomeOutletAdapter(getContext(), outletItems);
        recyclerriceFragmentOutlet.setAdapter(homeOutletAdapter);
    }

    @Override
    public void getOutletSuccessful(ArrayList<OutletItems> outletItem) {
        outletItems.clear();
        outletItems=outletItem;
        recyclerriceFragmentOutlet.setVisibility(View.VISIBLE);
        relativelayoutBtnRetry.setVisibility(View.GONE);
        proprogressview.setVisibility(View.GONE);
        textViewRecyclerviewEmpty.setVisibility(View.GONE);




        homeOutletAdapter = new HomeOutletAdapter(getContext(), outletItems);
        recyclerriceFragmentOutlet.setAdapter(homeOutletAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getOutletFail(String msg) {
        recyclerriceFragmentOutlet.setVisibility(View.GONE);
        proprogressview.setVisibility(View.GONE);
        relativelayoutBtnRetry.setVisibility(View.VISIBLE);
        textViewRecyclerviewEmpty.setText(msg);
        textViewRecyclerviewEmpty.setVisibility(View.VISIBLE);
    }
}
