package emerge.project.onmeal.ui.activity.settings;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.ui.activity.contact.ActivityContact;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.suggestions.ActivitySuggestions;
import emerge.project.onmeal.ui.activity.termsandconditions.ActivityTerms;
import emerge.project.onmeal.ui.dialog.CustomDialogOne;
import emerge.project.onmeal.ui.dialog.CustomDialogTwo;

public class ActivitySettings extends Activity {


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



    @BindView(R.id.text_changepassword)
    TextView textViewChangepassword;

    @BindView(R.id.text_faq)
    TextView textViewFAQ;

    @BindView(R.id.text_terms)
    TextView textViewTerms;

    @BindView(R.id.text_suggetions)
    TextView textViewSuggetions;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setNavigationMenuItems();
    }

    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }


    @OnClick(R.id.text_suggetions)
    public void onClickSuggetions(View view) {

        Intent intentSingup = new Intent(this, ActivitySuggestions.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);

    }



    @OnClick(R.id.img_btn_contact)
    public void onClickContact(View view) {


       Intent intentSingup = new Intent(this, ActivityContact.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);

    }



    @OnClick(R.id.text_terms)
    public void onClickTerms(View view) {
        Intent intentSingup = new Intent(this, ActivityTerms.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);

    }



    private void setNavigationMenuItems() {
        textViewNavigationGetFood.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationFavourites.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationYourOrders.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationProfile.setTextColor(getResources().getColor(R.color.navigationTextColor));
        textViewNavigationSettings.setTextColor(getResources().getColor(R.color.colorAccent));

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
        Intent intentSingup = new Intent(this, ActivityProfile.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();
    }

    @OnClick(R.id.text_navigation_settings)
    public void onClickNavigationSettings(View view) {


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
