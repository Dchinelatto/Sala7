package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Controller.ControllerMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.View.Adapters.RecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaFragmentPeliculas extends ListaFragment implements RecyclerAdapter.EnviadorDatosAFragment {

    public static final String TITULO_TAB_PELICULA = "Peliculas";

    @Override
    public void cargarLista() {
        super.listaGeneros = new ArrayList<>();

        ControllerMedia controllerListaGeneroInternet = new ControllerMedia(getContext());
        controllerListaGeneroInternet.traerListaGenero(new ResultListener<List<Genero>>() {
            @Override
            public void finish(List<Genero> resultado) {
                listaGeneros = resultado;

                elAdapter.setListaGenero(resultado);

                elAdapter.notifyDataSetChanged();
                setSwipeRefreshLayout();



            }
        });
    }
}