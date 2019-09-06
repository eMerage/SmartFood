package emerge.project.onmeal.ui.activity.intro;



/**
 * Created by Himanshu on 4/4/2017.
 */

public interface FragmentActivityIntroInteractor {

    interface OnViewPagerStepFinishedListener {
        void viewPagerStep(int step);
    }
    void setViewPagerStep(int step,OnViewPagerStepFinishedListener onViewPagerStepFinishedListener);


    interface OnSaveAgrementFinishedListener {
        void saveAgrementSuccessful();
    }
    void saveAgrement(OnSaveAgrementFinishedListener onSaveAgrementFinishedListener);




}
