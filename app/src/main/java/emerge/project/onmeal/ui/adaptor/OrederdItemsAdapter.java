package emerge.project.onmeal.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.cart.ActivityCart;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class OrederdItemsAdapter extends RecyclerView.Adapter<OrederdItemsAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    ArrayList<CartHeader> cartArrayList;
    Realm realm;


    public OrederdItemsAdapter(Context mContext, ArrayList<CartHeader> item) {
        this.mContext = mContext;
        this.cartArrayList = item;
        realm = Realm.getDefaultInstance();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ordered, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final CartHeader cartHeader =cartArrayList.get(position);

        holder.textviewMenuName.setText(cartHeader.getName());
        holder.textviewQty.setText("x"+String.valueOf(cartHeader.getQuantity()));


        String sPrice = String.valueOf(cartHeader.getPriceTotal());
        String[] priseArray = sPrice.split("\\.");
        holder.textviewTotalPrice.setText(priseArray[0]);
        holder.textviewTotalPriceCents.setText("." + priseArray[1]);

    }





    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    @Override
    public void onClick(View v) {
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_menuname)
        TextView textviewMenuName;


        @BindView(R.id.textview_qty)
        TextView textviewQty;


        @BindView(R.id.textview_total_price)
        TextView textviewTotalPrice;

        @BindView(R.id.textview_total_price_cents)
        TextView textviewTotalPriceCents;




        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
