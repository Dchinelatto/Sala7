package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Controller.ControllerMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.View.Adapters.GeneroAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaResultados extends Fragment {


    private RecyclerView recyclerView;
    private GeneroAdapter generoAdapter;
    private ListaFragment.ReceptorMedia receptorMedia;
    public static final Integer CODIGO_RESULTADOS = 101010;


    public ListaResultados() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista_resultados, container, false);

        Bundle bundle= getArguments();
        final String busqueda= bundle.getString("busqueda");

        recyclerView= (RecyclerView) view.findViewById(R.id.contenedorBusqueda);
        generoAdapter = new GeneroAdapter(getContext());
        recyclerView.setAdapter(generoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        ControllerMedia controllerMedia= new ControllerMedia(getContext());
        controllerMedia.traerBuscador(busqueda, new ResultListener<List<Media>>() {
            @Override
            public void finish(final List<Media> resultado) {
                generoAdapter.setListaMedia(resultado);
                generoAdapter.notifyDataSetChanged();

                generoAdapter.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer posicion = recyclerView.getChildAdapterPosition(view);
                        Media laMedia = resultado.get(posicion);
                        receptorMedia.recibirMedia(laMedia, posicion, busqueda, CODIGO_RESULTADOS );
                    }
                });


            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        receptorMedia = (ListaFragment.ReceptorMedia) context;
    }

}

