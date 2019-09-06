package emerge.project.onmeal.ui.activity.intro;



/**
 * Created by Himanshu on 4/4/2017.
 */

public class FragmentActivityIntroPresenterImpli implements FragmentActivityIntroPresenter,
        FragmentActivityIntroInteractor.OnViewPagerStepFinishedListener,FragmentActivityIntroInteractor.OnSaveAgrementFinishedListener{


    private FragmentActivityIntroView fragmentActivityIntroView;
    FragmentActivityIntroInteractor fragmentActivityIntroInteractor;


    public FragmentActivityIntroPresenterImpli(FragmentActivityIntroView fragmentActivityIntroView) {
        this.fragmentActivityIntroView = fragmentActivityIntroView;
        this.fragmentActivityIntroInteractor = new FragmentActivityIntroInteractorImpil();

    }


    @Override
    public void viewPagerStep(int step) {
        fragmentActivityIntroView.viewPagerStep(step);
    }

    @Override
    public void setViewPagerStep(int step) {
        fragmentActivityIntroInteractor.setViewPagerStep(step,this);
    }

    @Override
    public void saveAgrement() {
        fragmentActivityIntroInteractor.saveAgrement(this);
    }

    @Override
    public void saveAgrementSuccessful() {
        fragmentActivityIntroView.saveAgrementSuccessful();
    }
}
