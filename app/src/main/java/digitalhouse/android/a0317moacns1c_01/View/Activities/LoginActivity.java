package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.Retornador{

    private FirebaseAuth mAuth;
    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        mAuth = FirebaseAuth.getInstance();

        loginFragment = new LoginFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frameLayout_contenedor_loginFragment,loginFragment);

        fragmentTransaction.commit();


    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        transicionCorrecta(currentUser);
    }



    public void transicionCorrecta(FirebaseUser user){

        if(user != null){

            Intent intent = new Intent(this, FavoritosActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginFragment.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void retroceder() {
        onBackPressed();
    }
}
