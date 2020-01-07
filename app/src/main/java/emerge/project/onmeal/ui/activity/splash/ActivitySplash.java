package emerge.project.onmeal.ui.activity.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntro;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.login.LoginInteractor;
import emerge.project.onmeal.utils.entittes.UpdateToken;
import io.realm.Realm;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.UserBuilder;

public class ActivitySplash extends Activity implements SplashView {

    SplashPresenter splashPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashPresenter = new SplashPresenterImpli(this);




        splashPresenter.deleteLocalOrderData();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public void userAvailable() {
        Intent intent = new Intent(ActivitySplash.this, ActivityLanding.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();
    }

    @Override
    public void userNotAvailable() {
        Intent intent = new Intent(ActivitySplash.this, FragmentActivityIntro.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();
    }

    @Override
    public void userSingOut() {
        Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();
    }

    @Override
    public void deletetedData() {
        splashPresenter.updatePushTokenAndAppVersion(this);

    }

    @Override
    public void updateStatus(Boolean status, final UpdateToken updateToken) {

        if(status){
            splashPresenter.checkUser();
        }else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("App Update");
            alertDialogBuilder.setMessage(updateToken.getError().getErrorMessage());
            if(updateToken.getError().getErrorCode().equals("CE")){
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ActivitySplash.this, "You can not processed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });


            }else {
                alertDialogBuilder.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateToken.getAppUrl()));
                                startActivity(browserIntent);

                                return;
                            }
                        });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ActivitySplash.this, "You can not processed", Toast.LENGTH_SHORT).show();

                        return;
                    }
                });

            }

            alertDialogBuilder.show();


        }

    }




}
