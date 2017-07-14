package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Adapters.RecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ListaFragment extends Fragment implements RecyclerAdapter.EnviadorDatosAFragment {


    // VARIABLES GLOBALES DE LA CLASE
    private ReceptorMedia receptorMedia;
    protected List<Genero> listaGeneros;
    private SwipeRefreshLayout swipeRefreshLayout;


    // TRAIGO EL RECYCLERVIEW
    RecyclerView elRecyclerView;

    // INSTANCIO EL ADAPTER Y LE CARGO LA LISTA Y EL CONTEXTO
    RecyclerAdapter elAdapter;


    // DEFINO EL COMPORTAMIENTO DEL METODO DE LA INTERFAZ
    @Override
    public void enviarDatosAFragment(Media unaMedia, Integer posicion, String tipoPelicula, Integer generoID) {
        receptorMedia.recibirMedia(unaMedia, posicion, tipoPelicula, generoID);
    }


    // INTERFAZ QUE PERMITE AL FRAGMENT COMUNICARSE CON LA ACTIVITY
    public static interface ReceptorMedia {
        void recibirMedia(Media unaMedia, Integer posicion, String tipoPelicula, Integer generoID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // DEFINO EL TITULO DE LA ACTIONBAR
        getActivity().setTitle("Sala 7");

        // SE INFLA LA VIEWGROUP
        final View laView = inflater.inflate(R.layout.fragment_list, container, false);

        // TRAIGO EL RECYCLERVIEW
        elRecyclerView = (RecyclerView) laView.findViewById(R.id.recyclerview_listaCategorias);

        // INSTANCIO EL ADAPTER Y LE CARGO LA LISTA Y EL CONTEXTO
        elAdapter = new RecyclerAdapter(getContext(), this);

        // LE SETEO EL ADAPTER AL RECYCLERVIEW
        elRecyclerView.setAdapter(elAdapter);

        elRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        cargarLista();

        swipeRefreshLayout= (SwipeRefreshLayout) laView.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarLista();
            }
        });

        // DEVUELVO LA VIEW INFLADA Y EDITADA
        return laView;
    }




    // LE CARGO EL CONTEXTO A NUESTRA INTERFAZ
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        receptorMedia = (ReceptorMedia) context;
    }


    // METODO PARA INICIAR LA LISTA Y QUE HAGAN OVERRIDE LOS HIJOS
    public void cargarLista() {




    }


    public void setSwipeRefreshLayout(){

        swipeRefreshLayout.setRefreshing(false);
    }

}
