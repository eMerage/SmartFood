package emerge.project.onmeal.ui.fragment.intro;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmeal.R;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntroPresenter;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntroPresenterImpli;
import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntroView;


@SuppressLint("ValidFragment")
public class StepTwo extends Fragment {



    FragmentActivityIntroPresenter fragmentActivityIntroPresenter;

    public StepTwo(FragmentActivityIntroView fragmentActivityIntroView) {
        fragmentActivityIntroPresenter = new FragmentActivityIntroPresenterImpli(fragmentActivityIntroView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_intro_steptwo, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @OnClick(R.id.relativelayout2)
    public void onClickNext(View view) {
        fragmentActivityIntroPresenter.setViewPagerStep(2);

    }


}
