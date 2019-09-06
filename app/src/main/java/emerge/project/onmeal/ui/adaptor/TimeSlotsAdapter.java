package emerge.project.onmeal.ui.adaptor;

import android.content.Context;
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
import emerge.project.onmeal.ui.activity.cart.CartPresenter;
import emerge.project.onmeal.ui.activity.cart.CartPresenterImpli;
import emerge.project.onmeal.ui.activity.cart.CartView;
import emerge.project.onmeal.utils.entittes.TimeSlots;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class TimeSlotsAdapter extends RecyclerView.Adapter<TimeSlotsAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    ArrayList<TimeSlots> slotsArrayList;

    int sdk;

    CartPresenter cartPresenter;
    public TimeSlotsAdapter(Context mContext, ArrayList<TimeSlots> item,CartView cartView) {
        this.mContext = mContext;
        this.slotsArrayList = item;

        cartPresenter = new CartPresenterImpli(cartView);
        sdk = android.os.Build.VERSION.SDK_INT;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_time_slots, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        TimeSlots timeSlots = slotsArrayList.get(position);

        if (timeSlots.isSelected()) {

            holder.textViewTimeSlot.setTextColor(mContext.getResources().getColor(R.color.colorTextWhite));
            holder.imageviewSelectIocn.setVisibility(View.VISIBLE);
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.relativeLayoutSelect.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_selected_timeslots));
            } else {
                holder.relativeLayoutSelect.setBackground(mContext.getResources().getDrawable(R.drawable.bg_selected_timeslots));
            }

        } else {
            holder.textViewTimeSlot.setTextColor(mContext.getResources().getColor(R.color.timeSlotsTextColor));
            holder.imageviewSelectIocn.setVisibility(View.INVISIBLE);
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.relativeLayoutSelect.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_timeslots));
            } else {
                holder.relativeLayoutSelect.setBackground(mContext.getResources().getDrawable(R.drawable.bg_timeslots));
            }

        }


        holder.textViewTimeSlot.setText(timeSlots.getDeliveryTimeSlot());


        holder.relativeLayoutSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (TimeSlots items : slotsArrayList) {
                    items.setSelected(false);
                }
                slotsArrayList.get(position).setSelected(true);
                cartPresenter.selectedTimeSlot(slotsArrayList.get(position).getTimeSlotId());
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return slotsArrayList.size();
    }

    @Override
    public void onClick(View v) {
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.robotoLight6)
        TextView textViewTimeSlot;


        @BindView(R.id.imageView13)
        ImageView imageviewSelectIocn;


        @BindView(R.id.relativeLayout_br_select)
        RelativeLayout relativeLayoutSelect;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
