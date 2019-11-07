package emerge.project.onmeal.ui.activity.menu;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuyenmonkey.mkloader.MKLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.service.network.NetworkAvailability;
import emerge.project.onmeal.ui.activity.cart.ActivityCart;
import emerge.project.onmeal.ui.activity.home.ActivityHome;
import emerge.project.onmeal.ui.activity.personlaize.ActivityPersonlaize;
import emerge.project.onmeal.ui.adaptor.HomeOutletImagesAdaptor;
import emerge.project.onmeal.ui.adaptor.MenuAdapter;
import emerge.project.onmeal.ui.adaptor.MenuCategoryAdapter;
import emerge.project.onmeal.ui.dialog.CustomDialogOne;
import emerge.project.onmeal.ui.dialog.CustomDialogTwo;
import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;
import emerge.project.onmeal.utils.entittes.OutletItems;
import emerge.project.onmeal.utils.entittes.SelectedMenuDetails;

public class ActivityMenu extends Activity implements MenuView, OnMapReadyCallback {


    @BindView(R.id.imageView_cover_image)
    ImageView imageViewCoverImage;

    @BindView(R.id.progressBar_coverImage)
    ProgressBar progressBarCoverImage;

    @BindView(R.id.textview_titel)
    TextView textviewTitel;

    @BindView(R.id.textview_titel_cat)
    TextView textviewTitelCat;

    @BindView(R.id.recyclerView_menu_cat)
    RecyclerView recyclerViewMenuCat;

    @BindView(R.id.recyclerView_menu)
    RecyclerView recyclerViewMenu;


    @BindView(R.id.textview_cart_count)
    TextView textviewCartCount;

    @BindView(R.id.button_cover_image_more)
    ImageView btnCoverImageMore;




    @BindView(R.id.proprogressview)
    MKLoader proprogressview;

    OutletItems outlet;
    Foodtems food;

    MenuPresenter menuPresenter;
    MenuCategoryAdapter menuCategoryAdapter;


    MenuAdapter menuAdapter;


    double latitude = 0.0,longitude = 0.0;
    GoogleMap mMap ;

