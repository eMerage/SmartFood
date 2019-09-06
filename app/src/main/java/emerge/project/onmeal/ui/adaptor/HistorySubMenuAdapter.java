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


/**
 * Created by Himanshu on 4/10/2015.
 */
public class HistorySubMenuAdapter extends RecyclerView.Adapter<HistorySubMenuAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<OrderHistorySubMenu> foodsItems;



    public HistorySubMenuAdapter(Context mContext, ArrayList<OrderHistorySubMenu> item) {
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

        OrderHistorySubMenu foods =foodsItems.get(position);

        holder.textViewFoodname.setText(foods.getOutletMenuName());

        holder.textViewFoodqty.setText(String.valueOf(foods.getQty()));

       /* String catagory;

        if (foods.isBaseFood()) {
            catagory = "BASE";
        } else if (foods.getFoodItemTypeCode().equals("EX")) {
            catagory = "EXTRA";
        } else {
            catagory = foods.getFoodItemCategory();

        }


        holder.textViewFoodType.setText(catagory);*/


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
