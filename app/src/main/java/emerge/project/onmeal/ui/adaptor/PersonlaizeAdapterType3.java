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
public class PersonlaizeAdapterType3 extends RecyclerView.Adapter<PersonlaizeAdapterType3.MyViewHolder> {

    Context mContext;
    ArrayList<MenuSubItems> menuSubItems;

    Realm realm;
    public PersonlaizeAdapterType3(Context mContext, ArrayList<MenuSubItems> item) {
        this.mContext = mContext;
        this.menuSubItems = item;
        realm = Realm.getDefaultInstance();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_personlaizer, parent, false);
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
            holder.relativeLayoutQuntity.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
            holder.textQty.setText(String.valueOf(menuItems.getQuantity()));
        } else {

            holder.checkBox.setChecked(false);
            holder.relativeLayoutQuntity.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
        }


        String sPrice = String.valueOf(menuItems.getFoodItemPrice());
        String[] priseArray = sPrice.split("\\.");
        holder.textFoodPrice.setText(priseArray[0]);
        holder.textFoodPriceCents.setText("." + priseArray[1]);



        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.checkBox.isChecked()){
                    holder.relativeLayoutQuntity.setVisibility(View.VISIBLE);
                    holder.divider.setVisibility(View.VISIBLE);
                    addTypeThree(menuSubItems.get(position),1,holder);
                    holder.textQty.setText("01");
                }else {
                    holder.relativeLayoutQuntity.setVisibility(View.GONE);
                    holder.divider.setVisibility(View.GONE);
                    holder.textQty.setText("00");
                    removeTypeThree(menuSubItems.get(position),holder);
                }

            }
        });



        holder.imageViewQtyincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int qty = Integer.parseInt(holder.textQty.getText().toString());
                if (qty > 20) {//Maximum extra qty per item
                    Toast.makeText(mContext, "Maximum Extra count exceed", Toast.LENGTH_LONG).show();
                } else {
                    qty = qty + 1;
                    holder.textQty.setText(String.valueOf(qty));
                    addTypeThree(menuSubItems.get(position),qty,holder);
                }


            }
        });


        holder.imageViewQtyDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int qty = Integer.parseInt(holder.textQty.getText().toString());
                if (qty > 0) {
                    qty = qty - 1;
                    if (qty == 0) {
                        removeTypeThree(menuSubItems.get(position),holder);
                    } else {
                        addTypeThree(menuSubItems.get(position),qty,holder);
                    }

                    holder.textQty.setText(String.valueOf(qty));

                } else {

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


    public void addTypeThree(MenuSubItems menusubItems, final int qty , final MyViewHolder holder) {

        realm = Realm.getDefaultInstance();

        RealmResults<MenuSubItems> cartdetail = realm.where(MenuSubItems.class)
                .equalTo("foodItemCategory", menusubItems.getFoodItemCategory())
                .and()
                .equalTo("foodItemType", "Extra")
                .and()
                .equalTo("isSelect", true)
                .findAll();

        if (cartdetail.size() >=20) {
       // if (cartdetail.size() >= menusubItems.getMaxExtrasQty()) {
            Toast.makeText(mContext, "Max extras count exceed ", Toast.LENGTH_LONG).show();
            holder.checkBox.setChecked(false);
        } else if (menusubItems.getMaxOrderQty() == 0) {
            Toast.makeText(mContext, "Max Order count exceed", Toast.LENGTH_LONG).show();
        } else {
            /*final RealmResults<MenuSubItems> menuDetail = realm.where(MenuSubItems.class)
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
                            menuDetail.get(i).setQuantity(qty);
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
                    menuDetail.setQuantity(qty);

                }
            });


            holder.checkBox.setChecked(true);
            ((ActivityPersonlaize) mContext).priceUpdate();
        }



    }

    public void removeTypeThree(MenuSubItems menusubItems, final MyViewHolder holder){


  /*      final RealmResults<MenuSubItems> menuDetail = realm.where(MenuSubItems.class)
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




    @Override
    public int getItemCount() {
        return menuSubItems.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.robotoMedium17)
        TextView textFoodName;


        @BindView(R.id.robotoMedium18)
        TextView textFoodPriceCents;


        @BindView(R.id.robotoMedium19)
        TextView textFoodPrice;


        @BindView(R.id.divider17)
        View divider;

        @BindView(R.id.RelativeLayout2)
        RelativeLayout relativeLayoutQuntity;

        @BindView(R.id.imageView_header_cover)
        ImageView imageViewHeaderCover;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.checkBox)
        CheckBox checkBox;

        @BindView(R.id.img_qty_decrease)
        ImageView imageViewQtyDecrease;

        @BindView(R.id.imageView20)
        ImageView imageViewQtyincrease;

        @BindView(R.id.robotoRegular3)
        TextView textQty;

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
