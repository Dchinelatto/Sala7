package digitalhouse.android.a0317moacns1c_01.View.Adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragment;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragmentPeliculas;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragmentSeries;

/**
 * Created by Gonza on 7/6/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<ListaFragment> listaFragments;

    public ViewPagerAdapter(FragmentManager fm, List<ListaFragment> listaFragments) {
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

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        if(position==0){
            title = ListaFragmentPeliculas.TITULO_TAB_PELICULA;
        } else {
            title = ListaFragmentSeries.TITULO_TAB_SERIE;
        }

        return title;
    }
}
