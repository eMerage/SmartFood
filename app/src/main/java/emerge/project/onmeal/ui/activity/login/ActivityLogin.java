package emerge.project.onmeal.ui.activity.login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.tuyenmonkey.mkloader.MKLoader;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.landing.ActivityLanding;
import emerge.project.onmeal.ui.activity.numbervalidate.ActivityNumberValidation;
import emerge.project.onmeal.ui.activity.singin.ActivitySingIn;
import emerge.project.onmeal.ui.activity.singup.ActivitySingup;
import emerge.project.onmeal.ui.dialog.CustomDialogOne;
import emerge.project.onmeal.ui.dialog.CustomDialogTwo;

public class ActivityLogin extends Activity implements LoginView {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.edittext_user_email)
    EditText editTextEmail;


    private static final int RC_SIGN_IN = 9001;


    //google login
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient googleApiClient;


    //facebook login
    CallbackManager callbackManager;
    LoginManager loginManager;
    AccessToken mAccessToken;


    LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        facebookLoginButtonRegisterCallback();


        loginPresenter = new LoginPresenterImpli(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();

    }

    //Next Button Click
    @OnClick(R.id.button_login)
    public void onClickLogin(View view) {
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            loginPresenter.checkLocalSingInValidation(ActivityLogin.this, editTextEmail.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showLoginEmailAddressEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Email is required !", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoginEmailAddressInvalid() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Email not valid !", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showEmailExist(User user) {
        Intent intentSingup = new Intent(this, ActivitySingIn.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intentSingup.putExtra("USER", user);
        finish();
        startActivity(intentSingup, bndlanimation);
    }

    @Override
    public void showEmailExistSocial() {
        proprogressview.setVisibility(View.INVISIBLE);
        Intent intentLanding = new Intent(ActivityLogin.this, ActivityLanding.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentLanding, bndlanimation);

    }

    @Override
    public void showEmailNotExist(User user) {
        Intent intentSingup = new Intent(this, ActivitySingup.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intentSingup.putExtra("USER", user);
        finish();
        startActivity(intentSingup, bndlanimation);
    }

    @Override
    public void localSingInValidationErorr(String error) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

        try {

            new AlertDialog.Builder(this)
                    .setMessage(error)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                bloackUserInteraction();
                                proprogressview.setVisibility(View.VISIBLE);
                                loginPresenter.checkLocalSingInValidation(ActivityLogin.this, editTextEmail.getText().toString());
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .create()
                    .show();

        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void userNotValidateLocal(User user) {

        Intent intent = new Intent(this, ActivityNumberValidation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intent.putExtra("USERID", user.getUserId());
        intent.putExtra("SMSCODE", "");
        finish();
        startActivity(intent, bndlanimation);
    }


    //Facebook Button Click
    @OnClick(R.id.relativelayout_facebook_singin)
    public void onClickFacebookSingUp(View view) {
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            FacebookSingUp();


        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showAlreadySingUpWithFacebook() {
        proprogressview.setVisibility(View.INVISIBLE);
        Intent intentLanding = new Intent(this, ActivityLanding.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentLanding, bndlanimation);

    }

    @Override
    public void showNewSingUpWithFacebook(User user) {
        proprogressview.setVisibility(View.INVISIBLE);
        Intent intentLanding = new Intent(this, ActivitySingup.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intentLanding.putExtra("USER", user);
        finish();
        startActivity(intentLanding, bndlanimation);
    }

    @Override
    public void showFacebookSingInValidationErorr(String error) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();



        try {
        new AlertDialog.Builder(this)
                .setMessage(error)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            bloackUserInteraction();
                            FacebookSingUp();
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();

        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void userNotValidateFacebook(User user) {
        Intent intent = new Intent(this, ActivityNumberValidation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intent.putExtra("USERID", user.getUserId());
        intent.putExtra("SMSCODE", user.getSmsCode());
        finish();
        startActivity(intent, bndlanimation);
    }

    private void facebookLoginButtonRegisterCallback() {

            loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mAccessToken = loginResult.getAccessToken();
                getFacebookUserProfile(mAccessToken);
            }

            @Override
            public void onCancel() {

                unBloackUserInteraction();
            }

            @Override
            public void onError(FacebookException e) {
                proprogressview.setVisibility(View.INVISIBLE);
                unBloackUserInteraction();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getFacebookUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                JSONObject json = response.getJSONObject();
                proprogressview.setVisibility(View.VISIBLE);
                loginPresenter.checkFacebookSingInValidation(ActivityLogin.this, json);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    //Google Button Click
    @OnClick(R.id.relativelayout_google_singin)
    public void onClickGoogleSingUp(View view) {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            bloackUserInteraction();
            signIn();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            OptionalPendingResult<GoogleSignInResult> optPenRes = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (optPenRes.isDone()) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            } else {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }

        } else {

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                loginPresenter.checkGoogleSingInValidation(ActivityLogin.this, account);
            } else {
                unBloackUserInteraction();
                proprogressview.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Google Sing In fail,Please Try again !", Toast.LENGTH_LONG).show();
            }

        } catch (ApiException e) {
            proprogressview.setVisibility(View.INVISIBLE);
            unBloackUserInteraction();


            Toast.makeText(getApplicationContext(), "Google Sing In fail,Please Try again !", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void showAlreadySingUpWithGoogle() {
        proprogressview.setVisibility(View.INVISIBLE);
        Intent intentLanding = new Intent(this, ActivityLanding.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentLanding, bndlanimation);
    }

    @Override
    public void showNewSingUpWithGoogle(User user) {


        proprogressview.setVisibility(View.INVISIBLE);
        Intent intentLanding = new Intent(this, ActivitySingup.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intentLanding.putExtra("USER", user);
        finish();
        startActivity(intentLanding, bndlanimation);
    }

    @Override
    public void showGoogleSingInValidationErorr(String error) {
        proprogressview.setVisibility(View.INVISIBLE);
        unBloackUserInteraction();
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        try{

        new AlertDialog.Builder(this)
                .setMessage(error)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            proprogressview.setVisibility(View.VISIBLE);
                            bloackUserInteraction();
                            signIn();
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                        }


                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    } catch (WindowManager.BadTokenException e) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    }

    @Override
    public void userNotValidateGoogle(User user) {


        Intent intent = new Intent(this, ActivityNumberValidation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intent.putExtra("USERID", user.getUserId());
        intent.putExtra("SMSCODE", user.getSmsCode());
        finish();
        startActivity(intent, bndlanimation);
    }




    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            onStatEmailConfirmation(account);
        } else {
            OptionalPendingResult<GoogleSignInResult> optPenRes = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
            if (optPenRes.isDone()) {
                GoogleSignInResult result = optPenRes.get();
                GoogleSignInAccount acct = result.getSignInAccount();
                onStatEmailConfirmation(acct);
            } else {
                optPenRes.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        GoogleSignInAccount acct = googleSignInResult.getSignInAccount();
                        onStatEmailConfirmation(acct);

                    }
                });
            }
        }

    }


    @Override
    public void onBackPressed() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit!");
        alertDialogBuilder.setMessage("Do you really want to exit ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();


    }


    private void onStatEmailConfirmation(final GoogleSignInAccount account) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Google Sing in !");
        alertDialogBuilder.setMessage("Do you want to continue with this " + account.getEmail() + " ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        loginPresenter.checkGoogleSingInValidation(ActivityLogin.this, account);
                    }
                });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                singOut();
                return;
            }
        });
        alertDialogBuilder.show();


    }

    private void FacebookSingUp() {
        loginManager.logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

    }

    private void singOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Sign Out !", Toast.LENGTH_LONG).show();

                    }
                });
    }


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}

