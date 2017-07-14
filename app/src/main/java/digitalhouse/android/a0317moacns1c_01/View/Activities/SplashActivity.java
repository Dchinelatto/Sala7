package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Danilo on 02/06/2017.
 */

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent elIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(elIntent);
        finish();

    }
}
