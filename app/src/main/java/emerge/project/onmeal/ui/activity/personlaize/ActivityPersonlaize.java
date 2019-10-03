package emerge.project.onmeal.ui.activity.personlaize;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.cart.ActivityCart;
import emerge.project.onmeal.ui.adaptor.FoodCategoryAdapter;
import emerge.project.onmeal.ui.adaptor.MenuSizeAdapter;
import emerge.project.onmeal.ui.adaptor.PersonlaizeAdapterType1;
import emerge.project.onmeal.ui.adaptor.PersonlaizeAdapterType2;
import emerge.project.onmeal.ui.adaptor.PersonlaizeAdapterType3;
import emerge.project.onmeal.ui.adaptor.PersonlaizeAdapterType4;
import emerge.project.onmeal.ui.adaptor.PersonlaizeAdapterType5;
import emerge.project.onmeal.ui.adaptor.PersonlaizeAdapterType6;
import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuSize;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;

public class ActivityPersonlaize extends Activity implements PersonlaizeView {


    @BindView(R.id.imageView_cover_image)
    ImageView imageViewCoverImage;


    @BindView(R.id.textview_total_price)
    TextView textviewTotalPrice;

    @BindView(R.id.textview_total_price_cents)
    TextView textviewTotalPriceCents;

    @BindView(R.id.textview_cart_count)
    TextView textviewCartCount;


    @BindView(R.id.progressBar_coverImage)
    ProgressBar progressBarCoverImage;

    @BindView(R.id.textview_titel)
    TextView textviewTitel;

    @BindView(R.id.textview_qty)
    TextView textviewQty;

    @BindView(R.id.textview_titel_cat)
    TextView textviewTitelCat;

    @BindView(R.id.recyclerView_food_cat)
    RecyclerView recyclerViewFoodCat;

    @BindView(R.id.recyclerView_menu)
    RecyclerView recyclerViewMenu;


    @BindView(R.id.recyclerView_size)
    RecyclerView recyclerViewSize;


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    SelectedMenuDetails selectedMenuDetails;
    FoodCategoryAdapter foodCategoryAdapter;

    PersonlaizePresenter personlaizePresenter;

    PersonlaizeAdapterType1 personlaizeAdapterType1;
    PersonlaizeAdapterType2 personlaizeAdapterType2;
    PersonlaizeAdapterType3 personlaizeAdapterType3;
    PersonlaizeAdapterType4 personlaizeAdapterType4;
    PersonlaizeAdapterType5 personlaizeAdapterType5;
    PersonlaizeAdapterType6 personlaizeAdapterType6;


    ArrayList<FoodCategoryItems> foodCategoryItemsArrayList;
    MenuSizeAdapter menuSizeAdapter;


    String menuSizeCode;
    Double totalPrice;

