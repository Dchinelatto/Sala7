package digitalhouse.android.a0317moacns1c_01.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dh2 on 08/06/17.
 */

public class ContenedorGenero {


    public static final String GENRES = "genres";

    @SerializedName(GENRES)
    private List<Genero> genres;


    public List<Genero> getGenres() {
        return genres;
    }
}
