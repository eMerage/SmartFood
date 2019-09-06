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
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    ArrayList<HomeFavouriteItems> favouriteItem;



    public FavouriteAdapter(Context mContext, ArrayList<HomeFavouriteItems> favouriteItem) {
        this.mContext = mContext;
        this.favouriteItem = favouriteItem;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_favorite, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final HomeFavouriteItems landingFavouriteItems = favouriteItem.get(position);


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
                .load(landingFavouriteItems.getFavImgUrl())
                .apply(requestOptions)
                .listener(requestListener)
                .into(holder.imageviewMenu);

       holder.textViewMenueName.setText("Test Name");



    }

    @Override
    public int getItemCount() {
        return favouriteItem.size();
    }

    @Override
    public void onClick(View v) {
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.robotoRegular7)
       TextView textViewMenueName;

        @BindView(R.id.imageview_list_menu_image)
        ImageView imageviewMenu;




        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
