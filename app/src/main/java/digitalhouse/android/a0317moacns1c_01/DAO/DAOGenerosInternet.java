package digitalhouse.android.a0317moacns1c_01.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.ContenedorGenero;
import digitalhouse.android.a0317moacns1c_01.Model.ContenedorMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Utils.HTTPConnectionManager;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;

/**
 * Created by dh2 on 08/06/17.
 */

public class DAOGenerosInternet {

    public void traerListaGenero(String busqueda, ResultListener<List<Genero>> listenerDelController){

        MiAsyncTaskGenero  laTarea = new MiAsyncTaskGenero();
        laTarea.setListenerDelController(listenerDelController);
        laTarea.setUrlBusqueda(busqueda);
        laTarea.execute();

    }



    private class MiAsyncTaskGenero extends AsyncTask<String, Void, List<Genero>> {

        private ResultListener<List<Genero>> listenerDelController;
        private String urlBusqueda;

        public void setListenerDelController(ResultListener<List<Genero>> listenerDelController) {
            this.listenerDelController = listenerDelController;
        }

        public void setUrlBusqueda(String urlBusqueda) {this.urlBusqueda = urlBusqueda;}

        @Override
        protected List<Genero> doInBackground(String... strings) {

            List<Genero> listaGeneros = new ArrayList<>();

            try {

                HTTPConnectionManager elManager = new HTTPConnectionManager();
                String json = elManager.getRequestString(urlBusqueda);

                Gson elGson = new Gson();

                ContenedorGenero elContenedor = elGson.fromJson(json, ContenedorGenero.class);
                listaGeneros = elContenedor.getGenres();


            } catch (Exception e) {
                e.printStackTrace();
            }


            return listaGeneros;
        }


        @Override
        protected void onPostExecute(List<Genero> resultado) {
            super.onPostExecute(resultado);
            listenerDelController.finish(resultado);
        }
    }




}
