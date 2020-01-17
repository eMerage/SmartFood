package emerge.project.onmeal.ui.adaptor;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import emerge.project.onmeal.R;
import emerge.project.onmeal.utils.entittes.OrderHistorySubMenu;
import emerge.project.onmeal.utils.entittes.v2.Orders.OrderMenuDetails;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class HistorySubMenuAdapter extends RecyclerView.Adapter<HistorySubMenuAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<OrderMenuDetails> foodsItems;



    public HistorySubMenuAdapter(Context mContext, ArrayList<OrderMenuDetails> item) {
        this.mContext = mContext;
        this.foodsItems = item;



    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_orderhistory_sub_items, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        OrderMenuDetails foods =foodsItems.get(position);

        holder.textViewFoodname.setText(foods.getName());

        holder.textViewFoodqty.setText(String.valueOf(foods.getFoodQty()));



    }

    @Override
    public int getItemCount() {
        return foodsItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_foodname)
        TextView textViewFoodname;

        @BindView(R.id.textView_foodqty)
        TextView textViewFoodqty;


        @BindView(R.id.textView_foodtype)
        TextView textViewFoodType;



       //

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
