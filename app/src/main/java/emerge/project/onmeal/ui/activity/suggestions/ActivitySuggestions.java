package emerge.project.onmeal.ui.activity.suggestions;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.history.ActivityHistory;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.profile.ActivityProfile;
import emerge.project.onmeal.ui.activity.settings.ActivitySettings;

public class ActivitySuggestions extends Activity implements SuggestionView{


    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;


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


    @BindView(R.id.robotoMedium0)
    TextView edirtextHintResturent;

    @BindView(R.id.edittext_restaurant)
    EditText edittextRestaurant;


    @BindView(R.id.robotoMedium2)
    TextView edirtextHintLocation;

    @BindView(R.id.edittext_location)
    EditText edittextLocation;


    @BindView(R.id.edittext_note)
    EditText edittextNote;



    SuggestionPresenter suggestionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        ButterKnife.bind(this);

        setNavigationMenuItems();

        suggestionPresenter = new SuggestionPresenterImpli(this);





    }



    @OnClick(R.id.relativelayout_menu)
    public void onClickSliderMenue(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }



    @OnClick(R.id.img_btn_send)
    public void onClickSend(View view) {

        String name = edittextRestaurant.getText().toString();
        String city =edittextLocation.getText().toString();
        String suggetion =edittextNote.getText().toString();


        if(name.isEmpty() || name.equals("")){
            Toast.makeText(this, "Restaurant name is empty", Toast.LENGTH_SHORT).show();
        }else if(city.isEmpty() || city.equals("")){
            Toast.makeText(this, "Restaurant city is empty", Toast.LENGTH_SHORT).show();
        }else {

            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                proprogressview.setVisibility(View.VISIBLE);
                bloackUserInteraction();

                suggestionPresenter.sendSuggestion(name,city,suggetion);

            } else {
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

    }




    @OnTextChanged(value = R.id.edittext_restaurant, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedRestaurant(CharSequence text) {
        if (text.length() == 0) {
            edirtextHintResturent.setVisibility(View.INVISIBLE);
            edirtextHintResturent.setEnabled(false);
        } else {
            if (edirtextHintResturent.isEnabled()) {
            } else {
                edirtextHintResturent.setVisibility(View.VISIBLE);
                edirtextHintResturent.setEnabled(true);
            }
        }
    }


    @OnTextChanged(value = R.id.edittext_location, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void textChangedLocation(CharSequence text) {
        if (text.length() == 0) {
            edirtextHintLocation.setVisibility(View.INVISIBLE);
            edirtextHintLocation.setEnabled(false);
        } else {
            if (edirtextHintLocation.isEnabled()) {
            } else {
                edirtextHintLocation.setVisibility(View.VISIBLE);
                edirtextHintLocation.setEnabled(true);
            }
        }
    }


    private void setNavigationMenuItems(){
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
        Intent intentSingup = new Intent(this, ActivitySettings.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        finish();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void sendSuggestionSuccess() {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        Toast.makeText(this, "Suggestion sent successfully", Toast.LENGTH_SHORT).show();
        edittextRestaurant.setText("");
        edittextLocation.setText("");
        edittextNote.setText("");

    }

    @Override
    public void sendSuggestionFail() {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        try{

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Warning");
        alertDialogBuilder.setMessage("Suggestion sent fail ,Please try again");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            proprogressview.setVisibility(View.VISIBLE);
                            bloackUserInteraction();

                            String name = edittextRestaurant.getText().toString();
                            String city =edittextLocation.getText().toString();
                            String suggetion =edittextNote.getText().toString();

                            suggestionPresenter.sendSuggestion(name,city,suggetion);
                        }else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActivitySuggestions.this);
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
        Toast.makeText(this, "Suggestion sent fail ,Please try again", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
    }



    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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
        suggestionPresenter.signOut(this);

    }
}
