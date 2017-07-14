package digitalhouse.android.a0317moacns1c_01.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Model.Media;

import static digitalhouse.android.a0317moacns1c_01.DAO.DAOTablaMedia.FAVORITO;

/**
 * Created by Danilo on 15/06/2017.
 */

public class DAOTablaGeneros extends DatabaseHelper{


    public static final String NOMBRE_TABLA = "tabla_generos";
    public static final String GENERO_ID = "genero_id";
    public static final String GENERO_NOMBRE = "genero_nombre";

    public DAOTablaGeneros(Context context) {
        super(context);
    }

    // METODO PARA CARGAR UNA MEDIA A FAVORITOS
    public void cargarGenero(Genero unGenero){

        if(!chequearSiExiste(unGenero.getId())) {

            ContentValues losValores = new ContentValues();
            losValores.put(GENERO_ID, unGenero.getId());
            losValores.put(GENERO_NOMBRE, unGenero.getName());

            SQLiteDatabase laDB = getWritableDatabase();
            laDB.insert(NOMBRE_TABLA, null, losValores);
            laDB.close();
        }

    }

    // METODO PARA CARGAR UNA LISTA DE MEDIA A FAVORITOS
    public void cargarListaGeneros(List<Genero> unaListaGeneros){

        for (Genero cadaGenero : unaListaGeneros){
            cargarGenero(cadaGenero);
        }

    }

    // METODO PARA TRAER LA LISTA DE GENEROS
    public List<Genero> traerListaGeneros(){

        List<Genero> listaGeneros = new ArrayList<>();

        SQLiteDatabase laDB = getReadableDatabase();
        String query = "SELECT * FROM " + NOMBRE_TABLA + " ORDER BY " + GENERO_NOMBRE + " ASC";

        Cursor elCursor = laDB.rawQuery(query, null);

        while (elCursor.moveToNext()){

            Genero elGenero = new Genero();

            elGenero.setId(elCursor.getInt(elCursor.getColumnIndex(GENERO_ID)));
            elGenero.setName(elCursor.getString(elCursor.getColumnIndex(GENERO_NOMBRE)));

            listaGeneros.add(elGenero);
        }

        elCursor.close();
        laDB.close();

        return listaGeneros;
    }

    public Genero obtenerGeneroPorId (Integer id){

        String sql= "SELECT * FROM " + NOMBRE_TABLA + " WHERE " + GENERO_ID + " = "+ id;
        SQLiteDatabase db= getReadableDatabase();
        Cursor cursor= db.rawQuery(sql,null);
        Genero genero= null;
        while(cursor.moveToNext()){

            genero= new Genero();
            genero.setId(cursor.getInt(cursor.getColumnIndex(GENERO_ID)));
            genero.setName(cursor.getString(cursor.getColumnIndex(GENERO_NOMBRE)));


        }

        cursor.close();
        db.close();
        return genero;

    }

    public boolean chequearSiExiste(Integer id){


        Genero genero= obtenerGeneroPorId(id);
        return genero != null;

    }

}
