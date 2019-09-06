package emerge.project.onmeal.ui.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import emerge.project.onmeal.ui.activity.menu.MenuPresenter;
import emerge.project.onmeal.ui.activity.menu.MenuPresenterImpli;
import emerge.project.onmeal.ui.activity.menu.MenuView;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;

    Context mContext;
    ArrayList<MenuItems> menuItems;

    boolean isFromOutlet =true;


    MenuPresenter menuPresenter;

    public MenuAdapter(Context mContext, ArrayList<MenuItems> item,boolean isfromoutlet,MenuView menuView) {
        this.mContext = mContext;
        this.menuItems = item;
        this.isFromOutlet = isfromoutlet;
        menuPresenter = new MenuPresenterImpli(menuView);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_menu_headerr, parent, false);
            return new HeaderViewHolder(itemView);
        } else{

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_menu_footer, parent, false);
            return new FooterViewHolder(itemView);

        }



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {



        final MenuItems menuItem = menuItems.get(position);



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_image_default);
        requestOptions.error(R.drawable.ic_image_default);




        if (viewHolder instanceof HeaderViewHolder) {
            final HeaderViewHolder headerHolder = (HeaderViewHolder) viewHolder;
            if(position==0){

                if(isFromOutlet){
                    headerHolder.textviewHeaderTitle.setText(menuItem.getFoodName());
                }else {

                    headerHolder.textviewHeaderTitle.setText(menuItem.getFoodName()+"-"+menuItem.getOutletName());
                }



                RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) { return false; }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        headerHolder.progressbarHeader.setVisibility(View.GONE);
                        return false;
                    }
                };
                Glide.with(mContext).asBitmap().load(menuItem.getFoodCoverImage()).apply(requestOptions).listener(requestListener).into(headerHolder.imageviewHeaderCover);

                headerHolder.relativeLayoutHeaderMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        menuPresenter.geSelectedMenuDetails(menuItem.getMenuId(),menuItem.getFoodId(),menuItem.getOutletId(),menuItem.getFoodName(),menuItem.getFoodCoverImage(),menuItem.getOutletName());

                    }
                });

            }else {


            }







        } else{


            final FooterViewHolder footerHolder = (FooterViewHolder) viewHolder;

            if(position==0){

            }else {
                if(isFromOutlet){
                    footerHolder.textviewFooterTitle.setText(menuItem.getFoodName());
                    footerHolder.textviewFooterOutlet.setVisibility(View.GONE);
                }else {
                    footerHolder.textviewFooterOutlet.setVisibility(View.VISIBLE);
                    footerHolder.textviewFooterOutlet.setText(menuItem.getOutletName());
                    footerHolder.textviewFooterTitle.setText(menuItem.getFoodName());
                }

                RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) { return false; }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        footerHolder.progressbarFooter.setVisibility(View.GONE);
                        return false;
                    }
                };
                Glide.with(mContext).asBitmap().load(menuItem.getFoodCoverImage()).apply(requestOptions).listener(requestListener).into(footerHolder.imageViewFooterCover);

                footerHolder.relativeLayoutFooterMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        menuPresenter.geSelectedMenuDetails(menuItem.getMenuId(),menuItem.getFoodId(),menuItem.getOutletId(),menuItem.getFoodName(),menuItem.getFoodCoverImage(),menuItem.getOutletName());


                    }
                });


            }


        }
    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == menuItems.size() + 1) {
            return TYPE_FOOTER;
        }
        return 2;
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.textview_header_title)
        TextView textviewHeaderTitle;


        @BindView(R.id.textview_header_price)
        TextView textviewHeaderPrice;

        @BindView(R.id.relativeLayout_header_price)
        RelativeLayout relativeLayoutHeaderPrice;

        @BindView(R.id.relativeLayout_header_main)
        RelativeLayout relativeLayoutHeaderMain;


        @BindView(R.id.imageView_header_cover)
        ImageView imageviewHeaderCover;

        @BindView(R.id.progressbar_header)
        ProgressBar progressbarHeader;



        public HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.textview_footer_title)
        TextView textviewFooterTitle;

        @BindView(R.id.textview_footer_outlet)
        TextView textviewFooterOutlet;



        @BindView(R.id.textview_footer_price)
        TextView textviewFooterPrice;

        @BindView(R.id.relativeLayout_footer_price)
        RelativeLayout relativeLayoutFooterPrice;

        @BindView(R.id.relativeLayout_footer_main)
        RelativeLayout relativeLayoutFooterMain;

        @BindView(R.id.imageView_footer_cover)
        ImageView imageViewFooterCover;

        @BindView(R.id.progressbar_footer)
        ProgressBar progressbarFooter;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }

}
