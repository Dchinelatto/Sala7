package digitalhouse.android.a0317moacns1c_01.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragment;

/**
 * Created by Danilo on 19/05/2017.
 */

public class Media {

    public static final String TITLE = "title";
    public static final String NAME = "name";
    public static final String ID = "id";
    public static final String GENRE_IDS = "genre_ids";
    public static final String OVERVIEW = "overview";
    public static final String POSTER_PATH = "poster_path";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RUNTIME = "runtime";


    // VARIABLES DE LA PELICULA
    @SerializedName(value = TITLE, alternate = NAME)
    private String nombre;

    @SerializedName(ID)
    private Integer id;

    @SerializedName(GENRE_IDS)
    private List<Integer> generos;

    private Integer generoID;

    @SerializedName(OVERVIEW)
    private String descripcion;

    @SerializedName(POSTER_PATH)
    private String imagen;

    @SerializedName(VOTE_AVERAGE)
    private Double calificacion;

    @SerializedName(RUNTIME)
    private String duracion;

    private Integer favorito = 0;

    private String trailer;


    // GETTERS Y SETTERS
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public Integer getGeneroID() {
        return generoID;
    }

    public void setGeneroID(Integer generoID) {
        this.generoID = generoID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getFavorito() {
        return favorito;
    }

    public void setFavorito(Integer favorito) {
        this.favorito = favorito;
    }

    @Override
    public String toString() {
        return "Media{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
