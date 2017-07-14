package digitalhouse.android.a0317moacns1c_01.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danilo on 26/05/2017.
 */

public class Genero {

    public static final String ID = "id";
    public static final String NAME = "name";


    // ATRIBUTOS DE LA CLASE CATEGORIA
    @SerializedName(ID)
    private Integer id;

    @SerializedName(NAME)
    private String name;


    // GETTERS Y SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
