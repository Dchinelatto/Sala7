package digitalhouse.android.a0317moacns1c_01.View.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.View.Fragments.DetalleFragment;

/**
 * Created by Danilo on 30/05/2017.
 */

public class DetalleViewPagerAdapter extends FragmentStatePagerAdapter {

    // ATRIBUTOS
    private List<DetalleFragment> listaFragments;

    // CONSTRUCTOR
    public DetalleViewPagerAdapter(FragmentManager fm, List<DetalleFragment> listaFragments) {
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
