package emerge.project.onmeal.ui.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import emerge.project.onmeal.ui.activity.intro.FragmentActivityIntroView;
import emerge.project.onmeal.ui.fragment.intro.StepFour;
import emerge.project.onmeal.ui.fragment.intro.StepOne;
import emerge.project.onmeal.ui.fragment.intro.StepThree;
import emerge.project.onmeal.ui.fragment.intro.StepTwo;


public class FragmentIntroAdaptor extends FragmentPagerAdapter {


    private List<String> mFragmentTitleList = new ArrayList<>();
    FragmentActivityIntroView fragmentActivityIntroView;


    public FragmentIntroAdaptor(FragmentManager fm, List<String> mFragmentTitleList,FragmentActivityIntroView fragmentActivityIntroView) {
        super(fm);
        this.mFragmentTitleList = mFragmentTitleList;
        this.fragmentActivityIntroView=fragmentActivityIntroView;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StepOne(fragmentActivityIntroView);
            case 1:
                return new StepTwo(fragmentActivityIntroView);
            case 2:
                return new StepThree(fragmentActivityIntroView);
            case 3:
                return new StepFour();



        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
