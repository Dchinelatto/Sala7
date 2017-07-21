package digitalhouse.android.a0317moacns1c_01.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.Model.Media;
import digitalhouse.android.a0317moacns1c_01.Utils.HTTPConnectionManager;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.View.Activities.FavoritosActivity;
import retrofit2.Call;

/**
 * Created by danil on 17-Jul-17.
 */

public class DAOFirebase {


    public void insertarFavoritoFirebase(String usuarioId, String mediaId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(usuarioId).child(mediaId);

        myRef.setValue(mediaId);

    }


    public void eliminarFavoritoFirebase(String usuarioId, String mediaId) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(usuarioId).child(mediaId);
        myRef.removeValue();

    }


    public void obtenerFavoritosFirebase(String usuarioId, final ResultListener<List<String>> listenerDelController) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child(usuarioId);

        final List<String> listaMediaID = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot stringSnapshot : dataSnapshot.getChildren()) {
                    String mediaID = (String) stringSnapshot.getValue();
                    listaMediaID.add(mediaID);

                }

                listenerDelController.finish(listaMediaID);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }


    public void bajarFotoTwitterYSubirFotoAFirebase(final String usuarioID){


        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        Call<User> userCall = twitterApiClient.getAccountService().verifyCredentials(false, false, false);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> result) {
                String photoUrlNormalSize = result.data.profileImageUrl.replace("_normal", "_bigger");;

                subirFotoAFirebase(usuarioID, photoUrlNormalSize);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

    }

    private void subirFotoAFirebase(String usuarioID, String urlFoto){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Fotos").child(usuarioID).child("Foto");

        myRef.setValue(urlFoto);

    }


    public void obtenerFotoFirebase(String usuarioId, final ResultListener<String> listenerDelController) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Fotos").child(usuarioId).child("Foto");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    String fotoURL = (String) dataSnapshot.getValue();



                listenerDelController.finish(fotoURL);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

    }


}