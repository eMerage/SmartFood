package emerge.project.onmeal.ui.activity.intro;


import com.luseen.logger.Logger;

import emerge.project.onmeal.data.table.Agrement;
import io.realm.Realm;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class FragmentActivityIntroInteractorImpil implements FragmentActivityIntroInteractor {

    Realm realm;
    @Override
    public void setViewPagerStep(int step, OnViewPagerStepFinishedListener onViewPagerStepFinishedListener) {
        onViewPagerStepFinishedListener.viewPagerStep(step);
    }

    @Override
    public void saveAgrement(final OnSaveAgrementFinishedListener onSaveAgrementFinishedListener) {

        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                final Long AgrementList = bgRealm.where(Agrement.class).count();
                Agrement agrement = bgRealm.createObject(Agrement.class, (AgrementList.intValue()+1));
                agrement.setAgrementApproved(true);


                onSaveAgrementFinishedListener.saveAgrementSuccessful();

            }
        });


    }
}
