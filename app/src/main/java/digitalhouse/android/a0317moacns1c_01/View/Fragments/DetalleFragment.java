package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import digitalhouse.android.a0317moacns1c_01.Controller.ControllerMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Trailer;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Activities.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleFragment extends Fragment {

    // CONSTANTES PARA TRAER LA INFORMACION DEL SAVEDINSTANCESTATE
    public static final String NOMBRE = "nombre";
    public static final String DESCRIPCION = "descripcion";
    public static final String GENERO = "genero";
    public static final String CALIFICACION = "calificacion";
    public static final String IMAGEN = "imagen";
    public static final String FAVORITO = "favorito";
    public static final String MEDIA_ID = "id";
    public static final String TIPO_MEDIA = "tipo_media";
    public static final String VIDEO = "video";
    public static final String ES_SERIE = "es_serie";

    // ATRIBUTOS DE LA MEDIA
    private String nombre;
    private String desc;
    private String genero;
    private Double calif;
    private String imagen;
    private Integer favoritos;
    private Integer mediaId;
    private String tipoMedia;
    private Boolean esSerie;

    private ReceptorDeYoutube receptorDeYoutube;
    private FirebaseUser firebaseUser;
    private ControllerMedia controllerMedia;

    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    // BOOLEAN PARA SABER SI LA MEDIA ESTA EN FAVORITOS
    private Boolean esFavorito;

    public interface ReceptorDeYoutube {
        void recibirMediaParaYoutube(Integer id);
    }


    // CREADOR DE FRAGMENTS
    public static DetalleFragment detalleFragmentCreator(Bundle elBundle) {
        DetalleFragment nuevoFragment = new DetalleFragment();
        nuevoFragment.setArguments(elBundle);
        return nuevoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // INFLO LA VIEW
        View laView = inflater.inflate(R.layout.fragment_detalle, container, false);

        progressBar = (ProgressBar) laView.findViewById(R.id.progressbar_detalle);
        progressBar.setVisibility(View.GONE);
        linearLayout = (LinearLayout) laView.findViewById(R.id.linearLayout_detalle);

        setHasOptionsMenu(true);

        // CHEQUEO SI HAY ALGO EN EL SAVEDINSTANCESTATE, SINO, CARGO LOS ATRIBUTOS POR PRIMERA VEZ
        if (savedInstanceState == null) {

            Bundle elBundle = getArguments();

            // SACO LOS DATOS DEL BUNDLE
            nombre = elBundle.getString(NOMBRE);
            genero = elBundle.getString(GENERO);
            desc = elBundle.getString(DESCRIPCION);
            calif = elBundle.getDouble(CALIFICACION);
            imagen = elBundle.getString(IMAGEN);
            favoritos = elBundle.getInt(FAVORITO);
            mediaId = elBundle.getInt(MEDIA_ID);
            tipoMedia = elBundle.getString(TIPO_MEDIA);

            if(elBundle.getString(VIDEO) == null){
                esSerie = true;
            } else {
                esSerie = false;
            }



        } else {
            nombre = savedInstanceState.getString(NOMBRE);
            desc = savedInstanceState.getString(DESCRIPCION);
            genero = savedInstanceState.getString(GENERO);
            calif = savedInstanceState.getDouble(CALIFICACION);
            imagen = savedInstanceState.getString(IMAGEN);
            favoritos = savedInstanceState.getInt(FAVORITO);
            mediaId = savedInstanceState.getInt(MEDIA_ID);
            tipoMedia = savedInstanceState.getString(TIPO_MEDIA);
            esSerie = savedInstanceState.getBoolean(ES_SERIE);

        }

        // SETEAMOS EL TITULO
        getActivity().setTitle(genero);

        // TRAIGO LAS VIEWS
        TextView textViewNombre = (TextView) laView.findViewById(R.id.textView_detalle_nombre);
        ImageView imageViewYouTube = (ImageView) laView.findViewById(R.id.imageView_detalle_youtube);
        TextView textViewDesc = (TextView) laView.findViewById(R.id.textView_detalle_descripcion);
        ImageView imageViewImagen = (ImageView) laView.findViewById(R.id.textView_detalle_imagen);
        TextView textViewCalif = (TextView) laView.findViewById(R.id.textView_detalle_calificacion);
        TextView textViewYoutube = (TextView) laView.findViewById(R.id.textView_detalle_trailer);


        // CARGO LAS VIEWS
        textViewNombre.setText(nombre);
        textViewDesc.setText(desc);
        textViewCalif.setText(String.format("%1.1f", calif));
        String imagenURL = TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W300, imagen);
        Picasso.with(getContext()).load(imagenURL).placeholder(R.drawable.generico).error(R.drawable.generico).into(imageViewImagen);

        // HAGO DESAPARECER LA OPCION DE YOUTUBE SI ES UNA SERIE
        if (tipoMedia.equals("serie") || esSerie.equals(true)) {
            imageViewYouTube.setVisibility(View.GONE);
            textViewYoutube.setVisibility(View.GONE);
        }


        // SETEO UN LISTENER A LA IMAGEN DE YOUTUBE PARA QUE AL HACER CLICK AVISE A LA ACTIVITY
        imageViewYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receptorDeYoutube.recibirMediaParaYoutube(mediaId);
            }
        });


        return laView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_detalle, menu);
        super.onCreateOptionsMenu(menu, inflater);

        ControllerMedia controllerMedia = new ControllerMedia(getContext());

        // SI HAY USUARIO Y LA MEDIA ESTA EN FAVORITOS, LA MARCAMOS CON CORAZON RELLENO
        if (firebaseUser != null) {

            if (esFavorito) {

                menu.findItem(R.id.estrellaFavoritos).setIcon(R.drawable.ic_favorite_black_24dp);

            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {


        if (item.getItemId() == R.id.estrellaFavoritos) {

            if (firebaseUser == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            } else {
                final String userId = firebaseUser.getUid();

                if (!esFavorito) {


                    controllerMedia.insertarFavorito(userId, mediaId.toString());
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                    esFavorito = true;


                    Snackbar.make(getView(), "Agregado a favoritos", Snackbar.LENGTH_LONG)
                            .setAction("Deshacer", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    esFavorito = false;
                                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                                    controllerMedia.eliminarFavorito(userId, mediaId.toString());
                                }
                            }).show();

                } else if (esFavorito) {

                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                    controllerMedia.eliminarFavorito(userId, mediaId.toString());
                    esFavorito = false;


                }
            }

        } else if (item.getItemId() == R.id.botonCompartir) {

            mostrarProgressBar();

            // HAGO DESAPARECER LA OPCION DE YOUTUBE SI ES UNA SERIE
            if (tipoMedia.equals("serie")) {

                String imagenURL = TMDBHelper.getImagePoster(TMDBHelper.IMAGE_SIZE_W300, imagen);

                //Creamos un share de tipo ACTION_SENT
                Intent share = new Intent(android.content.Intent.ACTION_SEND);

                //Indicamos que voy a compartir texto
                share.setType("text/plain");

                //Le agrego un título
                share.putExtra(Intent.EXTRA_SUBJECT, "Poster Serie");
                //Le agrego el texto a compartir (Puede ser un link tambien)

                share.putExtra(Intent.EXTRA_TEXT, imagenURL);

                //Hacemos un start para que comparta el contenido.
                startActivity(Intent.createChooser(share, "Cómo lo compartimos?"));

                ocultarProgressBar();

            } else {

                ControllerMedia controllerMedia = new ControllerMedia(getContext());
                controllerMedia.traerTrailer(mediaId, new ResultListener<Trailer>() {
                    @Override
                    public void finish(Trailer resultado) {

                        String trailerKey = resultado.getTrailer();

                        //Creamos un share de tipo ACTION_SENT
                        Intent share = new Intent(android.content.Intent.ACTION_SEND);

                        //Indicamos que voy a compartir texto
                        share.setType("text/plain");

                        //Le agrego un título
                        share.putExtra(Intent.EXTRA_SUBJECT, "Youtube Video");
                        //Le agrego el texto a compartir (Puede ser un link tambien)

                        share.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + trailerKey);

                        //Hacemos un start para que comparta el contenido.
                        startActivity(Intent.createChooser(share, "Cómo lo compartimos?"));

                        ocultarProgressBar();
                    }
                });

            }




        }



        return super.onOptionsItemSelected(item);

    }


    // OVERRIDE DEL ONSAVEINSTANCESTATE POR SI SE GIRA EL TELEFONO
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(NOMBRE, nombre);
        outState.putString(DESCRIPCION, desc);
        outState.putString(GENERO, genero);
        outState.putDouble(CALIFICACION, calif);
        outState.putString(IMAGEN, imagen);
        outState.putInt(FAVORITO, favoritos);
        outState.putInt(MEDIA_ID, mediaId);
        outState.putString(TIPO_MEDIA, tipoMedia);
        outState.putBoolean(ES_SERIE, esSerie);
    }


    @Override
    public void onStart() {
        super.onStart();
        // ACTUALIZO EL BOOLEAN DE FAVORITOS
        controllerMedia = new ControllerMedia(getContext());
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            esFavorito = controllerMedia.chequearUsuarioYMedia(firebaseUser.getUid(), mediaId.toString());
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        receptorDeYoutube = (ReceptorDeYoutube) context;
    }

    public void mostrarProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
    }
    public void ocultarProgressBar(){
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }
}
