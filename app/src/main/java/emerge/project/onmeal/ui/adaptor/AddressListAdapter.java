package emerge.project.onmeal.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.onmeal.R;
import emerge.project.onmeal.ui.activity.landing.LandingPresenter;
import emerge.project.onmeal.ui.activity.landing.LandingPresenterImpli;
import emerge.project.onmeal.ui.activity.landing.LandingView;
import emerge.project.onmeal.utils.entittes.AddressItems;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.MyViewHolder>   {

    Context mContext;
    ArrayList<AddressItems> addressItemsArrayList;


    LandingPresenter landingPresenter;



    public AddressListAdapter(Context mContext, ArrayList<AddressItems> item,LandingView landingView) {
        this.mContext = mContext;
        this.addressItemsArrayList = item;
        landingPresenter = new LandingPresenterImpli(landingView);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_landing_address, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddressItems addressItems = addressItemsArrayList.get(position);

        final String address=addressItems.getAddressNumber()+" "+addressItems.getAddressRoad()+" "+addressItems.getAddressCity();


        if(addressItems.getAddressName().isEmpty() || addressItems.getAddressName().equals("")){
            holder.textViewAddressName.setText(addressItems.getAddressCity());
        }else {
            holder.textViewAddressName.setText(addressItems.getAddressName());
        }




        holder.textViewAddress.setText(address);




        holder.relativelayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                landingPresenter.saveAddress(addressItems.getAddressId(),address);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressItemsArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.robotoMedium8)
        TextView textViewAddressName;

        @BindView(R.id.robotoRegular5)
        TextView textViewAddress;

        @BindView(R.id.relativeLayout_main)
        RelativeLayout relativelayoutMain;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
