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
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizePresenter;
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizePresenterImpli;
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizeView;
import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<FoodCategoryItems> foodCategoryItems;

    PersonlaizePresenter personlaizePresenter;

    int status =0;

    public FoodCategoryAdapter(Context mContext, ArrayList<FoodCategoryItems> item,PersonlaizeView personlaizeView) {
        this.mContext = mContext;
        this.foodCategoryItems = item;

        personlaizePresenter = new PersonlaizePresenterImpli(personlaizeView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_cat, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {




        final FoodCategoryItems foodcatItems = foodCategoryItems.get(position);

        holder.textCatName.setText(foodcatItems.getFoodItemCategory());

        if(foodcatItems.isSelect()){
            holder.textCatName.setTextColor(mContext.getResources().getColor(R.color.colorTextWhite));
            holder.relativelayoutCatIndicator.setVisibility(View.VISIBLE);

            if(foodcatItems.getFoodItemCategoryID()==0){

            }else {
                if(status==0){
                    personlaizePresenter.geSelectedFoodCategory(foodcatItems.getFoodItemCategoryID(),position);
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
                for (FoodCategoryItems foodItems : foodCategoryItems) {
                    foodItems.setSelect(false);
                }
                foodCategoryItems.get(position).setSelect(true);
                status ++;

                notifyDataSetChanged();

                if(foodcatItems.getFoodItemCategoryID()==0){ }else {
                    personlaizePresenter.geSelectedFoodCategory(foodCategoryItems.get(position).getFoodItemCategoryID(),position);


                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return foodCategoryItems.size();
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
