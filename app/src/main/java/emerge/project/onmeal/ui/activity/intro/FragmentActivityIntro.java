package emerge.project.onmeal.ui.activity.intro;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import emerge.project.onmeal.R;
import emerge.project.onmeal.ui.activity.login.ActivityLogin;
import emerge.project.onmeal.ui.adaptor.FragmentIntroAdaptor;
import emerge.project.onmeal.ui.fragment.intro.StepFour;

public class FragmentActivityIntro extends FragmentActivity implements FragmentActivityIntroView {

    @BindView(R.id.pager)
    ViewPager viewPager;

    FragmentActivityIntroPresenter fragmentActivityIntroPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_intro);
        ButterKnife.bind(this);



        fragmentActivityIntroPresenter = new FragmentActivityIntroPresenterImpli(this);

        List<String> mFragmentTitleList = new ArrayList<>();
        mFragmentTitleList.add("StepOne");
        mFragmentTitleList.add("StepTwo");
        mFragmentTitleList.add("StepThree");
        mFragmentTitleList.add("StepFour");


        assert viewPager != null;
        viewPager.setAdapter(new FragmentIntroAdaptor(getSupportFragmentManager(), mFragmentTitleList, this));

    }


    @Override
    public void viewPagerStep(int step) {
        viewPager.setCurrentItem(step);

    }

    @Override
    public void saveAgrementSuccessful() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
