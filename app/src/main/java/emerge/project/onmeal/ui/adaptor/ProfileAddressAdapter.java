package emerge.project.onmeal.ui.adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.landing.LandingPresenter;
import emerge.project.onmeal.ui.activity.landing.LandingPresenterImpli;
import emerge.project.onmeal.ui.activity.landing.LandingView;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressPresenter;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressPresenterImpli;
import emerge.project.onmeal.ui.fragment.profile.address.ProfileAddressView;
import emerge.project.onmeal.utils.entittes.AddressItems;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class ProfileAddressAdapter extends RecyclerView.Adapter<ProfileAddressAdapter.MyViewHolder>   {

    Context mContext;
    ArrayList<AddressItems> addressItemsArrayList;
    ProfileAddressPresenter profileAddressPresenter;


    public ProfileAddressAdapter(Context mContext, ArrayList<AddressItems> item,ProfileAddressView profileAddressView) {
        this.mContext = mContext;
        this.addressItemsArrayList = item;

        profileAddressPresenter = new ProfileAddressPresenterImpli(profileAddressView);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_profile_address, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       final AddressItems addressItems = addressItemsArrayList.get(position);
       final String address=addressItems.getAddressNumber()+" "+addressItems.getAddressRoad();

        if(addressItems.getAddressName().isEmpty() || addressItems.getAddressName().equals("")){
            holder.textViewAddressName.setText(addressItems.getAddressCity());
        }else {
            holder.textViewAddressName.setText(addressItems.getAddressName());
        }


        holder.textViewAddressCity.setText(addressItems.getAddressCity());
        holder.textViewAddress.setText(address);

        holder.relativeLayoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (NetworkAvailability.isNetworkAvailable(mContext)) {
                    profileAddressPresenter.deleteAddress(addressItems.getAddressId());
                }else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
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


    }

    @Override
    public int getItemCount() {
        return addressItemsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.textview_addressname)
        TextView textViewAddressName;

        @BindView(R.id.textview_addres)
        TextView textViewAddress;

        @BindView(R.id.textview_addres_city)
        TextView textViewAddressCity;



        @BindView(R.id.relativeLayout_delete)
        RelativeLayout relativeLayoutDelete;





        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
