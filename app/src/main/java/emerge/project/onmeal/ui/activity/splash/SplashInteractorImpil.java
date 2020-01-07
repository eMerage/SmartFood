package emerge.project.onmeal.ui.activity.splash;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.luseen.logger.Logger;


import emerge.project.onmeal.data.table.Address;
import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.data.table.Agrement;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.utils.entittes.ErrorObject;
import emerge.project.onmeal.utils.entittes.UpdateToken;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class SplashInteractorImpil implements SplashInteractor {

    Realm realm;

    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    UpdateToken updateToken =  new UpdateToken();
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

    @Override
    public void updatePushTokenAndAppVersion(Context con, final OnUpdatePushTokenAndAppVersionFinishedListener onUpdatePushTokenAndAppVersionFinishedListener) {
        realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();

        int versionCode = 0;

        try {
            PackageInfo pInfo = con.getPackageManager().getPackageInfo(con.getPackageName(), 0);
            versionCode = pInfo.versionCode;

        } catch(PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }



        int userID = 0;
        try {
            userID = Integer.parseInt(user.getUserId());
        }catch (NumberFormatException num){

        }


        final JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("UserID", userID);
        jsonObject.addProperty("appVersion", versionCode);
        jsonObject.addProperty("AppType", "M");
        jsonObject.addProperty("AppOs", "A");


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            jsonObject.addProperty("pushToken", "");

                            return;
                        }else {
                            String token = task.getResult().getToken();

                            jsonObject.addProperty("pushToken", token);

                        }

                        updateTokenToServer(jsonObject,onUpdatePushTokenAndAppVersionFinishedListener);

                    }
                });

    }


    private void updateTokenToServer(JsonObject jsonObject, final OnUpdatePushTokenAndAppVersionFinishedListener onUpdatePushTokenAndAppVersionFinishedListener){


        final ErrorObject errorObject =  new ErrorObject();
        errorObject.setErrorCode("CE");
        errorObject.setErrorMessage("Communication error, Please try again");


        try {
            apiService.saveMealTimeUserPushToken(jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<UpdateToken>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(UpdateToken respond) {
                          updateToken = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            updateToken.setError(errorObject);
                            onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(false,updateToken);

                        }

                        @Override
                        public void onComplete() {
                            onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(updateToken.isStatus(),updateToken);

                        }
                    });

        } catch (Exception ex) {
            updateToken.setError(errorObject);
            onUpdatePushTokenAndAppVersionFinishedListener.updateStatus(false,updateToken);
        }


    }
}


