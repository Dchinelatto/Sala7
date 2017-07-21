package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Controller.ControllerMedia;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOTablaMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Adapters.GeneroAdapter;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragment;

public class FavoritosActivity extends AppCompatActivity implements ListaFragment.ReceptorMedia{

    // CONSTANTES
    public static final String FAV_O_RECOM = "fav_o_recom";
    public static final Integer CODIGO_FAVORITOS = 99099;

    // ATRIBUTOS
    private GeneroAdapter generoAdapter= new GeneroAdapter(this);
    private List<Media> listaFavoritos;
    private TextView textViewNoHayFavoritos;
    private RecyclerView recyclerViewFavoritos;
    private String eleccion = "";
    private ListaFragment.ReceptorMedia receptorMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        textViewNoHayFavoritos = (TextView) findViewById(R.id.textView_noHayFavoritos);
        textViewNoHayFavoritos.setVisibility(View.GONE);

        Intent laIntent = getIntent();
        eleccion = laIntent.getStringExtra(FAV_O_RECOM);

        listaFavoritos = new ArrayList<>();
        ControllerMedia controllerMedia= new ControllerMedia(this);
        controllerMedia.obtenerListaFavoritos(new ResultListener<List<Media>>() {
            @Override
            public void finish(List<Media> resultado) {
                for(Media cadaMedia : resultado){
                    listaFavoritos.add(cadaMedia);
                }


                recyclerViewFavoritos= (RecyclerView) findViewById(R.id.recyclerview_Favoritos);
                generoAdapter= new GeneroAdapter(FavoritosActivity.this);

                // SETEO EL LISTENER
                generoAdapter.setListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Integer posicion = recyclerViewFavoritos.getChildAdapterPosition(view);
                        Media laMedia = listaFavoritos.get(posicion);
                        recibirMedia(laMedia,posicion,"serie", CODIGO_FAVORITOS);
                    }

                });


                generoAdapter.setListaMedia(listaFavoritos);
                recyclerViewFavoritos.setAdapter(generoAdapter);
                generoAdapter.notifyDataSetChanged();
                recyclerViewFavoritos.setLayoutManager(new LinearLayoutManager(FavoritosActivity.this,LinearLayoutManager.VERTICAL,false));

                ocultarViewCorrecta();
            }
        });




    }

    // IMPLEMENTACION DEL METODO DE LA INTERFAZ
    @Override
    public void recibirMedia(Media unaMedia, Integer posicion, String unTipo, Integer generoID) {

        // CREO LA INTENT A LA ACTIVITY DETALLE
        Intent laIntent = new Intent(this, DetalleActivity.class);

        // CREO EL BUNDLE
        Bundle unBundle = new Bundle();

        // CARGO LOS DATOS DE LA PELICULA EN EL BUNDLE
        unBundle.putInt(DetalleActivity.POSICION, posicion);
        unBundle.putString(DetalleActivity.NOMBRE_TIPO, unTipo);
        unBundle.putInt(DetalleActivity.ID_GENERO, CODIGO_FAVORITOS);

        // CARGO EL BUNDLE EN LA INTENT
        laIntent.putExtras(unBundle);

        // INICIO LA ACTIVITY
        startActivity(laIntent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void ocultarViewCorrecta(){
        if(listaFavoritos.size() == 0){
            textViewNoHayFavoritos.setVisibility(View.VISIBLE);
            recyclerViewFavoritos.setVisibility(View.GONE);
        } else {
            textViewNoHayFavoritos.setVisibility(View.GONE);
            recyclerViewFavoritos.setVisibility(View.VISIBLE);
        }
    }

}
