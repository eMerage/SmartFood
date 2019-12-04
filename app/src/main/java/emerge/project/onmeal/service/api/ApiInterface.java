package emerge.project.onmeal.service.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.utils.entittes.AddressItems;
import emerge.project.onmeal.utils.entittes.FoodCategoryItems;
import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.MenuCategoryItems;
import emerge.project.onmeal.utils.entittes.MenuItems;
import emerge.project.onmeal.utils.entittes.MenuSize;
import emerge.project.onmeal.utils.entittes.OrderErrorReturn;
import emerge.project.onmeal.utils.entittes.OrderHistoryItems;
import emerge.project.onmeal.utils.entittes.OutletItems;
import emerge.project.onmeal.utils.entittes.TimeSlots;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Himanshu on 8/24/2017.
 */


public interface ApiInterface {


    @GET("MealTimeUser/GetUserByID")
    Observable<User> getUserByID(@Query("userID") int id);

    @GET("MealTimeUser/GetUserByEmail")
    Observable<User> getUserByEmail(@Query("email") String email, @Query("pushTokenId") String pushTokenId);

    @POST("MealTimeUser/RegisterM")
    Observable<JsonObject> userRegistration(@Body JsonObject userInfo);


    @POST("MealTimeUser/VerifyUser")
    Observable<JsonObject> userVerified(@Body JsonObject userInfo);

    @GET("MealTimeUser/ValidateUser")
    Observable<User> userLoginValidation(@Query("eMail") String email, @Query("Password") String password);

    @GET("MealTimeUser/GetNewVerificationCode")
    Observable<JsonObject> getNewValidationCode(@Query("userID") int userId);

    @POST("MealTimeUser/RegisterAddress")
    Observable<String> addNewAddress(@Body JsonObject address);

    @GET("MealTimeUser/GetAddresses")
    Observable<List<AddressItems>> getAddress(@Query("userId") int userId);


    @GET("MealTimeUser/GetUserFavourites")
    Observable<List<HomeFavouriteItems>> getFavouriteItems(@Query("userID") int userId);


    @GET("Menu/GetMenuTitles")
    Observable<List<Foodtems>> getMainFood(@Query("menuCategoryID") int menuCategoryID, @Query("userid") int userId, @Query("Addressid") String addressId,
                                           @Query("Dispatchtype") String dispatchType, @Query("searchType") String searchType,@Query("searchText") String searchText);


    @GET("Outlet/GetOutletsNearAddress")
    Observable<ArrayList<OutletItems>> getOutlet(@Query("userid") int userId, @Query("Addressid") String addressId,
                                                 @Query("Dispatchtype") String dispatchType, @Query("searchText") String searchText);


    @GET("Menu/GetMenuCategoriesForOutlet")
    Observable<List<MenuCategoryItems>> getMenuCategoriesForOutlet(@Query("outletID") int outletID);


    @GET("Menu/GetMenusForOutlet")
    Observable<List<MenuItems>> getMainFoodByOutlet(@Query("outletID") int outletId, @Query("menuCategoryID") int menuCategoryID);

    @GET("Menu/GetOutletMenusForMenuTitle")
    Observable<List<MenuItems>> getMainFoodByFood(@Query("menuTitleID") String foodId, @Query("userid") int userId, @Query("Addressid") String addressId, @Query("Dispatchtype") String dispatchType);



    @GET("Menu/GetFoodCategoriesForOutletMenuTitle")
    Observable<List<FoodCategoryItems>> GetFoodCategoriesForOutletMenuTitle(@Query("outletID") int outletId, @Query("menuCategoryID") int menuCategoryID,
                                                                      @Query("menuTitleID") int menuTitleID, @Query("outletMenuTitleID") int outletMenuTitleID,
                                                                      @Query("userID") int userID, @Query("addressID ") int addressID,
                                                                      @Query("dispatchType") String dispatchType, @Query("searchType") String searchType);


    @GET("Menu/GetMenuFoodItemForOutlets")
    Observable<List<MenuSubItems>> GetMenuFoodItemForOutlets(@Query("outletID") int outletId, @Query("menuCategoryID") int menuCategoryID,
                                                       @Query("menuTitleID") int menuTitleID, @Query("outletMenuTitleID") int outletMenuTitleID,
                                                       @Query("userID") int userID, @Query("addressID ") int addressID,
                                                       @Query("dispatchType") String dispatchType, @Query("searchType") String searchType, @Query("foodItemCategoryID") int foodItemCategoryID);

    @GET("Menu/GetFoodItemSizesForOutletMenuTitle")
    Observable<List<MenuSize>> GetFoodItemSizesForOutletMenuTitle(@Query("outletID") int outletId, @Query("menuCategoryID") int menuCategoryID,
                                                            @Query("menuTitleID") int menuTitleID, @Query("outletMenuTitleID") int outletMenuTitleID,
                                                            @Query("userID") int userID, @Query("addressID ") int addressID,
                                                            @Query("dispatchType") String dispatchType, @Query("searchType") String searchType);


    @GET("Menu/GetOutletMenuSetup")
    Call<JsonObject> GetOutletMenuSetup(@Query("outletMenuTitleID") int outletMenuTitleID);

    @POST("Order/ValidatePromoCode")
    Observable<JsonObject> validatePromoCode(@Body JsonObject promoInfo);


    @GET("Order/GetAvailableDeliveryTimeSlots")
    Observable<List<TimeSlots>> getTimeSlot();


    @GET("Order/VerifyPickupTime")
    Observable<Boolean> verifyPickupTime(@Query("orderTime") String orderTime, @Query("outletID") int outletID);


    @POST("Order/RegisterOrder")
    Observable<JsonObject> orderProsess(@Body JsonObject orderInfo);

    @GET("Order/GetBusinessRuleViolations")
    Observable<List<OrderErrorReturn>> getBusinessRuleViolations(@Query("orderCode") String orderCode);


    @GET("Order/GetOrderHistoryForUser")
    Observable<List<OrderHistoryItems>> orderHistor(@Query("UserID") String UserID);


    @POST("Outlet/SaveOutletSuggestion")
    Observable<String> SaveOutletSuggestion(@Body JsonObject suggestionInfo);


    @POST("Order/UpdateOrderPaymentReference")
    Call<Integer> updatePaymentReference(@Query("orderID") int orderID, @Query("paymentReference") String paymentReference);


    @POST("MealTimeUser/DeleteMealTimeUserAddress")
    Observable<Integer> deleteMealTimeUserAddress(@Query("addressID") int addresID);


    @POST("MealTimeUser/UpdateMealTimeUser")
    Observable<Integer> updateMealTimeUser(@Query("userID") int userID, @Query("gender") String gender, @Query("dateOfBirth") String dateOfBirth);


    @GET("Order/GetOrderHistoryByOrder")
    Observable<JsonObject> orderHistorDetails(@Query("OrderID") int orderID);

    @GET("Outlet/GetOutlet")
    Observable<OutletItems> getOutlet(@Query("outletID") int outletID);




}
