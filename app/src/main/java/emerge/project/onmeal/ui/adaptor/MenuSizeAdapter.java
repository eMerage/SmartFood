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
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizePresenter;
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizePresenterImpli;
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizeView;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.MenuSize;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class MenuSizeAdapter extends RecyclerView.Adapter<MenuSizeAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    ArrayList<MenuSize> menuSizeArrayList;

    PersonlaizePresenter personlaizePresenter;

    int status =0;

    public MenuSizeAdapter(Context mContext, ArrayList<MenuSize> item, PersonlaizeView personlaizeView) {
        this.mContext = mContext;
        this.menuSizeArrayList = item;
        personlaizePresenter = new PersonlaizePresenterImpli(personlaizeView);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_size, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final MenuSize menuSizeItems = menuSizeArrayList.get(position);
        if (menuSizeItems.isSelect()) {
            holder.textviewSize.setTextColor(mContext.getResources().getColor(R.color.colorTextWhite));
            holder.imageViewBackground.setImageResource(R.drawable.ic_bg_size_afterselct);

            if(status==0){
                personlaizePresenter.setMenuSize(menuSizeArrayList.get(position).getFoodItemSizeCode());
            }else {

            }
        } else {
            holder.textviewSize.setTextColor(mContext.getResources().getColor(R.color.navigationTextColor));
            holder.imageViewBackground.setImageResource(R.drawable.ic_bg_size_beforrselct);
        }





        holder.textviewSize.setText(menuSizeItems.getFoodItemSizeCode());

        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuSizeArrayList.get(position).isSelect()) {

                } else {
                    for (MenuSize items : menuSizeArrayList) {
                        items.setSelect(false);
                    }
                    menuSizeArrayList.get(position).setSelect(true);
                    notifyDataSetChanged();
                }

                status ++;
                personlaizePresenter.setMenuSize(menuSizeArrayList.get(position).getFoodItemSizeCode());


            }
        });


    }

    @Override
    public int getItemCount() {
        return menuSizeArrayList.size();
    }

    @Override
    public void onClick(View v) {
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_size)
        TextView textviewSize;

        @BindView(R.id.imageView_background)
        ImageView imageViewBackground;

        @BindView(R.id.relativeLayout_main)
        RelativeLayout relativeLayoutMain;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
