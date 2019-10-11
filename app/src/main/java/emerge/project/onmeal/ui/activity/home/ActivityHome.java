package emerge.project.onmeal.ui.activity.home;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.landingsetlocation.ActivitySetLocation;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.ui.adaptor.HomeFavouriteAdapter;
import emerge.project.onmeal.ui.adaptor.HomeViewPagerAdapter;
import emerge.project.onmeal.ui.fragment.home.FragmentHomeFood;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import io.realm.Realm;
import io.realm.RealmResults;

public class ActivityHome extends FragmentActivity implements HomeView{



    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;

    @BindView(R.id.recyclerView_favorite)
    RecyclerView recyclerViewFavorite;


    @BindView(R.id.text_home_indicator_food)
    TextView textIndicatorFood;
    @BindView(R.id.text_home_indicator_outlet)
    TextView textIndicatorOutlet;

    @BindView(R.id.relativelayout_indicator_food)
    RelativeLayout layoutIndicatorFood;

    @BindView(R.id.relativelayout_indicator_outlet)
    RelativeLayout layoutIndicatorOutlet;



    HomePresenter homePresenter;
    HomeFavouriteAdapter homeFavouriteAdapter;

    @BindView(R.id.pager)
    ViewPager viewPager;


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


    @BindView(R.id.textView_selectedaddress)
    EditText serachText;




    @BindView(R.id.relativelayout_logout)
    RelativeLayout relativelayoutNavigationLogout;
    // navigation menu


    HomeViewByFood homeViewByFood;
    HomeViewByOutlet homeViewByOutlet;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        homePresenter = new HomePresenterImpli(this);
        setFavouriteRecycalView();


        setNavigationMenuItems();





        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            homePresenter.getFavouriteItems();
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

        assert viewPager != null;
        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    indicatorFood();
                } else {
                    indicatorOutlet();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });


    }



    @OnTextChanged(R.id.textView_selectedaddress) void onTextChanged(CharSequence text) {
        if(text.length()==0)
            serachFoodAndOutlet("");
    }


    @OnClick(R.id.relativelayout_address)
    public void onClicSerach(View view) {

        String seracttext;
        if(serachText.getText().toString().isEmpty()){
            seracttext = "";
        }else {
            seracttext= serachText.getText().toString();
        }

        serachFoodAndOutlet(seracttext);


    }

    public void serachFoodAndOutlet(String text){

        HomePresenter  homePresenterForFoodSearch = new HomePresenterImpli(homeViewByFood);
        homePresenterForFoodSearch.getMainFood(this,text);


        HomePresenter  homePresenterForOutletSearch = new HomePresenterImpli(homeViewByOutlet);
        homePresenterForOutletSearch.getOutlet(this,text);

    }


    public void setHomeViewByFoodView(HomeViewByFood homeviewbyfood){
        homeViewByFood = homeviewbyfood;
    }

    public void setHomeViewByOutletView(HomeViewByOutlet homeviewbyoutlet){
        homeViewByOutlet = homeviewbyoutlet;
    }



    @OnClick(R.id.imageView_filter)
    public void onClicFilter(View view) {
        filterDialod();
    }

    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.text_home_indicator_food)
    public void onClickIndicatorFood(View view) {
        indicatorFood();
    }

    @OnClick(R.id.text_home_indicator_outlet)
    public void onClickIndicatorOutlet(View view) {
        indicatorOutlet();
    }



    @Override
    public void getFavouriteItemsEmpty() {


    }

    @Override
    public void getFavouriteItemsSuccessful(ArrayList<HomeFavouriteItems> favouriteItemsArrayList) {

        homeFavouriteAdapter = new HomeFavouriteAdapter(this, favouriteItemsArrayList);
        recyclerViewFavorite.setAdapter(homeFavouriteAdapter);
    }

    @Override
    public void getFavouriteItemsFail(String msg) {

    }

    private void indicatorFood(){
        textIndicatorFood.setTextColor(getResources().getColor(R.color.colorTextWhite));
        layoutIndicatorFood.setVisibility(View.VISIBLE);

        textIndicatorOutlet.setTextColor(getResources().getColor(R.color.homeIndicatorTextColor));
        layoutIndicatorOutlet.setVisibility(View.INVISIBLE);

        viewPager.setCurrentItem(getItem(-1), true);

    }

    private void indicatorOutlet(){

        textIndicatorOutlet.setTextColor(getResources().getColor(R.color.colorTextWhite));
        layoutIndicatorOutlet.setVisibility(View.VISIBLE);

        textIndicatorFood.setTextColor(getResources().getColor(R.color.homeIndicatorTextColor));
        layoutIndicatorFood.setVisibility(View.INVISIBLE);

        viewPager.setCurrentItem(getItem(+1), true);

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void setFavouriteRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFavorite.setLayoutManager(layoutManager);
        recyclerViewFavorite.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFavorite.setNestedScrollingEnabled(false);

    }
    private void setNavigationMenuItems(){
        textViewNavigationGetFood.setTextColor(getResources().getColor(R.color.colorAccent));
        textViewNavigationFavourites.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationYourOrders.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationProfile.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationSettings.setTextColor(getResources().getColor(R.color.navigationTextColor));

    }

    @OnClick(R.id.text_navigation_getfood)
    public void onClickNavigationGetFood(View view) {

    }


    @OnClick(R.id.text_navigation_yourorders)
    public void onClickNavigationYourOrders(View view) {
        Intent intentSingup = new Intent(this, ActivityHistory.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);

    }


    @OnClick(R.id.text_navigation_favourites)
    public void onClickNavigationFavourites(View view) {

        Intent intentSingup = new Intent(this, ActivityFavourites.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
    }



    @OnClick(R.id.text_navigation_profile)
    public void onClickNavigationProfile(View view) {
        Intent intentSingup = new Intent(this, ActivityProfile.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);

    }

    @OnClick(R.id.text_navigation_settings)
    public void onClickNavigationSettings(View view) {
        Intent intentSingup = new Intent(this, ActivitySettings.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);

    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void filterDialod(){

        Dialog dialogTimeSlots = new Dialog(this);
        dialogTimeSlots.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTimeSlots.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogTimeSlots.setContentView(R.layout.dialog_home_filter);
        dialogTimeSlots.setCancelable(true);

        dialogTimeSlots.show();



    }

    @Override
    public void signOutSuccess() {

        Intent intent = new Intent(this, ActivityLogin.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intent, bndlanimation);

    }


    @OnClick(R.id.relativelayout_logout)
    public void onSingOut(View view) {
        homePresenter.signOut(this);

    }



    @Override
    public void onBackPressed() {
        Intent intentSingup = new Intent(this, ActivityLanding.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();


    }
}
