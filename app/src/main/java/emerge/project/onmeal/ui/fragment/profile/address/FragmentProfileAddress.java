package emerge.project.onmeal.ui.fragment.profile.address;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.favorites.ActivityFavourites;
import emerge.project.onmeal.ui.activity.landingsetlocation.ActivitySetLocation;
import emerge.project.onmeal.ui.adaptor.ProfileAddressAdapter;
import emerge.project.onmeal.utils.entittes.AddressItems;


public class FragmentProfileAddress extends Fragment implements ProfileAddressView {


    @BindView(R.id.recyclerView_addres)
    RecyclerView recyclerViewAddres;

    @BindView(R.id.proprogressview)
    MKLoader proprogressview;


    ProfileAddressAdapter profileAddressAdapter;
    ProfileAddressPresenter profileAddressPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmenmt_profile_address, container, false);
        ButterKnife.bind(this, rootView);

        setRecycalView();

        profileAddressPresenter = new ProfileAddressPresenterImpli(this);

        if (NetworkAvailability.isNetworkAvailable(getContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            profileAddressPresenter.getAddress();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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


        return rootView;
    }


    @OnClick(R.id.imageView17)
    public void onClickAdd(View view) {

        Intent intentSingup = new Intent(getContext(), ActivitySetLocation.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
        getActivity().finish();

    }


    private void setRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAddres.setLayoutManager(layoutManager);
        recyclerViewAddres.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAddres.setNestedScrollingEnabled(false);

    }


    @Override
    public void getAddressEmpty() {
        proprogressview.setVisibility(View.GONE);
        Toast.makeText(getContext(), "No Address to your account", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getAddressSuccessful(ArrayList<AddressItems> addressItemsArrayList) {
        proprogressview.setVisibility(View.GONE);
        profileAddressAdapter = new ProfileAddressAdapter(getContext(), addressItemsArrayList, this);
        recyclerViewAddres.setAdapter(profileAddressAdapter);


    }

    @Override
    public void getAddressFail(String msg) {
        proprogressview.setVisibility(View.GONE);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            proprogressview.setVisibility(View.VISIBLE);
                            if (NetworkAvailability.isNetworkAvailable(getContext())) {
                                proprogressview.setVisibility(View.VISIBLE);
                                profileAddressPresenter.getAddress();

                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAddressStart() {
        bloackUserInteraction();
        proprogressview.setVisibility(View.VISIBLE);


    }


    @Override
    public void deleteAddressSuccessful() {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        if (NetworkAvailability.isNetworkAvailable(getContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            profileAddressPresenter.getAddress();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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
    public void deleteAddressFail(final String addressID, String msg) {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();


        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getContext())) {
                                profileAddressPresenter.deleteAddress(addressID);
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


    private void bloackUserInteraction() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
}
