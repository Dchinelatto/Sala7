package digitalhouse.android.a0317moacns1c_01.View.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;

/**
 * Created by Danilo on 19/05/2017.
 */

public class PeliAdapter extends RecyclerView.Adapter implements View.OnClickListener,ResultListener{

    // VARIABLES DEL ADAPTER
    private List<Media> listaMedia;
    private Context context;
    private View.OnClickListener listener;

    // CONSTRUCTOR DEL ADAPTER
    public PeliAdapter(Context context) {
        this.listaMedia = new ArrayList<>();
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // CREO UN INFLADOR
        LayoutInflater elInflador = LayoutInflater.from(context);

        // INFLO LA VISTA EN EL LAYOUT
        View laView = elInflador.inflate(R.layout.estetica_recycler_pelis, parent, false);

        // LE PONGO UN LISTENER A LA VIEW Y PASO AL ADAPTER COMO PARAMETRO
        laView.setOnClickListener(this);

        // INSTANCIO EL VIEWHOLDER
        ViewHolder elViewHolder = new ViewHolder(laView);

        return elViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // TRAIGO LA PELICULA DE LA LISTA
        Media laMedia = listaMedia.get(position);

        // CASTEO EL VIEWHOLDER DEL PARAMETRO
        ViewHolder elHolder = (ViewHolder) holder;

        // EJECUTO EL METODO DEL VIEWHOLDER PARA CARGAR LOS DATOS
        elHolder.cargarDatos(laMedia);
    }

    @Override
    public int getItemCount() {
        return listaMedia.size();
    }

    // OVERRIDE DEL ONCLICK, QUE EJECUTA EL ONCLICK DEL LISTENER QUE TENEMOS COMO ATRIBUTO
    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    @Override
    public void finish(Object resultado) {

    }

    // HAGO UNA CLASE VIEWHOLDER PROPIA
    private class ViewHolder extends RecyclerView.ViewHolder{

        // ATRIBUTOS DEL VIEWHOLDER (VIEWS DEL LAYOUT)
        ImageView laImagen;

        // CONSTRUCTOR CON LA POSICION DE LAS VIEWS
        public ViewHolder(View itemView) {
            super(itemView);
            laImagen = (ImageView) itemView.findViewById(R.id.imageView_estetica_imagen);
        }

        // METODO PARA CARGAR DATOS
        public void cargarDatos(Media laMedia){
            String url= TMDBHelper.getImagePoster("w185", laMedia.getImagen());
            Picasso.with(context).load(url).placeholder(R.drawable.loading).error(R.drawable.generico).into(laImagen);
        }
    }

    // SETTER DEL ATRIBUTO LISTENER
    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setListaMedia(List<Media> listaMedia) {
        this.listaMedia = listaMedia;
    }
}
