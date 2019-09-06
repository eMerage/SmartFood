package emerge.project.onmeal.ui.adaptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.cart.ActivityCart;
import emerge.project.onmeal.ui.activity.cart.CartPresenter;
import emerge.project.onmeal.ui.activity.cart.CartPresenterImpli;
import emerge.project.onmeal.ui.activity.cart.CartView;
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizePresenter;
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizePresenterImpli;
import emerge.project.onmeal.ui.activity.personlaize.PersonlaizeView;
import emerge.project.onmeal.utils.entittes.MenuSize;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Created by Himanshu on 4/10/2015.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> implements View.OnClickListener {

    Context mContext;
    ArrayList<CartHeader> cartArrayList;
    Realm realm;

    CartPresenter cartPresenter;

    public CartAdapter(Context mContext, ArrayList<CartHeader> item,CartView cartView) {
        this.mContext = mContext;
        this.cartArrayList = item;
        realm = Realm.getDefaultInstance();

        cartPresenter = new CartPresenterImpli(cartView);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart, parent, false);

        return new MyViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final CartHeader cartHeader =cartArrayList.get(position);
        if(cartHeader.isActive()){



            holder.textviewQty.setText(String.valueOf(cartHeader.getQuantity()));
            holder.textviewMenuName.setText(cartHeader.getName()+" ("+cartHeader.getSize()+")");


            String sPrice = String.valueOf(cartHeader.getPriceTotal());
            String[] priseArray = sPrice.split("\\.");
            holder.textviewTotalPrice.setText(priseArray[0]);
            holder.textviewTotalPriceCents.setText("." + priseArray[1]);



            holder.imgBtnRemoveitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NetworkAvailability.isNetworkAvailable(mContext)) {
                        removeCartItem(cartHeader,position,holder);
                    } else {
                        Toast.makeText(mContext, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });


            holder.textviewMenuName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartPresenter.getSubCartItems(cartHeader.getCartHeaderId());
                }
            });

            holder.textviewQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    int qty;

                    try {
                        qty=Integer.parseInt(charSequence.toString());
                    }catch (NumberFormatException num){
                        qty=-1;
                    }

                    if( qty==0){
                        removeCartItem(cartHeader,position,holder);
                    }else if(qty==-1){

                    }else {
                        chengeQty(cartHeader,qty);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }else {

        }



   /*     holder.textviewQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int qty;

                try {
                    qty=Integer.parseInt(charSequence.toString());
                }catch (NumberFormatException num){
                    qty=-1;
                }

                if( qty==0){
                    removeCartItem(cartHeader,position,holder);
                }else if(qty==-1){

                }else {
                    chengeQty(cartHeader,qty);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/



    }
    public void removeCartItem(final CartHeader cartHeader , int position, final MyViewHolder holder){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<CartHeader> resultsCartHeader = realm.where(CartHeader.class)
                        .equalTo("cartHeaderId",cartHeader.getCartHeaderId())
                        .findAll();


                if (resultsCartHeader.size() == 0) {

                } else {
                    for (int i = 0; i < resultsCartHeader.size(); i++) {
                        resultsCartHeader.get(i).setActive(false);

                    }
                }

            }
        });
        ((ActivityCart) mContext).getPriceAfterRemoveItem();

    }


    private void chengeQty(CartHeader cartHeader, final int qty){


        final RealmResults<CartHeader> menuDetail = realm.where(CartHeader.class)
                .equalTo("cartHeaderId",cartHeader.getCartHeaderId())
                .equalTo("isActive",true)
                .findAll();


        for (int i = 0; i <menuDetail.size() ; i++) {

            Double totalPrice=menuDetail.get(i).getPriceTotal();
            Double itemPrice=0.0;
            int addedQty=menuDetail.get(i).getQuantity();

            itemPrice=(totalPrice/addedQty);

            final Double newTotalPrice=itemPrice*qty;

            final int finalI = i;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    menuDetail.get(finalI).setQuantity(qty);
                    menuDetail.get(finalI).setPriceTotal(newTotalPrice);

                }
            });

        }

        ((ActivityCart) mContext).getPriceAfterRemoveItem();



    }





    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    @Override
    public void onClick(View v) {
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_menuname)
        TextView textviewMenuName;

        @BindView(R.id.img_btn_removeitem)
        ImageView imgBtnRemoveitem;

        @BindView(R.id.textview_qty)
        EditText textviewQty;


        @BindView(R.id.textview_total_price)
        TextView textviewTotalPrice;

        @BindView(R.id.textview_total_price_cents)
        TextView textviewTotalPriceCents;

        @BindView(R.id.relativeLayout_main)
        RelativeLayout relativeLayoutMain;

        @BindView(R.id.relativelayout)
        RelativeLayout relativeLayoutqty;



        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


    }


}
