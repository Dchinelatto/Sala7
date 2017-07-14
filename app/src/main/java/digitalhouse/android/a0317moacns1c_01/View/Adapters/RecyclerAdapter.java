package digitalhouse.android.a0317moacns1c_01.View.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.*;
import digitalhouse.android.a0317moacns1c_01.Controller.ControllerMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragmentSeries;

/**
 * Created by Danilo on 26/05/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter implements ResultListener<Media> {

    // ATRIBUTOS DEL ADAPTER PASADOS POR PARAMETRO
    private List<Genero> listaGenero;

    private Context context;
    private EnviadorDatosAFragment enviadorDatosAFragment;
    private List<Media> listaMedia;
    private boolean isLoading = false;
    private ControllerMedia controllerMedia;
    private LinearLayoutManager linearLayoutManager;

    // CONSTRUCTOR DEL ADAPTER
    public RecyclerAdapter(Context elContexto, Fragment elFragment) {
        listaGenero = new ArrayList<>();
        this.context = elContexto;
        enviadorDatosAFragment = (EnviadorDatosAFragment) elFragment;
    }

    @Override
    public void finish(Media resultado) {

    }

    // CREO UNA INTERFAZ MANDARAFRAGMENT PARA COMUNICARME CON EL FRAGMENT
    public interface EnviadorDatosAFragment {
        void enviarDatosAFragment(Media unaMedia, Integer posicion, String tipoPelicula, Integer generoID);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        // TRAIGO UN INFLADOR
        LayoutInflater elInflador = LayoutInflater.from(context);

        // INFLO LA VIEW
        View laView = elInflador.inflate(R.layout.estetica_recycler, viewGroup, false);

        // INSTANCIO UN VIEWHOLDER
        ViewHolder elHolder = new ViewHolder(laView);

        // DEVUELVO EL VIEWHOLDER
        return elHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        // TRAIGO EL GENERO ACTUAL
        Genero elGenero = listaGenero.get(i);

        // CASTEO EL HOLDER
        ViewHolder elHolder = (ViewHolder) viewHolder;

        // CARGO LOS DATOS CON EL METODO DEL HOLDER
        elHolder.cargarDatos(elGenero);
    }

    @Override
    public int getItemCount() {
        return listaGenero.size();
    }

    // CREO UNA CLASE VIEWHOLDER PROPIA
    private class ViewHolder extends RecyclerView.ViewHolder {

        // ATRIBUTOS (VIEWS) DEL HOLDER
        TextView textViewGenero;
        RecyclerView laRecyclerView;

        // CONSTRUCTOR DEL HOLDER
        public ViewHolder(View itemView) {
            super(itemView);
            laRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview_contenedorRecyclerView);
            textViewGenero = (TextView) itemView.findViewById(R.id.textView_recycler_genero);
        }


        // METODO PARA CARGAR LOS DATOS AL HOLDER
        public void cargarDatos(final Genero unGenero) {

            // CARGA DE TEXTO PARA EL TITULO DEL GENERO
            textViewGenero.setText(unGenero.getName());

            listaMedia = new ArrayList<>();

            // INSTANCIO UN ADAPTER PARA EL RECYCLERVIEW
            final PeliAdapter elAdapter = new PeliAdapter(context);

            // SETEO EL ADAPTER
            laRecyclerView.setAdapter(elAdapter);
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            // SETEO EL LAYOUT MANAGER
            laRecyclerView.setLayoutManager(linearLayoutManager);

            // INSTANCIO EL CONTROLLER PARA TRAER LAS LISTAS
            controllerMedia = new ControllerMedia(context);

            final String tipoMedia = getTipo();

            controllerMedia.traerListaMediaPorGenero(unGenero.getId(), tipoMedia, new ResultListener<List<Media>>() {
                @Override
                public void finish(final List<Media> resultado) {
//                      getNewPage(laRecyclerView,unGenero.getId(),tipoMedia);
                    elAdapter.setListaMedia(resultado);

                    // SETEO EL LISTENER
                    elAdapter.setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Integer posicion = laRecyclerView.getChildLayoutPosition(view);
                            Media laMedia = resultado.get(posicion);
                            enviadorDatosAFragment.enviarDatosAFragment(laMedia, posicion, tipoMedia, unGenero.getId());
                        }
                    });

                    elAdapter.notifyDataSetChanged();
                }
            });


//            laRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    if (!isLoading) {
//
//                        Integer lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//
//                        Integer totalItemCount = linearLayoutManager.getItemCount();
//
//                        if (lastVisibleItem >= totalItemCount - 3) {
//                            getNewPage(laRecyclerView, unGenero.getId(), tipoMedia);
//                        }
//                    }
//
//                }
//            });

        }

    }


    public void getNewPage(final RecyclerView laRecyclerView, final Integer id, String tipo) {


        final String finalTipoMedia = tipo;


        if (controllerMedia.isAnyPageAvailable()) {
            isLoading = true;
            controllerMedia.traerListaMediaPorGenero(id, tipo, new ResultListener<List<Media>>() {

                @Override
                public void finish(List<Media> resultado) {
                    listaMedia = resultado;

                    // INSTANCIO UN ADAPTER PARA EL RECYCLERVIEW
                    PeliAdapter elAdapter = new PeliAdapter(context);
                    elAdapter.setListaMedia(resultado);

                    // SETEO EL ADAPTER
                    laRecyclerView.setAdapter(elAdapter);

                    elAdapter.notifyDataSetChanged();


                    // SETEO EL LISTENER
                    elAdapter.setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Integer posicion = laRecyclerView.getChildLayoutPosition(view);
                            Media laMedia = listaMedia.get(posicion);
                            enviadorDatosAFragment.enviarDatosAFragment(laMedia, posicion, finalTipoMedia, id);
                        }
                    });

                    isLoading = false;
                }
            });


        }


    }

    public String getTipo() {

        String tipoMedia;

        if (enviadorDatosAFragment instanceof ListaFragmentSeries) {
            tipoMedia = TMDBHelper.TIPO_SERIE;
        } else {
            tipoMedia = TMDBHelper.TIPO_PELICULA;
        }

        return tipoMedia;

    }

    public void setListaGenero(List<Genero> listaGenero) {
        this.listaGenero = listaGenero;
    }
}
