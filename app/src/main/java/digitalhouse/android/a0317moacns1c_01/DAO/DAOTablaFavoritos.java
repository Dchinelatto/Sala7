package digitalhouse.android.a0317moacns1c_01.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Utils.HTTPConnectionManager;
import digitalhouse.android.a0317moacns1c_01.View.Activities.FavoritosActivity;

/**
 * Created by danil on 07-Jul-17.
 */

public class DAOTablaFavoritos extends DatabaseHelper {


    public static final String NOMBRE_TABLA = "tabla_favoritos";
    public static final String FAVORITOS_ID = "favoritos_id";
    public static final String USUARIO_ID = "usuario_id";
    public static final Integer ES_FAVORITO = 1;

    private Context context;

    public DAOTablaFavoritos(Context context) {
        super(context);
        this.context = context;
    }


    public void insertarFavorito(String usuarioId, String mediaId){

        if(!chequearUsuarioYMedia(usuarioId, mediaId)) {

            SQLiteDatabase laDB = getWritableDatabase();

            // CARGA TABLA MEDIA
            ContentValues losValores = new ContentValues();
            losValores.put(USUARIO_ID, usuarioId);
            losValores.put(DAOTablaMedia.MEDIA_ID, mediaId);

            laDB.insert(NOMBRE_TABLA, null, losValores);

            laDB.close();

        }


    }

    public void insertarVariosFavoritos(String usuarioId, List<String> listaMediaIDs){

        for (String cadaMediaID : listaMediaIDs){

            insertarFavorito(usuarioId, cadaMediaID);

        }


    }

    public void eliminarFavorito(String userId, String mediaId){

        String sql= "DELETE FROM " + NOMBRE_TABLA + " WHERE " +
                USUARIO_ID + " = '" + userId + "' AND " + DAOTablaMedia.MEDIA_ID + " = " + mediaId;

        SQLiteDatabase sqLiteDatabase= getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();

    }


    public List<Media> obtenerFavoritos(){


        List<Media>listaFavoritos= new ArrayList<>();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        String sql = "SELECT *" +
                " FROM " + DAOTablaMedia.NOMBRE_TABLA +
                " LEFT OUTER JOIN " + DAOTablaFavoritos.NOMBRE_TABLA + " ON " + DAOTablaMedia.NOMBRE_TABLA + "." + DAOTablaMedia.MEDIA_ID + " = " + DAOTablaFavoritos.NOMBRE_TABLA + "." + DAOTablaMedia.MEDIA_ID +
                " WHERE " + USUARIO_ID + " = '" + userID +
                "' ORDER BY " + DAOTablaMedia.CALIFICACION + " DESC";


        SQLiteDatabase sqLiteDatabase= getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        while (cursor.moveToNext()){


            Media media = new Media();

            media.setId(cursor.getInt(cursor.getColumnIndex(DAOTablaMedia.MEDIA_ID)));
            media.setNombre(cursor.getString(cursor.getColumnIndex(DAOTablaMedia.NOMBRE)));
            media.setDescripcion(cursor.getString(cursor.getColumnIndex(DAOTablaMedia.DESCRIPCION)));
            media.setImagen(cursor.getString(cursor.getColumnIndex(DAOTablaMedia.IMAGEN)));
            media.setVideo(cursor.getString(cursor.getColumnIndex(DAOTablaMedia.VIDEO)));
            media.setFavorito(ES_FAVORITO);
            media.setGeneroID(FavoritosActivity.CODIGO_FAVORITOS);
            media.setCalificacion(cursor.getDouble(cursor.getColumnIndex(DAOTablaMedia.CALIFICACION)));

            listaFavoritos.add(media);
        }
        cursor.close();
        sqLiteDatabase.close();
        return listaFavoritos;
    }


    public Boolean chequearUsuarioYMedia(String usuarioId, String mediaId){

        String sql= "SELECT * FROM " + NOMBRE_TABLA +
                " WHERE " + USUARIO_ID + " = '" + usuarioId + "' AND " + DAOTablaMedia.MEDIA_ID + " = " + mediaId ;

        SQLiteDatabase db= getReadableDatabase();

        Cursor cursor= db.rawQuery(sql,null);

        Boolean existeElParCombinado = cursor.moveToNext();

        cursor.close();
        db.close();
        return existeElParCombinado;
    }


}


