package emerge.project.onmeal.ui.fragment.intro;


import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.Agrement;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntroPresenter;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntroPresenterImpli;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntroView;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import io.realm.Realm;


@SuppressLint("ValidFragment")
public class StepFour extends Fragment {

    Realm realm;
    static final int MY_PERMISSIONS_REQUEST_READ_SMS = 100;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 101;

    public StepFour() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_intro_stepfour, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Dialog dialogBox = new Dialog(getContext());

        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dialog_fragmentintrostepfour_terms_conditions);
        dialogBox.setCancelable(false);


        RelativeLayout btn_agree = (RelativeLayout) dialogBox.findViewById(R.id.relativelayout_aggreed);
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBox != null) {
                    dialogBox.dismiss();
                } else {

                }

                realm.executeTransaction(new Realm.Transaction() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void execute(Realm bgRealm) {
                        final Long AgrementList = bgRealm.where(Agrement.class).count();
                        Agrement agrement = bgRealm.createObject(Agrement.class, (AgrementList.intValue() + 1));
                        agrement.setAgrementApproved(true);


                        Intent intent = new Intent(getContext(), ActivityLogin.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
                        startActivity(intent, bndlanimation);
                        getActivity().finish();


                       // checkSMS();

                    }
                });
            }
        });


        try {
            dialogBox.show();
        } catch (Exception ec) {
            dialogBox.show();
        }


    }


}
