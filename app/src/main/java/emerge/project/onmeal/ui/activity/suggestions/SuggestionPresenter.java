package emerge.project.onmeal.ui.activity.suggestions;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SuggestionPresenter {


    void sendSuggestion(String name,String city,String suggestion);
    void signOut(Context context);
}
