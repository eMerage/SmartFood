package emerge.project.onmeal.ui.activity.profile;



import android.content.Context;

import com.luseen.logger.Logger;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.home.HomeInteractor;
import emerge.project.onmeal.utils.entittes.Foodtems;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import emerge.project.onmeal.utils.entittes.OutletItems;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Himanshu on 4/5/2017.
 */

public class ProfileInteractorImpil implements ProfileInteractor {


    Realm realm = Realm.getDefaultInstance();

    @Override
    public void signOut(Context context, final OnsignOutinishedListener onsignOutinishedListener) {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<User> resultsAddress = realm.where(User.class).findAll();
                resultsAddress.deleteAllFromRealm();


                onsignOutinishedListener.signOutSuccess();
            }
        });

    }

}
