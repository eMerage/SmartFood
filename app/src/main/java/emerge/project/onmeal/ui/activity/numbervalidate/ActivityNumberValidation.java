package emerge.project.onmeal.ui.activity.numbervalidate;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.broadcastreceiver.SmsBroadcastReceiver;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.welcome.ActivityWelcome;
import emerge.project.onmeal.ui.dialog.CustomDialogOne;
import emerge.project.onmeal.ui.dialog.CustomDialogTwo;

public class ActivityNumberValidation extends Activity implements NumberValidateView, NumberValidateCodeListner {

    @BindView(R.id.proprogressview)
    MKLoader proprogressview;


    @BindView(R.id.edittext_otp_first_number)
    EditText editTextOptFirstNumber;
    @BindView(R.id.edittext_otp_secend_number)
    EditText editTextOptSecendNumber;
    @BindView(R.id.edittext_otp_third_number)
    EditText editTextOptThirdNumber;
    @BindView(R.id.edittext_otp_fourth_number)
    EditText editTextOptFourthNumber;

    @BindView(R.id.textview_phonenumber)
    TextView textviewPhonenumber;


    NumberValidatePresenter numberValidatePresenter;


    String userID;
    String smsCode;
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_validation);
        ButterKnife.bind(this);

        userID = getIntent().getStringExtra("USERID");
        smsCode = getIntent().getStringExtra("SMSCODE");
        phoneNumber = getIntent().getStringExtra("PHONENUMBER");


        textviewPhonenumber.setText(phoneNumber);
        SmsBroadcastReceiver.bindListener(this);

        numberValidatePresenter = new NumberValidatePresenterImpli(this);
        editTextTextChangeListener();

        ckeackSMSCode();
    }


    @OnClick(R.id.btn_resend)
    public void onClickResend(View view) {
        numberValidatePresenter.getNewOTPCode(userID);
    }


    @OnClick(R.id.button_validated)
    public void onClickValidated(View view) {

        bloackUserInteraction();


        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            if (smsCode == null) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Verified !");
                alertDialogBuilder.setMessage("You are already registered ,to continue please verify ");
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                proprogressview.setVisibility(View.VISIBLE);
                                numberValidatePresenter.getNewOTPCode(userID);
                            }
                        });
                alertDialogBuilder.show();

            } else {
                proprogressview.setVisibility(View.VISIBLE);
                numberValidatePresenter.validateOTPCode(smsCode, getCodeFromEdittext(), userID);
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }


    }

    private void ckeackSMSCode() {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        if (smsCode == null || smsCode.equals("")) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Verified !");
            alertDialogBuilder.setMessage("You are already registered ,to continue please verify");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            proprogressview.setVisibility(View.VISIBLE);
                            numberValidatePresenter.getNewOTPCode(userID);
                        }
                    });
            alertDialogBuilder.show();
        } else {

        }


    }


    private String getCodeFromEdittext() {

        return editTextOptFirstNumber.getText().toString() + editTextOptSecendNumber.getText().toString() +
                editTextOptThirdNumber.getText().toString() + editTextOptFourthNumber.getText().toString();
    }

    private void editTextTextChangeListener() {

        editTextOptFirstNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 1) {
                    editTextOptSecendNumber.requestFocus();
                } else {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextOptSecendNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 1) {
                    editTextOptThirdNumber.requestFocus();
                } else {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        editTextOptThirdNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 1) {
                    editTextOptFourthNumber.requestFocus();
                } else {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextOptFourthNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() >= 1) {


                } else {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    @Override
    public void showOTPCodeMissing() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Missing code !", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showOTPCodeInvalid() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "Invalid code !", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showOTPCodeValid() {
        proprogressview.setVisibility(View.INVISIBLE);
        Intent intentSingup = new Intent(this, ActivityWelcome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);

    }

    @Override
    public void showOTPCodeExpired(String code) {

        unBloackUserInteraction();
        editTextOptFirstNumber.setText("");
        editTextOptSecendNumber.setText("");
        editTextOptThirdNumber.setText("");
        editTextOptFourthNumber.setText("");

        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(), "The sms code has expired. We will re-send the verification code to try again !", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showOTPCodeServerError(String error) {
        proprogressview.setVisibility(View.INVISIBLE);
        unBloackUserInteraction();

        try {

            new AlertDialog.Builder(this)
                    .setMessage(error + ",Please try again !")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                bloackUserInteraction();
                                proprogressview.setVisibility(View.VISIBLE);

                                numberValidatePresenter.validateOTPCode(smsCode, getCodeFromEdittext(), userID);

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
    public void showNewOTPCode(String code) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        smsCode = code;
    }

    @Override
    public void showNewOTPCodeServerError(String error) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        new AlertDialog.Builder(this)
                .setMessage(error)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                            if (smsCode == null) {
                                proprogressview.setVisibility(View.VISIBLE);
                                numberValidatePresenter.getNewOTPCode(userID);
                            } else {
                                proprogressview.setVisibility(View.VISIBLE);
                                numberValidatePresenter.validateOTPCode(smsCode, getCodeFromEdittext(), userID);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                        }

                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();


    }


    @Override
    public void messageReceived(final String message) {



       /* if (message.isEmpty()) {
        } else {
            editTextOptFirstNumber.setText(message.substring(0, 1));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    editTextOptSecendNumber.setText(message.substring(1, 2));

                }
            }, 300);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    editTextOptThirdNumber.setText(message.substring(2, 3));

                }
            }, 600);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    editTextOptFourthNumber.setText(message.substring(3, 4));

                }
            }, 900);
        }*/
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
                        System.exit(0);
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

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
