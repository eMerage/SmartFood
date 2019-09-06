package emerge.project.onmeal.ui.activity.singin;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.activity.welcome.ActivityWelcome;

public class ActivitySingIn extends Activity implements SingInView{


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;


    @BindView(R.id.edittext_user_password)
    EditText editTextPassword;
    @BindView(R.id.edittext_user_email)
    EditText editTextEmail;


    SingInPresenter singInPresenter;
    User userFormActivity;
    
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        ButterKnife.bind(this);
        
        userFormActivity = (User) getIntent().getSerializableExtra("USER");
        singInPresenter = new SingInPresenterImpli(this);

        editTextEmail.setText(userFormActivity.getUserEmail());
        
     
        
        
    }


    @OnClick(R.id.button_login)
    public void onClickSingUp(View view) {
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            
            singInPresenter.checkLoginValidation(editTextEmail.getText().toString(), editTextPassword.getText().toString());

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }
        
    }


    @Override
    public void ShowEmailAddressEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Email is required !", Toast.LENGTH_LONG).show();
        editTextEmail.setError("Empty");
    }

    @Override
    public void ShowEmailAddressInvalid() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Email address not valid !", Toast.LENGTH_LONG).show();
        editTextEmail.setError("Invalid");
    }

    @Override
    public void ShowPasswordEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Password is requiredy !", Toast.LENGTH_LONG).show();
        editTextPassword.setError("Empty");
    }

    @Override
    public void ShowPasswordInvalid(String invalidReason) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), invalidReason, Toast.LENGTH_LONG).show();
        editTextPassword.setError("Invalid");
    }

    @Override
    public void ShowLoginValidationSuccessful() {
        proprogressview.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, ActivityWelcome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intent, bndlanimation);
    }

    @Override
    public void ShowLoginValidationFail(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        try{


        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            bloackUserInteraction();
                            proprogressview.setVisibility(View.VISIBLE);
                            singInPresenter.checkLoginValidation(editTextEmail.getText().toString(), editTextPassword.getText().toString());

                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intentSingup = new Intent(ActivitySingIn.this, ActivityLogin.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);

    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
