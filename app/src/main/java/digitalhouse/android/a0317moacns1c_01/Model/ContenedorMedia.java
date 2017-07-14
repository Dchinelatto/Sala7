package digitalhouse.android.a0317moacns1c_01.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Danilo on 08/06/2017.
 */

public class ContenedorMedia {

    public static final String RESULTS = "results";


    @SerializedName(RESULTS)
    private List<Media> results;

    public List<Media> getResults() {
        return results;
    }
}
