package emerge.project.onmeal.ui.adaptor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.ui.activity.personlaize.ActivityPersonlaize;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class PersonlaizeAdapterType2 extends RecyclerView.Adapter<PersonlaizeAdapterType2.MyViewHolder> {

    Context mContext;
    ArrayList<MenuSubItems> menuSubItems;

    Realm realm;

    public PersonlaizeAdapterType2(Context mContext, ArrayList<MenuSubItems> item) {
        this.mContext = mContext;
        this.menuSubItems = item;
        realm = Realm.getDefaultInstance();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_personlaizer_type_two, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final MenuSubItems menuItems = menuSubItems.get(position);


        holder.textFoodName.setText(menuItems.getFoodname());


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

        Glide.with(mContext).asBitmap().load(menuItems.getImageUrl()).apply(requestOptions).listener(requestListener).into(holder.imageViewHeaderCover);


        if (menuItems.isSelect()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked()){
                    addTypeTwo(menuItems, holder);
                }else {
                    removeTypeTwo(menuItems, holder);
                }

            }
        });



        holder.imageViewHeaderCover.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showImageLongClick(menuItems.getImageUrl());
                return true;
            }
        });


    }
    public void removeTypeTwo(MenuSubItems menusubItems, final MyViewHolder holder){


       /* final RealmResults<MenuSubItems> menuDetail = realm.where(MenuSubItems.class)
                .equalTo("foodId", menusubItems.getFoodId())
                .findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (menuDetail.size() == 0) {

                } else {
                    for (int i = 0; i < menuDetail.size(); i++) {





                        menuDetail.get(i).setSelect(false);
                        menuDetail.get(i).setQuantity(0);
                    }
                }

            }
        });*/


        final MenuSubItems menuDetail = realm.where(MenuSubItems.class)
                .equalTo("foodId", menusubItems.getFoodId())
                .findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                menuDetail.setSelect(false);
                menuDetail.setQuantity(0);
            }
        });

        holder.checkBox.setChecked(false);

        ((ActivityPersonlaize) mContext).priceUpdate();

    }




    public void addTypeTwo(MenuSubItems menusubItems, final MyViewHolder holder) {



        RealmResults<MenuSubItems> cartdetail = realm.where(MenuSubItems.class)
                .equalTo("foodItemCategory", menusubItems.getFoodItemCategory())
                .and()
                .equalTo("foodItemType", "Plate")
                .and()
                .equalTo("isSelect", true)
                .findAll();


        if (cartdetail.size() >= menusubItems.getMaxCurryCount()) {
            Toast.makeText(mContext, "Max curry count exceed", Toast.LENGTH_LONG).show();
            holder.checkBox.setChecked(false);
        } else if (menusubItems.getMaxOrderQty() == 0) {
            Toast.makeText(mContext, "Max Order count exceed", Toast.LENGTH_LONG).show();
        } else {


          /*  final RealmResults<MenuSubItems> menuDetail = realm.where(MenuSubItems.class)
                    .equalTo("foodId", menusubItems.getFoodId())
                    .findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (menuDetail.size() == 0) {

                    } else {
                        for (int i = 0; i < menuDetail.size(); i++) {

                            String a =menuDetail.get(i).getFoodname();
                            int bc =menuDetail.get(i).getFoodId();


                            menuDetail.get(i).setSelect(true);
                            menuDetail.get(i).setQuantity(1);
                        }
                    }

                }
            });*/

            final MenuSubItems menuDetail = realm.where(MenuSubItems.class)
                    .equalTo("foodId", menusubItems.getFoodId())
                    .findFirst();


            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    menuDetail.setSelect(true);
                    menuDetail.setQuantity(1);

                }
            });




            holder.checkBox.setChecked(true);
            ((ActivityPersonlaize) mContext).priceUpdate();
        }




    }




    @Override
    public int getItemCount() {
        return menuSubItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.robotoMedium17)
        TextView textFoodName;

        @BindView(R.id.imageView_header_cover)
        ImageView imageViewHeaderCover;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.checkBox)
        CheckBox checkBox;

        @BindView(R.id.progressbar_header)
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }

    private void showImageLongClick(String image){

        Dialog dialogBox = new Dialog(mContext);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dialog_longclick_imageviver);
        dialogBox.setCancelable(true);


        ImageView fullImage = (ImageView) dialogBox.findViewById(R.id.imageView_fullimage);


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
                .load(image)
                .apply(requestOptions)
                .listener(requestListener)
                .into(fullImage);

        dialogBox.show();
    }


}
