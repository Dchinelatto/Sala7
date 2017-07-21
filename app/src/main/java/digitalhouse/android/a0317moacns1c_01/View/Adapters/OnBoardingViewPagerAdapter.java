package digitalhouse.android.a0317moacns1c_01.View.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.View.Fragments.OnBoardingFragment;

/**
 * Created by Gonza on 18/7/2017.
 */

public class OnBoardingViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> listaFragments;

    public OnBoardingViewPagerAdapter(FragmentManager fm, List<Fragment> listaFragments) {
        super(fm);
        this.listaFragments = listaFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return listaFragments.get(position);
    }

    @Override
    public int getCount() {
        return listaFragments.size();
    }
}
