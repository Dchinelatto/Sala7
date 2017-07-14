package digitalhouse.android.a0317moacns1c_01.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Genero;

/**
 * Created by danil on 27-Jun-17.
 */

public class DAOTablaCruce extends DatabaseHelper {


    public static final String NOMBRE_TABLA = "tabla_cruce";
    public static final String CRUCE_ID = "cruce_id";

    public DAOTablaCruce(Context context) {
        super(context);
    }


}
