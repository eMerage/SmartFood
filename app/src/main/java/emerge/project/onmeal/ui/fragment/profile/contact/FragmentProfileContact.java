package emerge.project.onmeal.ui.fragment.profile.contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.luseen.logger.Logger;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.home.HomePresenter;
import emerge.project.onmeal.ui.activity.home.HomePresenterImpli;
import emerge.project.onmeal.ui.activity.home.HomeViewByFood;
import emerge.project.onmeal.ui.adaptor.HomeFoodAdapter;
import emerge.project.onmeal.utils.entittes.Foodtems;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentProfileContact extends Fragment implements ProfileContactView {


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    @BindView(R.id.textview_phonenumber)
    TextView textviewPhonenumber;

    @BindView(R.id.textview_email)
    TextView textviewEmail;

    @BindView(R.id.textview_gender)
    TextView textviewGender;

    @BindView(R.id.textview_dob)
    TextView textviewDob;

    @BindView(R.id.relativeLayout_btn_edit)
    RelativeLayout relativeLayoutBtnEdit;

    @BindView(R.id.relativeLayout_btn_save)
    RelativeLayout relativeLayoutBtnSave;


    @BindView(R.id.edittext_gender)
    EditText edittextGender;

    @BindView(R.id.edittext_dob)
    EditText edittextDob;

    @BindView(R.id.divider1)
    View divider1;

    @BindView(R.id.divider2)
    View divider2;


    Realm realm;


    ProfileContactPresenter profileContactPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmenmt_profile_contact, container, false);
        ButterKnife.bind(this, rootView);

        profileContactPresenter = new ProfileContactPresenterImpli(this);


        if (NetworkAvailability.isNetworkAvailable(getContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);

            profileContactPresenter.getUserDetails();
        } else {
            Toast.makeText(getContext(), "No Internet Access !", Toast.LENGTH_LONG).show();
        }


        relativeLayoutBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textviewGender.setVisibility(View.INVISIBLE);
                textviewDob.setVisibility(View.INVISIBLE);
                relativeLayoutBtnSave.setVisibility(View.VISIBLE);
                edittextGender.setVisibility(View.VISIBLE);
                edittextDob.setVisibility(View.VISIBLE);
                divider1.setVisibility(View.VISIBLE);
                divider2.setVisibility(View.VISIBLE);

            }
        });


        relativeLayoutBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkAvailability.isNetworkAvailable(getContext())) {

                    bloackUserInteraction();
                    proprogressview.setVisibility(View.VISIBLE);
                    profileContactPresenter.updateUserDetails(edittextGender.getText().toString(), edittextDob.getText().toString());
                } else {
                    Toast.makeText(getContext(), "No Internet Access !", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }


    private void bloackUserInteraction() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    @Override
    public void getUserDetailsSuccessful(User user) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        textviewPhonenumber.setText(user.getUserPhoneNumber());
        textviewDob.setText(user.getDateOfBirth());
        textviewEmail.setText(user.getUserEmail());
        textviewGender.setText(user.getUserGender());
    }

    @Override
    public void getUserDetailsFail(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);


        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getContext())) {
                                profileContactPresenter.getUserDetails();
                            } else {
                                Toast.makeText(getContext(), "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void incorrectDOBFormat() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Incorrect format of the DOB", Toast.LENGTH_LONG).show();
    }

    @Override
    public void incorrectGenderFormat() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Incorrect format of the Gender", Toast.LENGTH_LONG).show();
    }

    @Override
    public void emptyInputs() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Gender & DOB are empty  ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getUpdateSuccessful() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        textviewGender.setVisibility(View.VISIBLE);
        textviewDob.setVisibility(View.VISIBLE);
        textviewGender.setText(edittextGender.getText().toString());
        textviewDob.setText(edittextDob.getText().toString());
        relativeLayoutBtnSave.setVisibility(View.GONE);
        edittextGender.setVisibility(View.GONE);
        edittextDob.setVisibility(View.GONE);
        divider1.setVisibility(View.GONE);
        divider2.setVisibility(View.GONE);

        Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_LONG).show();


    }

    @Override
    public void getUpdateFail(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getContext())) {
                                profileContactPresenter.updateUserDetails(edittextGender.getText().toString(), edittextDob.getText().toString());
                            } else {
                                Toast.makeText(getContext(), "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
