package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Activities.FavoritosActivity;
import digitalhouse.android.a0317moacns1c_01.View.Activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    private TwitterLoginButton loginButton;
    private Button button;
    private EditText editText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        Twitter.initialize(getContext());


        getActivity().setTitle("Login");

        View view = inflater.inflate(R.layout.fragment_login, container, false);


        loginButton = (TwitterLoginButton) view.findViewById(R.id.loginButton);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                handleTwitterSession(result.data);

            }

            @Override
            public void failure(TwitterException exception) {

                Toast.makeText(getActivity(), "No se pudo loguear en Twitter", Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });


        return view;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    //FUNCION PARA QUE EL ERROR DEL TEXT INPUT LAYOUT DESAPAREZCA EN TIEMPO REAL
    public void editTextTiempoReal(final EditText editText, final TextInputLayout textInputLayout) {

        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textInputLayout.setError(null);
            }

            public void afterTextChanged(Editable editable) {
            }
        });

    }


    private void handleTwitterSession(TwitterSession session) {

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Se logueo correctamente", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(getActivity(), "No se pudo loguear en la aplicacion", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }

    public void updateUI(FirebaseUser user){

        if (user != null){

            Intent intent = new Intent(getActivity(), FavoritosActivity.class);
            intent.putExtra(FavoritosActivity.FAV_O_RECOM, "favoritos");
            startActivity(intent);

        } else {


        }

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

}





