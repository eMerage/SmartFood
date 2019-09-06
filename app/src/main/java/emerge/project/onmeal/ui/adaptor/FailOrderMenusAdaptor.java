package emerge.project.onmeal.ui.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import emerge.project.onmeal.R;
import emerge.project.onmeal.utils.entittes.MenuItemsError;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class FailOrderMenusAdaptor extends RecyclerView.Adapter<FailOrderMenusAdaptor.MyViewHolder> {

    Context mContext;
    ArrayList<MenuItemsError> item;


    public FailOrderMenusAdaptor(Context context, ArrayList<MenuItemsError> items) {
        mContext = context;
        item = items;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_faild_items, parent, false);
        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        MenuItemsError menuItemsError = item.get(position);


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
                .load(menuItemsError.getImageUrl())
                .apply(requestOptions)
                .listener(requestListener)
                .into(holder.imageviewItemCover);


        holder.textViewMenuname.setText(menuItemsError.getMenuTitle());



    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_menuname)
        TextView textViewMenuname;

        @BindView(R.id.imageview_item_cover)
        ImageView imageviewItemCover;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }


}
