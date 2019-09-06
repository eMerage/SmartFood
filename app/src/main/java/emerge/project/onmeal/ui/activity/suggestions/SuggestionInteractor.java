package emerge.project.onmeal.ui.activity.suggestions;


/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SuggestionInteractor {


    interface OnSendSuggestionFinishedListener {
        void sendSuggestionSuccess();
        void sendSuggestionFail();
    }
    void sendSuggestion(String name,String city,String suggestion,OnSendSuggestionFinishedListener onSendSuggestionFinishedListener);



}
