package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.YouTubeFragment;

public class YouTubeActivity extends AppCompatActivity {

    public static final String MEDIA_ID = "media_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Bundle bundle = getIntent().getExtras();

        YouTubeFragment youTubeFragment = new YouTubeFragment();

        youTubeFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container_youtube, youTubeFragment);

        fragmentTransaction.commit();

    }
}
