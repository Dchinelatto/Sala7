package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.content.Intent;

import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;

import java.util.List;

import digitalhouse.android.a0317moacns1c_01.DAO.DAOFirebase;
import digitalhouse.android.a0317moacns1c_01.DAO.DAOTablaGeneros;
import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Model.Genero;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.GeneroFragment;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragment;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaFragmentSeries;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.ListaResultados;
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
    private MaterialSearchView materialSearchView;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseuser;

    private TextView textViewHeaderUsuario;
    private ImageView imageViewHeaderUsuario;


    // LISTAS DE PELICULAS
    private List<Genero> listaGeneros;

    public static Boolean logueado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the shared preferences
        SharedPreferences preferences =  getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Check if onboarding_complete is false
        if(!preferences.getBoolean("onboarding_complete",false)) {
            // Start the onboarding Activity
            Intent onboarding = new Intent(this, OnBoardingActivity.class);
            startActivity(onboarding);

            // Close the main Activity
            finish();
            return;
        }
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
        imageViewHeaderUsuario = (ImageView) view.findViewById(R.id.imageView_imagenUsuario);

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
            case (R.id.menu_documental):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_DOCUMENTARY));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_familia):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_FAMILY));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_historia):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_HISTORY));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_pelitele):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_SCIENCE_TV_MOVIE));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_western):
                elBundle.putInt(DAOTablaGeneros.GENERO_ID, Integer.parseInt(TMDBHelper.MOVIE_GENRE_SCIENCE_WESTERN));
                elFragment.setArguments(elBundle);
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, elFragment);
                break;
            case (R.id.menu_login):
                checkUser();
                break;

            case (R.id.menu_favoritos):
                if(firebaseuser != null){

                    Intent intent = new Intent(this, FavoritosActivity.class);
                    intent.putExtra(FavoritosActivity.FAV_O_RECOM, "favoritos");
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);

                }

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
            Toast.makeText(this, "Se cerró la sesión exitosamente", Toast.LENGTH_SHORT).show();
            firebaseuser = firebaseAuth.getCurrentUser();
            ocultarViewsNavigationHeader();

        } else {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }

    public void setearMenuUsuario(){


        if(firebaseuser != null){
            mostrarViewsNavigationHeader();
            textViewHeaderUsuario.setText(firebaseuser.getDisplayName());

            DAOFirebase daoFirebase = new DAOFirebase();
            daoFirebase.obtenerFotoFirebase(firebaseuser.getUid(), new ResultListener<String>() {
                @Override
                public void finish(String resultado) {
                    Picasso.with(MainActivity.this).load(resultado).noFade().into(imageViewHeaderUsuario);
                }
            });




        } else {

            ocultarViewsNavigationHeader();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar_main,menu);

        menuItem= menu.findItem(R.id.busquedaItem);

        materialSearchView= (MaterialSearchView) findViewById(R.id.material_search_view);

        materialSearchView.setMenuItem(menuItem);

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {

            @Override

            public boolean onQueryTextSubmit(String query) {



                return true;

            }



            @Override
            //LA BUSQUEDA CAMBIA A MEDIDA QUE SE VA ESCRIBIENDO
            public boolean onQueryTextChange(String newText) {

                ListaResultados listaResultados= new ListaResultados();

                //REEMPLAZO LOS ESPACIOS PARA QUE EN LA BUSQUEDA CONSIDERE LOS ESPACIOS ENTRE PALABRAS
                newText= newText.replaceAll(" ","%20");
                Bundle bundle= new Bundle();
                bundle.putString("busqueda",newText);
                listaResultados.setArguments(bundle);
                FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout_fragmentList,listaResultados);
                fragmentTransaction.commit();

                return true;
            }
        });


        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {

            @Override

            public void onSearchViewShown() {}

            @Override
            public void onSearchViewClosed() {

                viewPagerFragment = new ViewPagerFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout_fragmentList, viewPagerFragment);
                fragmentTransaction.commit();
            }});

        return true;
    }

    public void ocultarViewsNavigationHeader(){
        navigationView.getMenu().getItem(0).setTitle("Loguearse");
        textViewHeaderUsuario.setVisibility(View.GONE);
        imageViewHeaderUsuario.setVisibility(View.GONE);
    }

    public void mostrarViewsNavigationHeader(){
        navigationView.getMenu().getItem(0).setTitle("Cerrar Sesión");
        textViewHeaderUsuario.setVisibility(View.VISIBLE);
        imageViewHeaderUsuario.setVisibility(View.VISIBLE);
    }
}


