package digitalhouse.android.a0317moacns1c_01.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.DAO.DAOFirebase;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOMediaInternet;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOTablaFavoritos;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOTablaGeneros;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOTablaMedia;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOGenerosInternet;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOTrailers;
import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Model.Trailer;
import digitalhouse.android.a0317moacns1c_01.Utils.HTTPConnectionManager;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Activities.FavoritosActivity;

/**
 * Created by dh2 on 08/06/17.
 */

public class ControllerMedia {

    // ATRIBUTO DEL CONTROLLER
    Context elContexto;
    private Integer page = 1;

    private boolean endPaging = false;

    // CONSTRUCTOR CON CONTEXTO
    public ControllerMedia(Context elContexto) {
        this.elContexto = elContexto;
    }


    public void traerListaGenero(final ResultListener<List<Genero>> listenerDeLaView) {

        final DAOTablaGeneros elDAOGeneros = new DAOTablaGeneros(elContexto);

        if (HTTPConnectionManager.isNetworkingOnline(elContexto)) {

            String json = TMDBHelper.getAllGenres(TMDBHelper.language_SPANISH);

            DAOGenerosInternet elDAO = new DAOGenerosInternet();
            elDAO.traerListaGenero(json, new ResultListener<List<Genero>>() {
                @Override
                public void finish(List<Genero> resultado) {

                    List<Genero> listaGeneros = new ArrayList<Genero>();
                    for(Genero cadaGenero : resultado){
                        if(cadaGenero.getName().equals("Western")||cadaGenero.getName().equals("Guerra")||cadaGenero.getName().equals("película de la televisión")||cadaGenero.getName().equals("Suspense")||cadaGenero.getName().equals("Historia")||cadaGenero.getName().equals("Documental")||cadaGenero.getName().equals("Crimen")||cadaGenero.getName().equals("Familia")||cadaGenero.getName().equals("Aventura")){
                        } else {
                            listaGeneros.add(cadaGenero);

                        }
                    }

                    elDAOGeneros.cargarListaGeneros(listaGeneros);

                    listenerDeLaView.finish(elDAOGeneros.traerListaGeneros());
                }
            });

        } else {

            listenerDeLaView.finish(elDAOGeneros.traerListaGeneros());

        }

    }


    public void traerListaMediaPorGenero(final Integer genero, final String tipoMedia, final ResultListener<List<Media>> listenerDeLaView) {

        // ACA VOLVEMOS A HACER UN IF PARA SEPARAR EL DETALLE DE FAVORITOS DEL DETALLE NORMAL
        if (!genero.equals(FavoritosActivity.CODIGO_FAVORITOS)) {


            if (HTTPConnectionManager.isNetworkingOnline(elContexto)) {

                String json = "";

                if (tipoMedia.equals(TMDBHelper.TIPO_PELICULA)) {
                    json = TMDBHelper.getMoviesByGenre(genero.toString(), page, TMDBHelper.language_SPANISH);

                } else if (tipoMedia.equals(TMDBHelper.TIPO_SERIE)) {

                    json = TMDBHelper.getTVByGenre(genero.toString(), page, TMDBHelper.language_SPANISH);

                } else {
                    Log.e("Tipo media:", " Incorrecto! Debe ser serie o pelicula");
                }

                DAOMediaInternet elDAO = new DAOMediaInternet();
                elDAO.traerListaMedia(json, new ResultListener<List<Media>>() {
                    @Override
                    public void finish(List<Media> resultado) {

                        if (resultado.isEmpty() || resultado == null) {
                            endPaging = true;
                        } else {

                            DAOTablaMedia elDAO = new DAOTablaMedia(elContexto);
                            elDAO.cargarListaMedia(resultado, genero.toString(), tipoMedia);
//                            List<Media> listaMedia = elDAO.traerListaMediaPorGenero(genero, tipoMedia);
                            listenerDeLaView.finish(resultado);
                            page = page + 1;
                        }
                    }
                });


            } else {

                DAOTablaMedia elDAO = new DAOTablaMedia(elContexto);
                List<Media> listaMedia = elDAO.traerListaMediaPorGenero(genero, tipoMedia);

                listenerDeLaView.finish(listaMedia);
            }

        } else {
            listenerDeLaView.finish(null);
        }


    }

