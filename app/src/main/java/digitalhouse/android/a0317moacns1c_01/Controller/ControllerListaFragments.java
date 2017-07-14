package digitalhouse.android.a0317moacns1c_01.Controller;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.DAO.DAOListaFragments;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.DetalleFragment;

/**
 * Created by Danilo on 07/06/2017.
 */

public class ControllerListaFragments {


    public List<DetalleFragment> generarListaFragments(List<Media> listaMedia, Integer generoID) {


        DAOListaFragments elDAOListaFrag = new DAOListaFragments();

        List<DetalleFragment> listaFragments = elDAOListaFrag.generarListaFragments(listaMedia, generoID);

        return listaFragments;
    }




}
