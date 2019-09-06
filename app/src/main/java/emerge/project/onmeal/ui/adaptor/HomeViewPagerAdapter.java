package emerge.project.onmeal.ui.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import emerge.project.onmeal.ui.fragment.home.FragmentHomeFood;
import emerge.project.onmeal.ui.fragment.home.FragmentHomeOutlet;


public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentHomeFood();
            case 1:
                return new FragmentHomeOutlet();

        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position+1;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