    MapView mapView;
    TextView textNoLocation;
    ImageView ImageView_googlemap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);


        menuPresenter = new MenuPresenterImpli(this);

        outlet = (OutletItems) getIntent().getSerializableExtra("OUTLET");
        food = (Foodtems) getIntent().getSerializableExtra("FOOD");

        setMenuCatRecycalView();
        setMenuRecycalView();

        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            bloackUserInteraction();

            if (outlet == null) {
                setCoverImage(food.getFoodCoverImage(), food.getFoodName(), food.getMenuCategory());
                btnCoverImageMore.setVisibility(View.GONE);
                menuPresenter.getMenuCategory(null, food.getFoodId(), food.getMenuCategory());
                menuPresenter.getMainFoodByFood(food.getFoodId());

            } else {
                btnCoverImageMore.setVisibility(View.VISIBLE);
                setCoverImage(outlet.getOutletImageUrl(), outlet.getOutletName(), outlet.getOutletCity());
                menuPresenter.getMenuCategory(outlet.getOutletId(), null, null);
            }
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

        menuPresenter.cartCount();




        btnCoverImageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               showOutletMoreDetails();


            }
        });







    }




    @OnClick(R.id.relativelayout_cart)
    public void onClickCart(View view) {
        menuPresenter.checkCartAvailabilityForCart();

    }




    @OnClick(R.id.relativelayout_menu)
    public void onClickBackMenu(View view) {
        menuPresenter.checkCartAvailability();

    }

    @Override
    protected void onRestart() {
        menuPresenter.cartCount();



        super.onRestart();

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

                return false;
            }
        };


        textviewTitel.setText(title);
        textviewTitelCat.setText(cat);

        textviewTitel.setVisibility(View.VISIBLE);
        textviewTitelCat.setVisibility(View.VISIBLE);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .apply(requestOptions)
                .listener(requestListener)
                .into(imageViewCoverImage);


    }


    private void setMenuCatRecycalView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMenuCat.setLayoutManager(layoutManager);
        recyclerViewMenuCat.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMenuCat.setNestedScrollingEnabled(false);

    }


    private void setMenuRecycalView() {
        recyclerViewMenu.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerViewMenu.setLayoutManager(gridLayoutManager);
        recyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMenu.setNestedScrollingEnabled(true);

    }


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {
        menuPresenter.checkCartAvailability();
    }


    @Override
    public void menuCategory(ArrayList<MenuCategoryItems> menuCategoryItems) {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

        menuCategoryAdapter = new MenuCategoryAdapter(this, menuCategoryItems, this);
        recyclerViewMenuCat.setAdapter(menuCategoryAdapter);

    }

    @Override
    public void menuCategoryFail(String msg) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();

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
                                if (outlet == null) {
                                    setCoverImage(food.getFoodCoverImage(), food.getFoodName(), food.getMenuCategory());
                                    menuPresenter.getMenuCategory(null, food.getFoodId(), food.getMenuCategory());
                                } else {
                                    setCoverImage(outlet.getOutletImageUrl(), outlet.getOutletName(), "Colombo 06");
                                    menuPresenter.getMenuCategory(outlet.getOutletId(), null, null);
                                }

                            } else {
                                Toast.makeText(ActivityMenu.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

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
    public void getMainFoodByOutletSuccess(ArrayList<MenuItems> menuItems) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        menuAdapter = new MenuAdapter(this, menuItems, true, this);
        recyclerViewMenu.setAdapter(menuAdapter);
    }


    @Override
    public void getMainFoodByOutletLoadEmpty() {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        Toast.makeText(this, "No Items", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void getMainFoodByFoodSuccess(ArrayList<MenuItems> menuItems) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        menuAdapter = new MenuAdapter(this, menuItems, false, this);
        recyclerViewMenu.setAdapter(menuAdapter);
    }

    @Override
    public void getMainFoodByFoodLoadFail(String msg) {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();


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
                                menuPresenter.getMainFoodByFood(food.getFoodId());
                            } else {
                                Toast.makeText(ActivityMenu.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

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
    public void getMainFoodByFoodLoadEmpty() {
        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();
        Toast.makeText(this, "No Items", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMainFoodByOutletLoadFail(String msg, final String outletId, final String menuCategoryID) {

        proprogressview.setVisibility(View.GONE);
        unBloackUserInteraction();


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
                                if (outlet == null) {
                                } else {
                                    menuPresenter.getMainFoodByOutlet(outletId, menuCategoryID);
                                }
                            } else {
                                Toast.makeText(ActivityMenu.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

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
    public void selectedMenuCategor(String menuCategoryID) {
        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            proprogressview.setVisibility(View.VISIBLE);
            bloackUserInteraction();
            if (outlet == null) {
            } else {
                menuPresenter.getMainFoodByOutlet(outlet.getOutletId(), menuCategoryID);
            }

        } else {
            Toast.makeText(ActivityMenu.this, "No Internet Access, Please try again", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void selectedMenuDetails(int menuId, int foodId, int outletId, String menuName, String menuImg, String outlet,int menucat) {


        SelectedMenuDetails selectedMenuDetails = new SelectedMenuDetails();
        selectedMenuDetails.setMenuId(menuId);
        selectedMenuDetails.setFoodId(foodId);
        selectedMenuDetails.setOutletId(outletId);
        selectedMenuDetails.setMenuImg(menuImg);
        selectedMenuDetails.setOutletName(outlet);
        selectedMenuDetails.setMenuName(menuName);
        selectedMenuDetails.setMenuCat(menucat);

        Intent intentSingup = new Intent(this, ActivityPersonlaize.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        intentSingup.putExtra("SELECTEDMENU", selectedMenuDetails);
        startActivity(intentSingup, bndlanimation);




    }


    @Override
    public void cartAvailable() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Warning");
        alertDialogBuilder.setMessage("Your Cart will be clear");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ActivityMenu.this, ActivityHome.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ActivityMenu.this, R.anim.fade_in, R.anim.fade_out).toBundle();
                        startActivity(intent, bndlanimation);
                        finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();
    }

    @Override
    public void cartNotAvailable() {
        Intent intent = new Intent(this, ActivityHome.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intent, bndlanimation);
        finish();
    }

    @Override
    public void cartCountNumber(int count) {
        textviewCartCount.setText(String.valueOf(count));
    }




    @Override
    public void cartAvailableForCart() {
        Intent intentSingup = new Intent(this, ActivityCart.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        startActivity(intentSingup, bndlanimation);
    }

    @Override
    public void cartNotAvailableForCart() {
        Toast.makeText(this, "No items added to cart yet", Toast.LENGTH_LONG).show();
    }

    private void showOutletMoreDetails(){

        final Dialog dialogOutletMoreDetails = new Dialog(this);
        dialogOutletMoreDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOutletMoreDetails.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogOutletMoreDetails.setContentView(R.layout.dialog_outlet_more_details);
        dialogOutletMoreDetails.setCancelable(true);



        RecyclerView  recyclerViewOutletImages= dialogOutletMoreDetails.findViewById(R.id.recyclerView_outletimages);
        TextView  text3= dialogOutletMoreDetails.findViewById(R.id.text3);
         ImageView_googlemap= dialogOutletMoreDetails.findViewById(R.id.ImageView_googlemap);

        textNoLocation =  dialogOutletMoreDetails.findViewById(R.id.textview_nolocations);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOutletImages.setLayoutManager(layoutManager);
        recyclerViewOutletImages.setItemAnimator(new DefaultItemAnimator());
        recyclerViewOutletImages.setNestedScrollingEnabled(true);


         mapView= dialogOutletMoreDetails.findViewById(R.id.mapView_outlet);


        latitude = outlet.getOutletLatitude();
        longitude = outlet.getOutletLongitude();

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }


        if(outlet.getOutletImages().isEmpty()){
            text3.setVisibility(View.VISIBLE);
            recyclerViewOutletImages.setVisibility(View.INVISIBLE);

        }else {
            text3.setVisibility(View.GONE);
            recyclerViewOutletImages.setVisibility(View.VISIBLE);

            HomeOutletImagesAdaptor homeOutletImagesAdaptor = new HomeOutletImagesAdaptor(this,outlet.getOutletImages());
            recyclerViewOutletImages.setAdapter(homeOutletImagesAdaptor);
        }



        TextView textView_outletdetails_name= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_name);
        textView_outletdetails_name.setText(outlet.getOutletName());

        TextView textView_outletdetails_city= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_city);
        textView_outletdetails_city.setText(outlet.getOutletCity());

        TextView textView_outletdetails_ownersname= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_ownersname);
        textView_outletdetails_ownersname.setText(outlet.getOutletOwnersName());

        TextView textView_outletdetails_contactnumber= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_contactnumber);
        textView_outletdetails_contactnumber.setText(outlet.getPhoneNUmber());


        TextView textView_outletdetails_email= dialogOutletMoreDetails.findViewById(R.id.textView_outletdetails_email);
        textView_outletdetails_email.setText(outlet.geteMail());




        ImageView_googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(latitude==0.0){

                }else {
                    String latit = String.valueOf(latitude);
                    String longi = String.valueOf(longitude);


                    String label = outlet.getOutletName();
                    String uriBegin = "geo:" + latit + "," + longi;
                    String query = latit + "," + longi + "(" + label + ")";
                    String encodedQuery = Uri.encode(query);
                    String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";

                    Uri uri = Uri.parse(uriString);

                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    try{
                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mapIntent);
                        }
                    }catch (NullPointerException e){
                    }


                }



            }
        });





        dialogOutletMoreDetails.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this);



        mMap = googleMap;



        LatLng locationMap = new LatLng(latitude, longitude);
        if (latitude == 0.0) {
            textNoLocation.setVisibility(View.VISIBLE);
            mapView.setVisibility(View.INVISIBLE);
            ImageView_googlemap.setVisibility(View.GONE);


        } else {
            textNoLocation.setVisibility(View.GONE);
            mapView.setVisibility(View.VISIBLE);
            ImageView_googlemap.setVisibility(View.VISIBLE);

            mMap.addMarker(new MarkerOptions().position(locationMap));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMap, 17));

        }

    }
}
