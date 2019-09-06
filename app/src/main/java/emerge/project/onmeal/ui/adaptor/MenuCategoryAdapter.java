package emerge.project.onmeal.ui.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.onmeal.R;
import emerge.project.onmeal.ui.activity.menu.MenuPresenter;
import emerge.project.onmeal.ui.activity.menu.MenuPresenterImpli;
import emerge.project.onmeal.ui.activity.menu.MenuView;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<MenuCategoryItems> menuCategoryItems;

    MenuPresenter menuPresenter;
    int status =0;
    public MenuCategoryAdapter(Context mContext, ArrayList<MenuCategoryItems> item, MenuView menuView) {
        this.mContext = mContext;
        this.menuCategoryItems = item;
        menuPresenter = new MenuPresenterImpli(menuView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_cat, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MenuCategoryItems categoryItems = menuCategoryItems.get(position);
        holder.textCatName.setText(categoryItems.getMenuCategory());
        if(categoryItems.isSelect()){
            holder.textCatName.setTextColor(mContext.getResources().getColor(R.color.colorTextWhite));
            holder.relativelayoutCatIndicator.setVisibility(View.VISIBLE);
            if(categoryItems.getMenuCategoryID().equals("0")){ }else {
                if(status==0){
                    menuPresenter.geSelectedMenuCategory(categoryItems.getMenuCategoryID());
                }else {

                }

            }

        }else {
            holder.textCatName.setTextColor(mContext.getResources().getColor(R.color.menuIndicatorTextColor));
            holder.relativelayoutCatIndicator.setVisibility(View.INVISIBLE);
        }


        holder.relativeLayoutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (MenuCategoryItems menuItems : menuCategoryItems) {
                    menuItems.setSelect(false);
                }
                menuCategoryItems.get(position).setSelect(true);
                notifyDataSetChanged();

                status ++;
                if(categoryItems.getMenuCategoryID().equals("0")){

                }else {
                    menuPresenter.geSelectedMenuCategory(menuCategoryItems.get(position).getMenuCategoryID());
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return menuCategoryItems.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

       @BindView(R.id.text_cat_name)
       TextView textCatName;

       @BindView(R.id.relativelayout_cat_indicator)
       RelativeLayout relativelayoutCatIndicator;

        @BindView(R.id.relativeLayout_main)
        RelativeLayout relativeLayoutMain;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
