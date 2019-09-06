package emerge.project.onmeal.ui.activity.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntro;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.login.LoginInteractor;
import io.realm.Realm;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import io.sentry.SentryClientFactory;
import io.sentry.context.Context;
import io.sentry.event.UserBuilder;

public class ActivitySplash extends Activity implements SplashView {

    SplashPresenter splashPresenter;
    public Handler mHandler;
    private Runnable myRunnableActivityIntro;
    private Runnable myRunnableActivityLanding;
    private Runnable myRunnableActivityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashPresenter = new SplashPresenterImpli(this);
        mHandler = new Handler();
        runnable();
        splashPresenter.deleteLocalOrderData();

    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(myRunnableActivityIntro);
        mHandler.removeCallbacks(myRunnableActivityLanding);
        mHandler.removeCallbacks(myRunnableActivityLogin);
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        mHandler.removeCallbacks(myRunnableActivityIntro);
        mHandler.removeCallbacks(myRunnableActivityLanding);
        mHandler.removeCallbacks(myRunnableActivityLogin);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(myRunnableActivityIntro);
        mHandler.removeCallbacks(myRunnableActivityLanding);
        mHandler.removeCallbacks(myRunnableActivityLogin);
        super.onStop();
    }

    @Override
    public void userAvailable() {
        try {
            mHandler.postDelayed(myRunnableActivityLanding,2000);
        } catch (Exception ex) { }


    }

    @Override
    public void userNotAvailable() {
        try {
            mHandler.postDelayed(myRunnableActivityIntro,2000);
        } catch (Exception ex) { }

    }

    @Override
    public void userSingOut() {
        try {
            mHandler.postDelayed(myRunnableActivityLogin,2000);
        } catch (Exception ex) { }
    }

    @Override
    public void deletetedData() {
        splashPresenter.checkUser();
    }

    private void runnable(){
        myRunnableActivityIntro = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplash.this, FragmentActivityIntro.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(intent, bndlanimation);
                finish();
            }
        };

        myRunnableActivityLanding = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplash.this, ActivityLanding.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(intent, bndlanimation);
                finish();
            }
        };
        myRunnableActivityLogin = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplash.this, ActivityLogin.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivitySplash.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                startActivity(intent, bndlanimation);
                finish();
            }
        };


    }

}
