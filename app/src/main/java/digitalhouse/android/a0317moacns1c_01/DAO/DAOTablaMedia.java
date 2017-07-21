package digitalhouse.android.a0317moacns1c_01.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.View.Activities.FavoritosActivity;

/**
 * Created by Danilo on 14/06/2017.
 */

public class DAOTablaMedia extends DatabaseHelper{


    public static final String NOMBRE_TABLA = "tabla_media";
    public static final String MEDIA_ID = "media_id";
    public static final String NOMBRE = "media_nombre";
    public static final String DESCRIPCION = "descripcion";
    public static final String CALIFICACION = "calificacion";
    public static final String IMAGEN = "imagen";
    public static final String TIPO = "serie_o_pelicula";
    public static final String VIDEO = "video";
    public static final String FAVORITO = "favorito";
    public static final String TRAILER = "trailer";

    public DAOTablaMedia(Context context) {
        super(context);
    }

    // METODO PARA CARGAR UNA MEDIA A FAVORITOS
    public void cargarMedia(Media unaMedia, String unGeneroID, String unTipo){

        if(!chequearSiExisteEnTablaMedia(unaMedia.getId())) {

            SQLiteDatabase laDB = getWritableDatabase();

            // CARGA TABLA MEDIA
            ContentValues losValores = new ContentValues();
            losValores.put(MEDIA_ID, unaMedia.getId());
            losValores.put(NOMBRE, unaMedia.getNombre());
            losValores.put(DESCRIPCION, unaMedia.getDescripcion());
            losValores.put(IMAGEN, unaMedia.getImagen());
            losValores.put(CALIFICACION, unaMedia.getCalificacion());

            losValores.put(TIPO, unTipo);
            losValores.put(VIDEO, unaMedia.getVideo());

            laDB.insert(NOMBRE_TABLA, null, losValores);

            laDB.close();

        }

        if(!chequearSiExisteEnTablaCruce(unaMedia.getId(), unGeneroID)) {

            SQLiteDatabase laDB = getWritableDatabase();

            // CARGA TABLA CRUCE
            ContentValues otrosValores = new ContentValues();
            otrosValores.put(MEDIA_ID, unaMedia.getId());
            otrosValores.put(DAOTablaGeneros.GENERO_ID, unGeneroID);

            laDB.insert(DAOTablaCruce.NOMBRE_TABLA, null, otrosValores);

            laDB.close();

        }



    }

    // METODO PARA CARGAR UNA LISTA DE MEDIA A LA TABLA
    public void cargarListaMedia(List<Media> unaListaMedia, String unGeneroID, String unTipo){

        for (Media cadaMedia : unaListaMedia){
            cargarMedia(cadaMedia, unGeneroID, unTipo);
        }

    }

    // METODO PARA TRAER LA LISTA DE MEDIA
    public List<Media> traerListaMedia(){

        List<Media> listaMedia = new ArrayList<>();

        SQLiteDatabase laDB = getReadableDatabase();
        String query = "SELECT * FROM " + NOMBRE_TABLA;

        Cursor elCursor = laDB.rawQuery(query, null);

        while (elCursor.moveToNext()){

            Media laMedia = new Media();

            laMedia.setId(elCursor.getInt(elCursor.getColumnIndex(MEDIA_ID)));
            laMedia.setNombre(elCursor.getString(elCursor.getColumnIndex(NOMBRE)));
            laMedia.setDescripcion(elCursor.getString(elCursor.getColumnIndex(DESCRIPCION)));
            laMedia.setImagen(elCursor.getString(elCursor.getColumnIndex(IMAGEN)));
            laMedia.setFavorito(elCursor.getInt(elCursor.getColumnIndex(FAVORITO)));
            laMedia.setVideo(elCursor.getString(elCursor.getColumnIndex(VIDEO)));

            laMedia.setCalificacion(elCursor.getDouble(elCursor.getColumnIndex(CALIFICACION)));

            listaMedia.add(laMedia);
        }

        elCursor.close();
        laDB.close();

        return listaMedia;
    }


