package emerge.project.onmeal.ui.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import emerge.project.onmeal.ui.fragment.profile.address.FragmentProfileAddress;
import emerge.project.onmeal.ui.fragment.profile.contact.FragmentProfileContact;


public class ProfileViewPagerAdapter extends FragmentPagerAdapter {

    public ProfileViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FragmentProfileContact();
            case 1:
                return new FragmentProfileAddress();

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
