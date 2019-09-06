package emerge.project.onmeal.ui.activity.profile;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;
import emerge.project.onmeal.ui.adaptor.ProfileViewPagerAdapter;
import emerge.project.onmeal.ui.fragment.profile.address.FragmentProfileAddress;
import io.realm.Realm;

public class ActivityProfile extends FragmentActivity {



    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;


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


    @BindView(R.id.robotoLight3)
    TextView textName;

    @BindView(R.id.text_indicator_contact)
    TextView textIndicatorContact;
    @BindView(R.id.text_indicator_address)
    TextView textIndicatorAddress;

    @BindView(R.id.relativelayout_indicator_contact)
    RelativeLayout layoutIndicatorContact;

    @BindView(R.id.relativelayout_indicator_address)
    RelativeLayout layoutIndicatorAddress;


    @BindView(R.id.pager)
    ViewPager viewPager;

    FragmentManager mapFragment;
    FragmentProfileAddress fragmentProfileAddress;

    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setNavigationMenuItems();
        realm = Realm.getDefaultInstance();




        assert viewPager != null;
        viewPager.setAdapter(new ProfileViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    indicatorContact();
                } else {
                    indicatorAddress();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });



        User user = realm.where(User.class).findFirst();
        textName.setText(user.getUserName());

    }




    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }



    @OnClick(R.id.text_indicator_contact)
    public void onClickIndicatorContact(View view) {
        indicatorContact();
    }

    @OnClick(R.id.text_indicator_address)
    public void onClickIndicatorAddress(View view) {
        indicatorAddress();
    }



    private void indicatorContact(){

        textIndicatorContact.setTextColor(getResources().getColor(R.color.colorTextWhite));
        layoutIndicatorContact.setVisibility(View.VISIBLE);

        textIndicatorAddress.setTextColor(getResources().getColor(R.color.homeIndicatorTextColor));
        layoutIndicatorAddress.setVisibility(View.INVISIBLE);

        viewPager.setCurrentItem(getItem(-1), true);

    }

    private void indicatorAddress(){

        textIndicatorAddress.setTextColor(getResources().getColor(R.color.colorTextWhite));
        layoutIndicatorAddress.setVisibility(View.VISIBLE);

        textIndicatorContact.setTextColor(getResources().getColor(R.color.homeIndicatorTextColor));
        layoutIndicatorContact.setVisibility(View.INVISIBLE);

        viewPager.setCurrentItem(getItem(+1), true);

    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    private void setNavigationMenuItems() {
        textViewNavigationGetFood.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationFavourites.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationYourOrders.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationProfile.setTextColor(getResources().getColor(R.color.colorAccent));
        textViewNavigationSettings.setTextColor(getResources().getColor(R.color.navigationTextColor));

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

        Intent intentSingup = new Intent(this, ActivityFavourites.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }


    @OnClick(R.id.text_navigation_profile)
    public void onClickNavigationProfile(View view) {

    }

    @OnClick(R.id.text_navigation_settings)
    public void onClickNavigationSettings(View view) {
        Intent intentSingup = new Intent(this, ActivitySettings.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
