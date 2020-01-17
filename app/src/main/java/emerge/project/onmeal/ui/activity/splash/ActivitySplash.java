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
import emerge.project.onmeal.service.network.NetworkAvailability;
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

    Handler m_handler;
    private Runnable m_runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashPresenter = new SplashPresenterImpli(this);




        splashPresenter.deleteLocalOrderData();

    }

    @Override
    protected void onDestroy() {
        m_handler.removeCallbacks(m_runnable);
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        m_handler.removeCallbacks(m_runnable);
        super.onPause();
    }

    @Override
    protected void onStop() {
        m_handler.removeCallbacks(m_runnable);
        super.onStop();
    }

    @Override
    public void userAvailable() {
        final Intent intent = new Intent(ActivitySplash.this, ActivityLanding.class);
        final Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        m_handler =  new Handler();
        m_runnable = new  Runnable() {
            public void run()  {
                startActivity(intent, bndlanimation);
                finish();
            }
        };

        try {
            m_handler.postDelayed(m_runnable,2000);
        } catch (Exception ex) { }


    }

    @Override
    public void userNotAvailable() {


        final Intent intent = new Intent(ActivitySplash.this, FragmentActivityIntro.class);
        final Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        m_handler =  new Handler();
        m_runnable = new  Runnable() {
            public void run()  {
                startActivity(intent, bndlanimation);
                finish();
            }
        };

        try {
            m_handler.postDelayed(m_runnable,2000);
        } catch (Exception ex) { }



    }

    @Override
    public void userSingOut() {

        final Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
        final Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
        m_handler =  new Handler();
        m_runnable = new  Runnable() {
            public void run()  {
                startActivity(intent, bndlanimation);
                finish();
            }
        };

        try {
            m_handler.postDelayed(m_runnable,2000);
        } catch (Exception ex) { }


    }

    @Override
    public void deletetedData() {
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            splashPresenter.updatePushTokenAndAppVersion(this);
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

    @Override
    public void updateStatus(Boolean status, final UpdateToken updateToken) {

        if(status){
            splashPresenter.checkUser();
        }else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("App Update");
            alertDialogBuilder.setMessage(updateToken.getError().getErrDescription());



            if((updateToken.getError().getErrCode().equals("CE")) || (updateToken.getError().getErrCode().equals("SYSE")) ){
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
