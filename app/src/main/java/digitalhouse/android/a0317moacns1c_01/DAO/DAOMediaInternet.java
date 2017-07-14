package digitalhouse.android.a0317moacns1c_01.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.ContenedorMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Utils.HTTPConnectionManager;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;

/**
 * Created by Danilo on 08/06/2017.
 */

public class DAOMediaInternet {


    public void traerListaMedia(String busqueda, ResultListener<List<Media>> listenerDelController){



        MiAsyncTask laTarea = new MiAsyncTask(listenerDelController,busqueda);
        laTarea.execute();

    }



    private class MiAsyncTask extends AsyncTask<String, Void, List<Media>>{

        private ResultListener<List<Media>> listenerDelController;
        private String urlBusqueda;

        public MiAsyncTask(ResultListener<List<Media>> listenerDelController, String urlBusqueda) {
            this.listenerDelController = listenerDelController;
            this.urlBusqueda = urlBusqueda;
        }

        public void setUrlBusqueda(String urlBusqueda) {this.urlBusqueda = urlBusqueda;}

        @Override
        protected List<Media> doInBackground(String... strings) {

            List<Media> listaMedia = new ArrayList<>();

            try {

                HTTPConnectionManager elManager = new HTTPConnectionManager();
                String json = elManager.getRequestString(urlBusqueda);

                Gson elGson = new Gson();

                ContenedorMedia elContenedor = elGson.fromJson(json, ContenedorMedia.class);
                listaMedia = elContenedor.getResults();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return listaMedia;
        }


        @Override
        protected void onPostExecute(List<Media> resultado) {
            super.onPostExecute(resultado);
            listenerDelController.finish(resultado);
        }
    }

}
