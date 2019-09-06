package emerge.project.onmeal.ui.activity.singup;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;
import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.numbervalidate.ActivityNumberValidation;

public class ActivitySingup extends Activity implements SingUpView {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.editText_name)
    EditText editTextFullName;
    @BindView(R.id.editText_email)
    EditText editTextEmail;
    @BindView(R.id.editText_phone)
    EditText editTextPhoneNumber;
    @BindView(R.id.editText_password)
    EditText editTextPassword;
    @BindView(R.id.editText_password_confrim)
    EditText editTextPasswordConfrim;
    @BindView(R.id.button_singin)
    RelativeLayout btnSingIn;
    @BindView(R.id.divider)
    View viewLinnerConfrimPassword;
    @BindView(R.id.divider2)
    View viewLinnerPassword;


    private static final String PUSH_TOKEN = "pushToken";
    SingUpPresenter singUpPresenter;
    private GoogleSignInClient mGoogleSignInClient;
    EncryptedPreferences encryptedPreferences;
    String userPushTokenId = "";
    User userFormActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        ButterKnife.bind(this);

        userFormActivity = (User) getIntent().getSerializableExtra("USER");

        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();
        userPushTokenId = encryptedPreferences.getString(PUSH_TOKEN, "");

        singUpPresenter = new SingUpPresenterImpli(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        getUserDetailsToUI(userFormActivity);

    }

    @OnClick(R.id.button_singin)
    public void onClickSingIn(View view) {
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            singUpPresenter.checkFullNameValidation(editTextFullName.getText().toString());
        } else {
            Toast.makeText(this, getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void showFullNameEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Full Name is required !", Toast.LENGTH_LONG).show();
        editTextFullName.setError("Empty");
    }

    @Override
    public void showFullNameInvalid(String invalidReason) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, invalidReason, Toast.LENGTH_LONG).show();
        editTextFullName.setError("Invalid");

    }

    @Override
    public void showFullNameValid() {
        singUpPresenter.checkEmailValidation(editTextEmail.getText().toString());
    }


    @Override
    public void showEmailAddressEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Email is required !", Toast.LENGTH_LONG).show();
        editTextEmail.setError("Empty");
    }

    @Override
    public void showEmailAddressInvalid() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Email address not valid !", Toast.LENGTH_LONG).show();
        editTextEmail.setError("Invalid");
    }

    @Override
    public void showEmailAddressValid() {
        singUpPresenter.checkPhoneNumberValidation(editTextPhoneNumber.getText().toString());
    }


    @Override
    public void showPhoneNumberEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Phone Number is required !", Toast.LENGTH_LONG).show();
        editTextPhoneNumber.setError("Empty");
    }

    @Override
    public void showPhoneNumberInvalid(String invalidReason) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, invalidReason, Toast.LENGTH_LONG).show();
        editTextPhoneNumber.setError("Invalid");
    }

    @Override
    public void showPhoneNumberValid() {


        if (userFormActivity.getSocialMediaType().equals("L")) {
            singUpPresenter.checkPasswordValidation(editTextPassword.getText().toString(), editTextPasswordConfrim.getText().toString());
        } else {
            singUp();
        }
    }


    @Override
    public void showPasswordEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Password is requiredy !", Toast.LENGTH_LONG).show();
        editTextPassword.setError("Empty");
    }

    @Override
    public void showPasswordInvalid(String invalidReason) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), invalidReason, Toast.LENGTH_LONG).show();
        editTextPassword.setError("Invalid");
    }

    @Override
    public void showConfirmPasswordEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Confirm password is required !", Toast.LENGTH_LONG).show();
        editTextPasswordConfrim.setError("Empty");
    }

    @Override
    public void showPasswordDoesNotMatch() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Password does not match the confirm password !", Toast.LENGTH_LONG).show();

    }


    @Override
    public void showPasswordValid() {
        singUp();
    }


    private void getUserDetailsToUI(User userFormActivity) {

        if (userFormActivity.getSocialMediaType().equals("L")) {
            editTextEmail.setText(userFormActivity.getUserEmail());
            showUiElement();
        } else {
            editTextEmail.setText(userFormActivity.getUserEmail());
            editTextFullName.setText(userFormActivity.getUserName());
            hideUiElement();
        }

    }

    @Override
    public void showSingUpSuccess(String UserId, String phoneNumber, String smsVrificationCode) {
        unBloackUserInteraction();
        navigateToNumberVarification(UserId, phoneNumber, smsVrificationCode);
    }

    @Override
    public void showSingUpFail(String errorMsg) {

        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);

        try{


        new AlertDialog.Builder(this)
                .setMessage(errorMsg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            bloackUserInteraction();
                            proprogressview.setVisibility(View.VISIBLE);
                            singUp();
                        } else {
                            Toast.makeText(ActivitySingup.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();

    } catch (WindowManager.BadTokenException e) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
    }


    private void hideUiElement() {
        editTextPassword.setVisibility(View.INVISIBLE);
        editTextPasswordConfrim.setVisibility(View.INVISIBLE);
        viewLinnerConfrimPassword.setVisibility(View.INVISIBLE);
        viewLinnerPassword.setVisibility(View.INVISIBLE);
        editTextEmail.setEnabled(false);

    }

    private void showUiElement() {
        editTextPassword.setVisibility(View.VISIBLE);
        editTextPasswordConfrim.setVisibility(View.VISIBLE);
        viewLinnerConfrimPassword.setVisibility(View.VISIBLE);
        viewLinnerPassword.setVisibility(View.VISIBLE);
        editTextEmail.setEnabled(true);

    }

    @Override
    public void onBackPressed() {

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Intent intentSingup = new Intent(ActivitySingup.this, ActivityLogin.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
                        finish();
                        startActivity(intentSingup, bndlanimation);
                    }
                });


    }


    private void navigateToNumberVarification(String userID, String phoneNumber, String smsCode) {

        Intent intent = new Intent(this, ActivityNumberValidation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intent.putExtra("USERID", userID);
        intent.putExtra("SMSCODE", smsCode);
        intent.putExtra("PHONENUMBER", phoneNumber);
        finish();
        startActivity(intent, bndlanimation);

    }


    private void singUp() {

        User user = new User();
        user.setUserName(editTextFullName.getText().toString());
        user.setUserPassword(editTextPassword.getText().toString());
        user.setSocialMediaType(userFormActivity.getSocialMediaType());
        user.setUserTypeID("M");
        user.setUserGender("M");
        user.setUserEmail(editTextEmail.getText().toString());
        user.setUserPhoneNumber(editTextPhoneNumber.getText().toString());
        user.setuName(editTextFullName.getText().toString());
        user.setUserSocialMediaTokenId(userFormActivity.getUserSocialMediaTokenId());
        user.setUserPushTokenId(userPushTokenId);


        singUpPresenter.singUp(user);


    }


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