    public boolean isAnyPageAvailable() {
        return !endPaging;
    }



    public void cargarMedia(Media media, String genero, String tipo){

        DAOTablaMedia daoTablaMedia= new DAOTablaMedia(elContexto);
        daoTablaMedia.cargarMedia(media,genero,tipo);

    }


    // METODO PARA BORRAR UNA MEDIA DE FAVORITOS
    public void borrarMedia(Media unaMedia) {

        DAOTablaMedia elDAO = new DAOTablaMedia(elContexto);
        elDAO.borrarMedia(unaMedia);

    }

    public void insertarFavorito(String userId, String mediaId) {

        DAOTablaFavoritos daoTablaFavoritos = new DAOTablaFavoritos(elContexto);
        daoTablaFavoritos.insertarFavorito(userId, mediaId);

        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.insertarFavoritoFirebase(userId, mediaId);

    }

    public void eliminarFavorito(String userId, String mediaId) {

        DAOTablaFavoritos daoTablaFavoritos = new DAOTablaFavoritos(elContexto);
        daoTablaFavoritos.eliminarFavorito(userId, mediaId);

        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.eliminarFavoritoFirebase(userId, mediaId);
    }


    public void obtenerListaFavoritos(final ResultListener<List<Media>> listenerDeLaView) {

        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final DAOTablaFavoritos daoTablaFavoritos = new DAOTablaFavoritos(elContexto);

        if(HTTPConnectionManager.isNetworkingOnline(elContexto)){

            DAOFirebase daoFirebase = new DAOFirebase();
            daoFirebase.obtenerFavoritosFirebase(userId, new ResultListener<List<String>>() {
                @Override
                public void finish(List<String> resultado) {


                    daoTablaFavoritos.insertarVariosFavoritos(userId, resultado);

                    listenerDeLaView.finish(daoTablaFavoritos.obtenerFavoritos());;
                }
            });

        } else {

            listenerDeLaView.finish(daoTablaFavoritos.obtenerFavoritos());


        }

    }


    public void traerTrailer(Integer mediaId, final ResultListener<Trailer> listenerDelController) {

        DAOTrailers daoTrailers = new DAOTrailers();

        daoTrailers.traerListaMedia(mediaId, new ResultListener<Trailer>() {
            @Override
            public void finish(Trailer resultado) {
                listenerDelController.finish(resultado);
            }
        });

    }

    public Boolean chequearUsuarioYMedia(String usuarioId, String mediaId){

        DAOTablaFavoritos daoTablaFavoritos = new DAOTablaFavoritos(elContexto);

        return daoTablaFavoritos.chequearUsuarioYMedia(usuarioId, mediaId);

    }

    public void traerBuscador(String busqueda, final ResultListener <List<Media>> listenerDeView) {

        String json = TMDBHelper.getMultiSearch(TMDBHelper.language_SPANISH, busqueda);
        if (HTTPConnectionManager.isNetworkingOnline(elContexto)) {
            DAOMediaInternet daoMediaInternet = new DAOMediaInternet();
            daoMediaInternet.traerListaMedia(json, new ResultListener<List<Media>>() {
                @Override
                public void finish(List<Media> resultado) {
                    listenerDeView.finish(resultado);
                }
            });
        } else {

            Toast.makeText(elContexto, "No hay conexion a internet", Toast.LENGTH_SHORT).show();

        }
    }

    public void bajarFotoTwitterYSubirFotoAFirebase(String usuarioID){


        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.bajarFotoTwitterYSubirFotoAFirebase(usuarioID);

    }




    public void obtenerFotoFirebase(String usuarioId, final ResultListener<String> listenerDeLaView) {


        DAOFirebase daoFirebase = new DAOFirebase();
        daoFirebase.obtenerFotoFirebase(usuarioId, new ResultListener<String>() {
            @Override
            public void finish(String resultado) {
                listenerDeLaView.finish(resultado);
            }
        });
    }


}



