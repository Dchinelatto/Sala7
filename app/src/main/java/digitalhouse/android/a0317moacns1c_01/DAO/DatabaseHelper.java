package digitalhouse.android.a0317moacns1c_01.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Danilo on 13/06/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String NOMBRE_DB = "media_database";
    public static final Integer VERSION_DB = 1;

    public DatabaseHelper(Context context) {
        super(context, NOMBRE_DB, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query1 = "CREATE TABLE " + DAOTablaMedia.NOMBRE_TABLA + " (" +
                DAOTablaMedia.MEDIA_ID + " INTEGER PRIMARY KEY, " +
                DAOTablaMedia.NOMBRE + " TEXT, " +
                DAOTablaMedia.DESCRIPCION + " TEXT, " +
                DAOTablaMedia.CALIFICACION + " REAL, " +
                DAOTablaMedia.IMAGEN + " TEXT, " +
                DAOTablaMedia.FAVORITO + " TEXT, " +
                DAOTablaMedia.TIPO + " TEXT" +
                DAOTablaMedia.TRAILER + " TEXT)";

        String query2 = "CREATE TABLE " + DAOTablaGeneros.NOMBRE_TABLA + " (" +
                DAOTablaGeneros.GENERO_ID + " INTEGER PRIMARY KEY, " +
//                DAOTablaGeneros.MEDIA_ID + " INTEGER, " +
                DAOTablaGeneros.GENERO_NOMBRE + " TEXT)";

        String query3 = "CREATE TABLE " + DAOTablaCruce.NOMBRE_TABLA + " (" +
                DAOTablaCruce.CRUCE_ID + " INTEGER, " +
                DAOTablaMedia.MEDIA_ID + " INTEGER, " +
                DAOTablaGeneros.GENERO_ID + " INTEGER, " +
                "PRIMARY KEY (" + DAOTablaCruce.CRUCE_ID + "), " +
                "FOREIGN KEY (" + DAOTablaMedia.MEDIA_ID + ") REFERENCES " + DAOTablaMedia.NOMBRE_TABLA + "(" + DAOTablaMedia.MEDIA_ID + "), " +
                "FOREIGN KEY (" + DAOTablaGeneros.GENERO_ID + ") REFERENCES " + DAOTablaGeneros.NOMBRE_TABLA + "(" + DAOTablaGeneros.GENERO_ID + "));";

        String query4 = "CREATE TABLE " + DAOTablaFavoritos.NOMBRE_TABLA + " (" +
                DAOTablaFavoritos.FAVORITOS_ID + " INTEGER, " +
                DAOTablaMedia.MEDIA_ID + " INTEGER, " +
                DAOTablaFavoritos.USUARIO_ID + " INTEGER, " +
                "PRIMARY KEY (" + DAOTablaFavoritos.FAVORITOS_ID + "), " +
                "FOREIGN KEY (" + DAOTablaMedia.MEDIA_ID + ") REFERENCES " + DAOTablaMedia.NOMBRE_TABLA + "(" + DAOTablaMedia.MEDIA_ID + "));";


        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);
        sqLiteDatabase.execSQL(query3);
        sqLiteDatabase.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
