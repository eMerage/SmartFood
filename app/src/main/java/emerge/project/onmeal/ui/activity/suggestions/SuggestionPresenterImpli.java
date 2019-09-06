package emerge.project.onmeal.ui.activity.suggestions;


import emerge.project.onmeal.ui.activity.profile.ProfileContactView;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractor;
import emerge.project.onmeal.ui.activity.profile.ProfileInteractorImpil;
import emerge.project.onmeal.ui.activity.profile.ProfilePresenter;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SuggestionPresenterImpli implements SuggestionPresenter,SuggestionInteractor.OnSendSuggestionFinishedListener{


    private SuggestionView suggestionView;
    SuggestionInteractor suggestionInteractor;


    public SuggestionPresenterImpli(SuggestionView suggestionView) {
        this.suggestionView = suggestionView;
        this.suggestionInteractor = new SuggestionInteractorImpil();

    }



    @Override
    public void sendSuggestion(String name,String city,String suggestion) {
        suggestionInteractor.sendSuggestion( name, city, suggestion,this);
    }
    @Override
    public void sendSuggestionSuccess() {
        suggestionView.sendSuggestionSuccess();
    }

    @Override
    public void sendSuggestionFail() {
        suggestionView.sendSuggestionFail();
    }

}
