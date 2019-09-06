package emerge.project.onmeal.ui.activity.suggestions;


import com.google.gson.JsonObject;
import com.luseen.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import emerge.project.onmeal.data.table.User;
import emerge.project.onmeal.service.api.ApiClient;
import emerge.project.onmeal.service.api.ApiInterface;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractor;
import emerge.project.onmeal.utils.entittes.HomeFavouriteItems;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Himanshu on 4/5/2017.
 */

public class SuggestionInteractorImpil implements SuggestionInteractor {

    Realm realm = Realm.getDefaultInstance();
    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

    String sendSuggestion;

    @Override
    public void sendSuggestion(String name, String city, String suggestion, final OnSendSuggestionFinishedListener onSendSuggestionFinishedListener) {

        User user = realm.where(User.class).findFirst();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserStamp", Integer.parseInt(user.getUserId()));
        jsonObject.addProperty("Name", name);
        jsonObject.addProperty("City", city);
        jsonObject.addProperty("Description", suggestion);
        try {
            apiService.SaveOutletSuggestion(jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(String respond) {
                            sendSuggestion = respond;
                        }

                        @Override
                        public void onError(Throwable e) {
                            onSendSuggestionFinishedListener.sendSuggestionFail();
                        }

                        @Override
                        public void onComplete() {
                            if (sendSuggestion != null) {
                                try {
                                    if (sendSuggestion.equals("0")) {
                                        onSendSuggestionFinishedListener.sendSuggestionSuccess();
                                    } else {
                                        onSendSuggestionFinishedListener.sendSuggestionFail();
                                    }
                                } catch (NullPointerException exNull) {
                                    onSendSuggestionFinishedListener.sendSuggestionFail();
                                }


                            } else {
                                onSendSuggestionFinishedListener.sendSuggestionFail();
                            }
                        }
                    });

        } catch (Exception ex) {
            onSendSuggestionFinishedListener.sendSuggestionFail();
        }

    }
}
