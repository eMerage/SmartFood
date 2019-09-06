package emerge.project.onmeal.ui.activity.favorites;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.ui.adaptor.FavouriteAdapter;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;

public class ActivityFavourites extends Activity implements FavView{


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;


    @BindView(R.id.recyclerView_favorite)
    RecyclerView recyclerViewFavorite;


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    // navigation menu
    @BindView(R.id.text_navigation_getfood)
    TextView textViewNavigationGetFood;

    @BindView(R.id.text_navigation_yourorders)
    TextView textViewNavigationYourOrders;


    @BindView(R.id.text_navigation_favourites)
    TextView textViewNavigationFavourites;

    @BindView(R.id.text_navigation_profile)
    TextView textViewNavigationProfile;


    @BindView(R.id.text_navigation_settings)
    TextView textViewNavigationSettings;

    @BindView(R.id.relativelayout_logout)
    RelativeLayout relativelayoutNavigationLogout;
    // navigation menu


    FavPresenter favPresenter;
    FavouriteAdapter favouriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);


        favPresenter = new FavPresenterImpli(this);

        setFavouriteRecycalView();

        setNavigationMenuItems();




        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            favPresenter.getFavouriteItems();
        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("No Internet Access, Please try again ");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();
        }


    }

    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }



    @Override
    public void getFavouriteItemsEmpty() {
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(this, "No Favourite Items", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getFavouriteItemsSuccessful(ArrayList<HomeFavouriteItems> favouriteItemsArrayList) {
        proprogressview.setVisibility(View.GONE);
        favouriteAdapter = new FavouriteAdapter(this,favouriteItemsArrayList);
        recyclerViewFavorite.setAdapter(favouriteAdapter);

    }

    @Override
    public void getFavouriteItemsFail(String msg) {

        proprogressview.setVisibility(View.GONE);

        try {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Warning");
        alertDialogBuilder.setMessage(msg);
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            proprogressview.setVisibility(View.VISIBLE);
                            favPresenter.getFavouriteItems();
                        }else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivityFavourites.this);
                            alertDialogBuilder.setTitle("Warning");
                            alertDialogBuilder.setMessage("No Internet Access, Please try again ");
                            alertDialogBuilder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            return;
                                        }
                                    });
                            alertDialogBuilder.show();
                        }

                    }
                });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();

    } catch (WindowManager.BadTokenException e) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }


    }


    @OnClick(R.id.text_navigation_getfood)
    public void onClickNavigationGetFood(View view) {
        Intent intentSingup = new Intent(this, ActivityLanding.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }


    @OnClick(R.id.text_navigation_yourorders)
    public void onClickNavigationYourOrders(View view) {
        Intent intentSingup = new Intent(this, ActivityHistory.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }


    @OnClick(R.id.text_navigation_favourites)
    public void onClickNavigationFavourites(View view) {

    }



    @OnClick(R.id.text_navigation_profile)
    public void onClickNavigationProfile(View view) {
        Intent intentSingup = new Intent(this, ActivityProfile.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }

    @OnClick(R.id.text_navigation_settings)
    public void onClickNavigationSettings(View view) {

        Intent intentSingup = new Intent(this, ActivitySettings.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();


    }




    private void setNavigationMenuItems(){
        textViewNavigationGetFood.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationFavourites.setTextColor(getResources().getColor(R.color.colorAccent));
        textViewNavigationYourOrders.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationProfile.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationSettings.setTextColor(getResources().getColor(R.color.navigationTextColor));

    }


    private void setFavouriteRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFavorite.setLayoutManager(layoutManager);
        recyclerViewFavorite.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFavorite.setNestedScrollingEnabled(false);

    }







    @Override
    public void onBackPressed() {
        finish();
    }
}
