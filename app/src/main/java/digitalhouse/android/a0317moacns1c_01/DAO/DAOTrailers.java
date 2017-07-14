package digitalhouse.android.a0317moacns1c_01.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.ContenedorMedia;
import digitalhouse.android.a0317moacns1c_01.Model.ContenedorTrailer;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Model.Trailer;
import digitalhouse.android.a0317moacns1c_01.Utils.HTTPConnectionManager;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;

/**
 * Created by danil on 30-Jun-17.
 */

public class DAOTrailers {



    public void traerListaMedia(Integer idPelicula, ResultListener<Trailer> listenerDelController){

        MiAsyncTask laTarea = new MiAsyncTask(listenerDelController, idPelicula);
        laTarea.execute();

    }



    private class MiAsyncTask extends AsyncTask<String, Void, Trailer> {

        private ResultListener<Trailer> listenerDelController;
        private Integer idPelicula;

        public MiAsyncTask(ResultListener<Trailer> listenerDelController, Integer idPelicula) {
            this.listenerDelController = listenerDelController;
            this.idPelicula = idPelicula;
        }

        @Override
        protected Trailer doInBackground(String... strings) {

            Trailer trailer = null;

            try {

                String urlBusqueda = TMDBHelper.getTrailerURL(idPelicula.toString(), TMDBHelper.language_ENGLISH);

                HTTPConnectionManager elManager = new HTTPConnectionManager();
                String json = elManager.getRequestString(urlBusqueda);

                Gson elGson = new Gson();

                ContenedorTrailer elContenedor = elGson.fromJson(json, ContenedorTrailer.class);
                trailer = elContenedor.getListaTrailers().get(0);


            } catch (Exception e) {
                e.printStackTrace();
            }


            return trailer;
        }


        @Override
        protected void onPostExecute(Trailer resultado) {
            super.onPostExecute(resultado);
            listenerDelController.finish(resultado);
        }
    }

}
