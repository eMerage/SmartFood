package emerge.project.onmeal.ui.activity.suggestions;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SuggestionInteractor {


    interface OnSendSuggestionFinishedListener {
        void sendSuggestionSuccess();
        void sendSuggestionFail();
    }
    void sendSuggestion(String name,String city,String suggestion,OnSendSuggestionFinishedListener onSendSuggestionFinishedListener);




    interface OnsignOutinishedListener {
        void signOutSuccess();
    }
    void signOut(Context context, OnsignOutinishedListener onsignOutinishedListener);



}