    // METODO PARA TRAER LA LISTA DE MEDIA
    public List<Media> traerListaMediaPorGenero(Integer unGeneroID, String unTipo){

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String usuario = "";

        if(firebaseUser != null){
            usuario = firebaseUser.getUid();
        }

        List<Media> listaMedia = new ArrayList<>();

        SQLiteDatabase laDB = getReadableDatabase();

        String query = "SELECT " + DAOTablaMedia.NOMBRE_TABLA + "." + DAOTablaMedia.MEDIA_ID + ", " + DAOTablaMedia.NOMBRE_TABLA + "." + DAOTablaMedia.VIDEO + ", " + DAOTablaMedia.NOMBRE + ", " + DAOTablaMedia.DESCRIPCION + ", " + DAOTablaMedia.IMAGEN + ", " + DAOTablaFavoritos.USUARIO_ID + ", " + DAOTablaMedia.CALIFICACION +
                " FROM " + DAOTablaMedia.NOMBRE_TABLA +
                " LEFT OUTER JOIN " + DAOTablaCruce.NOMBRE_TABLA + " ON " + DAOTablaMedia.NOMBRE_TABLA + "." + DAOTablaMedia.MEDIA_ID + " = " + DAOTablaCruce.NOMBRE_TABLA + "." + DAOTablaMedia.MEDIA_ID +
                " LEFT OUTER JOIN " + DAOTablaGeneros.NOMBRE_TABLA + " ON " + DAOTablaCruce.NOMBRE_TABLA + "." + DAOTablaGeneros.GENERO_ID + " = " + DAOTablaGeneros.NOMBRE_TABLA + "." + DAOTablaGeneros.GENERO_ID +
                " LEFT OUTER JOIN " + DAOTablaFavoritos.NOMBRE_TABLA + " ON " + DAOTablaMedia.NOMBRE_TABLA + "." + DAOTablaMedia.MEDIA_ID + " = " + DAOTablaFavoritos.NOMBRE_TABLA + "." + DAOTablaMedia.MEDIA_ID +
                " WHERE " + DAOTablaGeneros.NOMBRE_TABLA + "." + DAOTablaGeneros.GENERO_ID + " = " + unGeneroID + " AND " + TIPO + " = \"" + unTipo + "\"" +
                " AND (" + DAOTablaFavoritos.USUARIO_ID + " = '" + usuario + "' OR " + DAOTablaFavoritos.USUARIO_ID + " is null)" +
                " ORDER BY " + CALIFICACION + " DESC";


        Cursor elCursor = laDB.rawQuery(query, null);

        while (elCursor.moveToNext()){

            Media media = new Media();

            media.setId(elCursor.getInt(elCursor.getColumnIndex(MEDIA_ID)));
            media.setNombre(elCursor.getString(elCursor.getColumnIndex(NOMBRE)));
            media.setDescripcion(elCursor.getString(elCursor.getColumnIndex(DESCRIPCION)));
            media.setImagen(elCursor.getString(elCursor.getColumnIndex(IMAGEN)));
            media.setVideo(elCursor.getString(elCursor.getColumnIndex(VIDEO)));
            media.setFavorito(elCursor.getInt(elCursor.getColumnIndex(DAOTablaFavoritos.USUARIO_ID)));

            media.setCalificacion(elCursor.getDouble(elCursor.getColumnIndex(CALIFICACION)));

            listaMedia.add(media);
        }

        elCursor.close();
        laDB.close();

        return listaMedia;
    }

    // METODO PARA BORRAR UNA MEDIA DE LA TABLA
    public void borrarMedia(Media unaMedia){

        String sql = "DELETE FROM " + NOMBRE_TABLA + " WHERE " + MEDIA_ID + " = " + unaMedia.getId();
        SQLiteDatabase laDB = getWritableDatabase();

        laDB.execSQL(sql);

        laDB.close();

    }

    public Media obtenerMediaPorId(Integer id){

        String sql= "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + MEDIA_ID + " = " + id;

        SQLiteDatabase db= getReadableDatabase();

        Cursor cursor= db.rawQuery(sql,null);

        Media media= null;

        while(cursor.moveToNext()) {

             media = new Media();

            media.setId(cursor.getInt(cursor.getColumnIndex(MEDIA_ID)));
            media.setNombre(cursor.getString(cursor.getColumnIndex(NOMBRE)));
            media.setDescripcion(cursor.getString(cursor.getColumnIndex(DESCRIPCION)));
            media.setImagen(cursor.getString(cursor.getColumnIndex(IMAGEN)));
            media.setFavorito(cursor.getInt(cursor.getColumnIndex(FAVORITO)));
            media.setVideo(cursor.getString(cursor.getColumnIndex(VIDEO)));

            media.setCalificacion(cursor.getDouble(cursor.getColumnIndex(CALIFICACION)));

        }

        cursor.close();
        db.close();
        return media;
    }


    public Media obtenerMediaPorIdYGenero(Integer id, String generoId){

        String query = "SELECT *" +
                " FROM " + DAOTablaMedia.NOMBRE_TABLA +
                " JOIN " + DAOTablaCruce.NOMBRE_TABLA + " using (" + DAOTablaMedia.MEDIA_ID + ")" +
                " JOIN " + DAOTablaGeneros.NOMBRE_TABLA + " using (" + DAOTablaGeneros.GENERO_ID + ")" +
                " WHERE " + DAOTablaGeneros.GENERO_ID + " = " + generoId + " AND " + DAOTablaMedia.MEDIA_ID + " = " + id +
                " ORDER BY " + CALIFICACION + " DESC";

        SQLiteDatabase db= getReadableDatabase();

        Cursor cursor= db.rawQuery(query,null);

        Media media= null;

        while(cursor.moveToNext()) {

            media = new Media();

        }

        cursor.close();
        db.close();
        return media;
    }





    public boolean chequearSiExisteEnTablaMedia(Integer id){

        Media media= obtenerMediaPorId(id);
        return media != null;

    }

    public boolean chequearSiExisteEnTablaCruce(Integer id, String generoId){

        Media media = obtenerMediaPorIdYGenero(id, generoId);
        return media != null;

    }

}
