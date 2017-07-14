package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Controller.ControllerListaFragments;
import digitalhouse.android.a0317moacns1c_01.Controller.ControllerMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Adapters.DetalleViewPagerAdapter;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.DetalleFragment;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.YouTubeFragment;

public class DetalleActivity extends AppCompatActivity implements DetalleFragment.ReceptorDeYoutube{

    // CONSTANTES DEL BUNDLE
    public static final String ID_GENERO = "nombre_genero";
    public static final String NOMBRE_TIPO = "nombre_tipo";
    public static final String POSICION = "posicion";
    public static final String MEDIA_ID = "media_id";

    // ATRIBUTOS
    private List<DetalleFragment> listaFragments;
    private Integer generoID;
    private String tipoMedia;
    private Integer itemActual;

    private List<Media> listaMedia;

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);


        Toolbar toolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        // TRAIGO EL INTENT
        Intent elIntent = getIntent();

        // EXTRAIGO EL BUNDLE
        Bundle elBundle = elIntent.getExtras();

        // EXTRAIGO LOS DATOS DEL BUNDLE
        generoID = elBundle.getInt(ID_GENERO);
        tipoMedia = elBundle.getString(NOMBRE_TIPO);
        itemActual = elBundle.getInt(POSICION);

//        getActionBar().setTitle(TMDBHelper.getNombreGenero(generoID));


        cargarListaFragments();

    }


    public void cargarListaFragments() {

        listaFragments = new ArrayList<>();
        listaMedia = new ArrayList<>();


        // ACA HACEMOS UN IF PARA SEPARAR EL DETALLE DE FAVORITOS DEL DETALLE NORMAL
        if (generoID.equals(FavoritosActivity.CODIGO_FAVORITOS)) {
            ControllerMedia controllerMedia = new ControllerMedia(DetalleActivity.this);
            listaMedia = controllerMedia.obtenerListaFavoritos();
        }




        ControllerMedia controllerMedia = new ControllerMedia(this);


        controllerMedia.traerListaMediaPorGenero(generoID, tipoMedia, new ResultListener<List<Media>>() {
            @Override
            public void finish(List<Media> resultado) {

                // ACA VOLVEMOS A HACER UN IF PARA SEPARAR EL DETALLE DE FAVORITOS DEL DETALLE NORMAL
                if (!generoID.equals(FavoritosActivity.CODIGO_FAVORITOS)) {

                    listaMedia = resultado;
                }

                ControllerListaFragments elControllerListaFragment = new ControllerListaFragments();
                listaFragments = elControllerListaFragment.generarListaFragments(listaMedia, generoID, tipoMedia);

                // SETEO EL TITULO DE LA ACTIVITY
                getSupportActionBar().setTitle(TMDBHelper.getNombreGenero(generoID.toString()));

                // TRAIGO EL VIEWPAGER
                viewPager = (ViewPager) findViewById(R.id.viewPager_fragmentDetalle);

                // INSTANCIO EL ADAPTER
                DetalleViewPagerAdapter elAdapter = new DetalleViewPagerAdapter(getSupportFragmentManager(), listaFragments);

                // SETEO EL ADAPTER AL VIEWPAGER
                viewPager.setAdapter(elAdapter);

                // SETEO EL ITEM SELECCIONADO DE LA LISTA COMO CURRENT ITEM
                viewPager.setCurrentItem(itemActual);

                // AGREGO SEPARACION ENTRE CELDAS
                viewPager.setClipToPadding(false);
                viewPager.setPageMargin(2);
            }
        });

    }

    @Override
    public void recibirMediaParaYoutube(Integer id) {

        Intent intent = new Intent(this, YouTubeActivity.class);

        Bundle bundle = new Bundle();

        bundle.putInt(YouTubeActivity.MEDIA_ID, id);

        intent.putExtras(bundle);

        startActivity(intent);

    }
}


