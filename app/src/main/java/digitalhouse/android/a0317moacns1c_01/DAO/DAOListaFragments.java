package digitalhouse.android.a0317moacns1c_01.DAO;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Activities.FavoritosActivity;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.DetalleFragment;

/**
 * Created by Danilo on 07/06/2017.
 */

public class DAOListaFragments {

    // ATRIBUTOS
    private Integer currentItem;


    // METODO PROPIO PARA CREAR LA LISTA DE FRAGMENTS DETALLE
    public List<DetalleFragment> generarListaFragments(List<Media> listaMedia, Integer generoID, String tipoMedia) {

        List<DetalleFragment> listaFragments = new ArrayList<>();

        for (Media cadaMedia : listaMedia) {
            Bundle elBundle = new Bundle();

            String nombreGenero;

            if(generoID == FavoritosActivity.CODIGO_FAVORITOS){
                nombreGenero = FavoritosActivity.CODIGO_FAVORITOS.toString();
            } else {
                nombreGenero = TMDBHelper.getNombreGenero(generoID.toString());
            }


            elBundle.putString(DetalleFragment.NOMBRE, cadaMedia.getNombre());
            elBundle.putString(DetalleFragment.GENERO, nombreGenero);
            elBundle.putString(DetalleFragment.DESCRIPCION, cadaMedia.getDescripcion());
            elBundle.putDouble(DetalleFragment.CALIFICACION, cadaMedia.getCalificacion());
            elBundle.putString(DetalleFragment.IMAGEN, cadaMedia.getImagen());
            elBundle.putInt(DetalleFragment.FAVORITO, cadaMedia.getFavorito());
            elBundle.putInt(DetalleFragment.MEDIA_ID,cadaMedia.getId());
            elBundle.putString(DetalleFragment.TIPO_MEDIA,tipoMedia);
            elBundle.putString(DetalleFragment.VIDEO,cadaMedia.getVideo());


            listaFragments.add(DetalleFragment.detalleFragmentCreator(elBundle));
        }

        return listaFragments;
    }



}
