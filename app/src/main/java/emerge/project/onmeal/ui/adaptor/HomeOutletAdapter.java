package emerge.project.onmeal.ui.adaptor;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.menu.ActivityMenu;
import emerge.project.onmeal.utils.entittes.OutletItems;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class HomeOutletAdapter extends RecyclerView.Adapter<HomeOutletAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    ArrayList<OutletItems> outletItems;


    public HomeOutletAdapter(Context mContext, ArrayList<OutletItems> item) {
        this.mContext = mContext;
        this.outletItems = item;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_home, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final OutletItems outlet = outletItems.get(position);


        holder.imageviewCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mContext, ActivityMenu.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(mContext, R.anim.fade_in, R.anim.fade_out).toBundle();
                intent.putExtra("OUTLET", outletItems.get(position));
                mContext.startActivity(intent, bndlanimation);
                ((ActivityHome)mContext).finish();


            }
        });


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
                holder.progressBar.setVisibility(View.GONE);

                return false;
            }
        };


        Glide.with(mContext)
                .asBitmap()
                .load(outlet.getOutletImageUrl())
                .apply(requestOptions)
                .listener(requestListener)
                .into(holder.imageviewCover);


        holder.textViewMenueName.setText(outlet.getOutletName());
        holder.textViewMenueCat.setText(outlet.getOutletCity());

    }

    @Override
    public int getItemCount() {
        return outletItems.size();
    }

    @Override
    public void onClick(View v) {
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_home_menu_name)
        TextView textViewMenueName;

        @BindView(R.id.textView_home_menu_cat)
        TextView textViewMenueCat;

        @BindView(R.id.imageView_home_menu_list)
        ImageView imageviewCover;

        @BindView(R.id.progressBar2)
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
