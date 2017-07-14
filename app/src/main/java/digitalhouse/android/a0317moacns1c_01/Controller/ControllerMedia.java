package digitalhouse.android.a0317moacns1c_01.Controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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


                    elDAOGeneros.cargarListaGeneros(resultado);

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
                            List<Media> listaMedia = elDAO.traerListaMediaPorGenero(genero, tipoMedia);
                            listenerDeLaView.finish(listaMedia);
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

//    public void traerListaMediaInternet(String busqueda, final ResultListener<List<Media>> listenerDeLaView){
//
//        DAOMediaInternet elDAO = new DAOMediaInternet();
//        elDAO.traerListaMedia(busqueda, new ResultListener<List<Media>>() {
//            @Override
//            public void finish(List<Media> resultado) {
//                listenerDeLaView.finish(resultado);
//            }
//        });
//
//
//    }


    // METODO PARA TRAER LA LISTA DE FAVORITOS
    public List<Media> traerListaMedia() {

        List<Media> listaMedia;

        DAOTablaMedia elDAO = new DAOTablaMedia(elContexto);
        listaMedia = elDAO.traerListaMedia();

        return listaMedia;
    }


    // METODO PARA BORRAR UNA MEDIA DE FAVORITOS
    public void borrarMedia(Media unaMedia) {

        DAOTablaMedia elDAO = new DAOTablaMedia(elContexto);
        elDAO.borrarMedia(unaMedia);

    }

    public void insertarFavorito(String userId, String mediaId) {

        DAOTablaFavoritos daoTablaFavoritos = new DAOTablaFavoritos(elContexto);
        daoTablaFavoritos.insertarFavorito(elContexto, userId, mediaId);

    }

    public void eliminarFavorito(String userId, String mediaId) {

        DAOTablaFavoritos daoTablaFavoritos = new DAOTablaFavoritos(elContexto);
        daoTablaFavoritos.eliminarFavorito(userId, mediaId);

    }


    public List<Media> obtenerListaFavoritos() {

        DAOTablaFavoritos daoTablaFavoritos = new DAOTablaFavoritos(elContexto);
        return daoTablaFavoritos.obtenerFavoritos();


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

}



