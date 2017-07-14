package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.content.Intent;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.DAO.DAOTablaGeneros;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.GeneroFragment;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragment;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragmentSeries;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.LoginFragment;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ViewPagerFragment;

public class MainActivity extends AppCompatActivity implements ListaFragment.ReceptorMedia {

    // ATRIBUTOS DEL MAIN ACTIVITY
    private ListaFragmentSeries elFragment;
    private FragmentTransaction fragmentTransaction;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private MenuItem menuItem;
    private ViewPagerFragment viewPagerFragment;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseuser;

    private TextView textViewHeaderUsuario;

    // LISTAS DE PELICULAS
    private List<Genero> listaGeneros;

    public static Boolean logueado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseuser = firebaseAuth.getCurrentUser();

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);

        // TRAIGO EL DRAWER Y LA NAVIGATION
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout_main);
        navigationView = (NavigationView) findViewById(R.id.navigationView);

        // TRAIGO EL HEADER DE LA NAVIGATION
        View view = navigationView.getHeaderView(0);
        textViewHeaderUsuario = (TextView) view.findViewById(R.id.textView_headerUsuario);

        // SETEO EL BOTON HAMBURGUESA
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //ITEM DEL MENU DEL LOGIN DE USUARIO
        menuItem = navigationView.getMenu().getItem(0);

        // LE SETEO UN LISTENER A LA NAVIGATION
        navigationView.setNavigationItemSelectedListener(new MenuListener());

        // CREO EL FRAGMENT
        viewPagerFragment= new ViewPagerFragment();

        // CREO LA TRANSACCION A PARTIR DEL MANAGER
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // CARGO EL FRAGMENT Y EL LAYOUT EN LA TRANSACCION
        fragmentTransaction.replace(R.id.frameLayout_fragmentList, viewPagerFragment);

        // CIERRO LA TRANSACCION CON COMMIT
        fragmentTransaction.commit();


        setearMenuUsuario();

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
        unBundle.putInt(DetalleActivity.ID_GENERO, generoID);
        unBundle.putInt(DetalleActivity.MEDIA_ID, unaMedia.getId());


        // CARGO EL BUNDLE EN LA INTENT
        laIntent.putExtras(unBundle);

        // INICIO LA ACTIVITY
        startActivity(laIntent);
    }


   // METODO PROPIO QUE DEFINE EL COMPORTAMIENTO ANTE UN CLICK DEL MENU
    private void navigate(MenuItem item){
        Intent laIntent;
        GeneroFragment elFragment= new GeneroFragment();
        Bundle elBundle = new Bundle();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){
            case (R.id.menu_suspenso):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_SCIENCE_THRILLER));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_accion):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_ACTION));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_drama):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_DRAMA));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_comedia):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_COMEDY));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_romance):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_ROMANCE));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_cienciaFiccion):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_SCIENCE_FICTION));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_login):
                checkUser();
                break;

            case (R.id.menu_favoritos):
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                break;
            case (R.id.botonHome):
                fragmentTransaction.replace(R.id.frameLayout_fragmentList,viewPagerFragment);

                break;

        }

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);


        fragmentTransaction.commit();



        // CIERRO EL DRAWER
        drawerLayout.closeDrawers();

    }


    // CLASE PRIVADA QUE IMPLEMENTA UN COMPORTAMIENTO PROPIO PARA EL CLICK EN UN ITEM DEL MENU DE NAVEGACION
    private class MenuListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            navigate(item);
            return true;
        }
    }




    public ListaFragmentSeries getElFragment() {
        return elFragment;
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }


    public void checkUser(){


        if(firebaseuser != null){

            firebaseAuth.signOut();
            firebaseuser = firebaseAuth.getCurrentUser();
            navigationView.getMenu().getItem(0).setTitle("Loguearse");
            textViewHeaderUsuario.setText("");

        } else {

            navigationView.getMenu().getItem(0).setTitle("Cerrar Sesión");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public void setearMenuUsuario(){


        if(firebaseuser != null){
            navigationView.getMenu().getItem(0).setTitle("Cerrar Sesión");
            textViewHeaderUsuario.setText(firebaseuser.getDisplayName());

        } else {

            navigationView.getMenu().getItem(0).setTitle("Loguearse");
            textViewHeaderUsuario.setText("");
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        setearMenuUsuario();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseuser = firebaseAuth.getCurrentUser();
    }
}


