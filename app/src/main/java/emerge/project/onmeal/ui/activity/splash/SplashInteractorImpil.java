package emerge.project.onmeal.ui.activity.splash;


import com.luseen.logger.Logger;


import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.data.table.Agrement;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class SplashInteractorImpil implements SplashInteractor {

    Realm realm;

    @Override
    public void checkUser(OnCheckUserFinishedListener onCheckUserFinishedListener) {
        realm = Realm.getDefaultInstance();
        Long user = realm.where(User.class).count();

        Long Agrement = realm.where(Agrement.class).count();

        if(user.intValue()==0){
            onCheckUserFinishedListener.userNotAvailable();
            if(Agrement.intValue()==0){
                onCheckUserFinishedListener.userNotAvailable();
            }else {
                onCheckUserFinishedListener.userSingOut();
            }

        }else {
            onCheckUserFinishedListener.userAvailable();
        }

    }

    @Override
    public void deleteLocalOrderData(final OnDeleteLocalOrderDataFinishedListener onDeleteLocalOrderDataFinishedListener) {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Address> resultsAddress = realm.where(Address.class).findAll();
                resultsAddress.deleteAllFromRealm();


                onDeleteLocalOrderDataFinishedListener.deletetedData();
            }
        });

    }
}


