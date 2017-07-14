package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Adapters.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private List<ListaFragment> listaFragments;
    private ListaFragmentPeliculas fragmentPeliculas;
    private ListaFragmentSeries fragmentSeries;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        getActivity().setTitle("Sala 7");

        //BUSCO EL VIEWPAGER Y EL TABLAYOUT
        viewPager = (ViewPager) view.findViewById(R.id.viewPagerFragmentPantalla);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        listaFragments = new ArrayList<>();

        //CREO LOS FRAGMENTS
        fragmentPeliculas = new ListaFragmentPeliculas();
        fragmentSeries = new ListaFragmentSeries();

        Bundle bundle = new Bundle();
        bundle.putString(ListaFragmentPeliculas.TITULO_TAB_PELICULA, ListaFragmentPeliculas.TITULO_TAB_PELICULA);
        bundle.putString(ListaFragmentSeries.TITULO_TAB_SERIE, ListaFragmentSeries.TITULO_TAB_SERIE);

        fragmentPeliculas.setArguments(bundle);

        //AGREGO LOS FRAGMENTS A LA LISTA
        listaFragments.add(fragmentPeliculas);
        listaFragments.add(fragmentSeries);

        //CREO EL ADAPTER
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), listaFragments);

        //LE SETEO EL ADAPTER AL VIEWPAGER
        viewPager.setAdapter(viewPagerAdapter);

        //LE SETEO EL VIEWPAGER AL TABLAYOUT
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
