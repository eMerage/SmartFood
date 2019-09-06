package emerge.project.onmeal.ui.activity.orderddetails;



import com.google.gson.JsonObject;
import com.luseen.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.data.table.CartDetail;
import emerge.project.onmeal.data.table.CartHeader;
import emerge.project.onmeal.data.table.MenuSubItems;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.cart.CartInteractor;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractor;
import emerge.project.onmeal.utils.entittes.TimeSlots;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Himanshu on 4/5/2017.
 */

public class OrderDetailsInteractorImpil implements OrderDetailsInteractor {



    Realm realm;


    @Override
    public void getCartItems(OnCartItemsFinishedListener onCartItemsFinishedListener) {
        realm = Realm.getDefaultInstance();
        ArrayList<CartHeader> cartHeaderArrayList = new ArrayList<CartHeader>();
        for (CartHeader cartHeader : realm.where(CartHeader.class).equalTo("isActive",true).findAll()) {
            cartHeaderArrayList.add(new CartHeader(cartHeader.getCartHeaderId(), cartHeader.getOutletMenuTitleID(),
                    cartHeader.getOutletID(), cartHeader.getOutlet(),cartHeader.getName(),cartHeader.getImageUrl(),cartHeader.getSize(), cartHeader.getQuantity(),
                    cartHeader.getPriceTotal(),cartHeader.isActive()));
        }
        onCartItemsFinishedListener.cartItems(cartHeaderArrayList);
    }

    @Override
    public void removeOrederData() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmResults<CartDetail> resultsCartDetail = bgRealm.where(CartDetail.class).findAll();
                resultsCartDetail.deleteAllFromRealm();

                RealmResults<CartHeader> resultsCartHeader = bgRealm.where(CartHeader.class).findAll();
                resultsCartHeader.deleteAllFromRealm();

                RealmResults<MenuSubItems> resultsMenuSubItems = bgRealm.where(MenuSubItems.class).findAll();
                resultsMenuSubItems.deleteAllFromRealm();

            }
        });
    }
}
