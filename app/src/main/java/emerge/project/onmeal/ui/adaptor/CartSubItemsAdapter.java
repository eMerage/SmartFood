package emerge.project.onmeal.ui.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.CartDetail;
import io.realm.Realm;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class CartSubItemsAdapter extends RecyclerView.Adapter<CartSubItemsAdapter.MyViewHolder> {

    Context mContext;

    ArrayList<CartDetail> itemCart;


    public CartSubItemsAdapter(Context mContext, ArrayList<CartDetail> item) {
        this.mContext = mContext;
        this.itemCart = item;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_sub_cart, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final CartDetail cartDetail = itemCart.get(position);

        holder.txtName.setText(cartDetail.getFoodname());
        holder.txtItemqty.setText(String.valueOf(cartDetail.getQuantity()));


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_default);
        requestOptions.error(R.drawable.ic_image_default);


        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;


            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

                return false;
            }
        };

        Glide.with(mContext)
                .asBitmap()
                .load(cartDetail.getImageUrl())
                .apply(requestOptions)
                .listener(requestListener)
                .into(holder.imgCover);





    }

    @Override
    public int getItemCount() {
        return itemCart.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview_item_cover)
        ImageView imgCover;

        @BindView(R.id.textView_menuname)
        TextView txtName;

        @BindView(R.id.textView_itemqty)
        TextView txtItemqty;







        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }

    }








}
