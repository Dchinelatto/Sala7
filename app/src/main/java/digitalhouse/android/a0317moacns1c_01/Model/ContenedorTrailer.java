package digitalhouse.android.a0317moacns1c_01.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by danil on 30-Jun-17.
 */

public class ContenedorTrailer {

    @SerializedName("results")
    private List<Trailer> listaTrailers;

    public List<Trailer> getListaTrailers() {
        return listaTrailers;
    }

    public void setListaTrailers(List<Trailer> listaTrailers) {
        this.listaTrailers = listaTrailers;
    }
}