    LinearLayoutManager layoutManagerFoodCat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personlaize);
        ButterKnife.bind(this);


        selectedMenuDetails = (SelectedMenuDetails) getIntent().getSerializableExtra("SELECTEDMENU");
        personlaizePresenter = new PersonlaizePresenterImpli(this);


        setFoodCatRecycalView();
        setFoodRecycal();
        setSizeRecycal();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            setCoverImage(selectedMenuDetails.getMenuImg(), selectedMenuDetails.getMenuName(), selectedMenuDetails.getOutletName());
            personlaizePresenter.getFoodCategory(this, selectedMenuDetails.getOutletId(), selectedMenuDetails.getMenuId(), selectedMenuDetails.getFoodId(),selectedMenuDetails.getMenuCat());

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("No Internet Access, Please try again ");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();

        }


        personlaizePresenter.cartCount();

    }

    @Override
    protected void onResume() {
        personlaizePresenter.cartCount();
        super.onResume();
    }

    @OnClick(R.id.relativelayout_menu)
    public void onClickBackMenu(View view) {
        finish();
    }

    @OnClick(R.id.img_btn_addtocart)
    public void onClicAddToCart(View view) {

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            personlaizePresenter.addToCart(selectedMenuDetails, Integer.parseInt(textviewQty.getText().toString()), menuSizeCode, totalPrice);

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage("No Internet Access, Please try again ");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();

        }


    }

    @OnClick(R.id.relativelayout_cart)
    public void onClicCart(View view) {
        personlaizePresenter.checkCartAvailability();
    }

    @OnClick(R.id.img_btn_qty_decrease)
    public void onClicQTYdecrease(View view) {
        int qty;
        try {
            qty = Integer.parseInt(textviewQty.getText().toString());
        } catch (NumberFormatException num) {
            qty = 1;
        }
        if (qty == 1) {
        } else {
            String sqty = String.valueOf(qty - 1);
            if (sqty.length() == 1) {
                sqty = "0" + sqty;
            } else {

            }
            textviewQty.setText(sqty);
            personlaizePresenter.getTotalPrice(Integer.parseInt(textviewQty.getText().toString()), menuSizeCode);
        }


    }

    @OnClick(R.id.img_btn_qty_increase)
    public void onClicQTYincrease(View view) {
        int qty;
        try {
            qty = Integer.parseInt(textviewQty.getText().toString());
        } catch (NumberFormatException num) {
            qty = 1;
        }
        String sqty = String.valueOf(qty + 1);
        if (sqty.length() == 1) {
            sqty = "0" + sqty;
        } else {

        }
        textviewQty.setText(sqty);
        personlaizePresenter.getTotalPrice(Integer.parseInt(textviewQty.getText().toString()), menuSizeCode);

    }


    private void setCoverImage(String imageUrl, final String title, final String cat) {

        progressBarCoverImage.setVisibility(View.VISIBLE);
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
                progressBarCoverImage.setVisibility(View.GONE);

                textviewTitel.setText(title);
                textviewTitelCat.setText(cat);

                textviewTitel.setVisibility(View.VISIBLE);
                textviewTitelCat.setVisibility(View.VISIBLE);


                return false;
            }
        };

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .apply(requestOptions)
                .listener(requestListener)
                .into(imageViewCoverImage);


    }


    private void setFoodCatRecycalView() {

        layoutManagerFoodCat = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFoodCat.setLayoutManager(layoutManagerFoodCat);
        recyclerViewFoodCat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewFoodCat.setNestedScrollingEnabled(false);

    }


    private void setFoodRecycal() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewMenu.setLayoutManager(layoutManager);
        recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMenu.setNestedScrollingEnabled(true);

    }


    private void setSizeRecycal() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSize.setLayoutManager(layoutManager);
        recyclerViewSize.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSize.setNestedScrollingEnabled(false);

    }

    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    @Override
    public void foodCategory(ArrayList<FoodCategoryItems> foodCategoryItems) {
        foodCategoryItemsArrayList = foodCategoryItems;
        personlaizePresenter.getMenuSize(this, selectedMenuDetails.getOutletId(), selectedMenuDetails.getMenuId(), selectedMenuDetails.getFoodId(),selectedMenuDetails.getMenuCat());
        foodCategoryAdapter = new FoodCategoryAdapter(this, foodCategoryItems, this);
        recyclerViewFoodCat.setAdapter(foodCategoryAdapter);

    }

    @Override
    public void foodCategoryFail(String msg, int outletID, int menuTitleID, int outletMenuTitleID,int menuCatID) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        try {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                proprogressview.setVisibility(View.VISIBLE);
                                bloackUserInteraction();
                                personlaizePresenter.getFoodCategory(ActivityPersonlaize.this, selectedMenuDetails.getOutletId(), selectedMenuDetails.getMenuId(),
                                        selectedMenuDetails.getFoodId(),selectedMenuDetails.getMenuCat());
                            } else {
                                Toast.makeText(ActivityPersonlaize.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();


        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    @Override
    public void selectedFoodCategor(int foodItemCategoryID, int position) {
        layoutManagerFoodCat.scrollToPosition(position + 1);
        proprogressview.setVisibility(View.VISIBLE);
        bloackUserInteraction();
        personlaizePresenter.getSubFoods(this, selectedMenuDetails.getMenuId(), selectedMenuDetails.getFoodId(),
                selectedMenuDetails.getOutletId(), foodItemCategoryID,selectedMenuDetails.getMenuCat(),
                foodCategoryItemsArrayList);

    }

    @Override
    public void subFoodsEmpty() {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        Toast.makeText(this, "No Product available", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void subFoods(ArrayList<MenuSubItems> menuSubItemsArrayList) {

        if (menuSubItemsArrayList.size() == 0) {

        } else {
            proprogressview.setVisibility(View.GONE);
            unBloackUserInteraction();

            if (menuSubItemsArrayList.get(0).getBaseFood()) {
                personlaizeAdapterType1 = new PersonlaizeAdapterType1(this, menuSubItemsArrayList);
                recyclerViewMenu.setAdapter(personlaizeAdapterType1);
            } else if (menuSubItemsArrayList.get(0).getFoodItemType().equals("Extra")) {
                personlaizeAdapterType3 = new PersonlaizeAdapterType3(this, menuSubItemsArrayList);
                recyclerViewMenu.setAdapter(personlaizeAdapterType3);
            } else if (menuSubItemsArrayList.get(0).getFoodItemCategory().equals("Meat")) {
                personlaizeAdapterType6 = new PersonlaizeAdapterType6(this, menuSubItemsArrayList);
                recyclerViewMenu.setAdapter(personlaizeAdapterType6);
            } else if (menuSubItemsArrayList.get(0).getFoodItemCategory().equals("Extra Ordinary")) {
                personlaizeAdapterType4 = new PersonlaizeAdapterType4(this, menuSubItemsArrayList);
                recyclerViewMenu.setAdapter(personlaizeAdapterType4);
            } else if (menuSubItemsArrayList.get(0).getFoodItemCategory().equals("Other")) {
                personlaizeAdapterType5 = new PersonlaizeAdapterType5(this, menuSubItemsArrayList);
                recyclerViewMenu.setAdapter(personlaizeAdapterType5);
            } else {
                personlaizeAdapterType2 = new PersonlaizeAdapterType2(this, menuSubItemsArrayList);
                recyclerViewMenu.setAdapter(personlaizeAdapterType2);
            }

        }


    }


    @Override
    public void subFoodsFail(final int menuId, final int foodId, final int outletId, final int foodItemCategoryID, String msg, final int menuCatID) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg + " ,Please try again");
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                proprogressview.setVisibility(View.VISIBLE);
                                bloackUserInteraction();
                                personlaizePresenter.getSubFoods(ActivityPersonlaize.this, menuId, foodId, outletId, foodItemCategoryID,menuCatID ,foodCategoryItemsArrayList);

                            } else {
                                Toast.makeText(ActivityPersonlaize.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();

        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


    @Override
    public void menuSize(ArrayList<MenuSize> menuItemSizes) {

        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        menuSizeAdapter = new MenuSizeAdapter(this, menuItemSizes, this);
        recyclerViewSize.setAdapter(menuSizeAdapter);


    }

    @Override
    public void menuSizeFail(String msg, final int outletID, final int menuTitleID, final int outletMenuTitleID, final int menuCatID) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.GONE);

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
                                proprogressview.setVisibility(View.VISIBLE);
                                bloackUserInteraction();
                                personlaizePresenter.getMenuSize(ActivityPersonlaize.this, outletID, menuTitleID, outletMenuTitleID, menuCatID);
                            } else {
                                Toast.makeText(ActivityPersonlaize.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void totalPrice(Double price) {

        totalPrice = price;
        String sPrice = String.valueOf(price);
        try {
            String[] priseArray = sPrice.split("\\.");
            textviewTotalPrice.setText(priseArray[0]);
            textviewTotalPriceCents.setText("." + priseArray[1]);
        }catch (ArrayIndexOutOfBoundsException aiobex){
            textviewTotalPrice.setText(sPrice);
        }


    }

    @Override
    public void selectedMenuSize(String sizeCode) {
        menuSizeCode = sizeCode;
        personlaizePresenter.getTotalPrice(Integer.parseInt(textviewQty.getText().toString()), menuSizeCode);
    }


    @Override
    public void itemAddedToCart(int cartCount) { textviewCartCount.setText(String.valueOf(cartCount));
        Toast.makeText(ActivityPersonlaize.this, "Item Added to cart", Toast.LENGTH_LONG).show();
        ArrayList<MenuSubItems> menuSubItemsArrayList = new ArrayList<MenuSubItems>();

        personlaizeAdapterType2 = new PersonlaizeAdapterType2(this, menuSubItemsArrayList);
        recyclerViewMenu.setAdapter(personlaizeAdapterType2);

        textviewQty.setText("01");
        textviewTotalPrice.setText("00");
        textviewTotalPriceCents.setText(".00");


    }


    @Override
    public void itemAddToCartFaild(String msg) {
        Toast.makeText(ActivityPersonlaize.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void itemAddToCartSocketFail(SelectedMenuDetails selectedMenuDetail, String msg) {
        Toast.makeText(ActivityPersonlaize.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void itemAddToCartOrderQtyExeed() {
        Toast.makeText(ActivityPersonlaize.this, "Exceed Maximum Order Quantity", Toast.LENGTH_LONG).show();
    }

    @Override
    public void itemAddToCartNoItems() {
        Toast.makeText(ActivityPersonlaize.this, "No items to add", Toast.LENGTH_LONG).show();
    }


    @Override
    public void cartAvailable() {
        Intent intentSingup = new Intent(this, ActivityCart.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
    }

    @Override
    public void cartNotAvailable() {
        Toast.makeText(ActivityPersonlaize.this, "No items added to cart yet", Toast.LENGTH_LONG).show();
    }


    @Override
    public void cartCountNumber(int count) {
        textviewCartCount.setText(String.valueOf(count));
    }

    @Override
    public void clareMenusFinsh(int cartcount) {


         textviewCartCount.setText(String.valueOf(cartcount));
        Toast.makeText(ActivityPersonlaize.this, "Item Added to cart", Toast.LENGTH_LONG).show();
        ArrayList<MenuSubItems> menuSubItemsArrayList = new ArrayList<MenuSubItems>();

        personlaizeAdapterType2 = new PersonlaizeAdapterType2(this, menuSubItemsArrayList);
        recyclerViewMenu.setAdapter(personlaizeAdapterType2);

        textviewQty.setText("01");
        textviewTotalPrice.setText("00");
        textviewTotalPriceCents.setText(".00");
       // finish();


    }


    public void priceUpdate() {
        personlaizePresenter.getTotalPrice(Integer.parseInt(textviewQty.getText().toString()), menuSizeCode);
    }
}
