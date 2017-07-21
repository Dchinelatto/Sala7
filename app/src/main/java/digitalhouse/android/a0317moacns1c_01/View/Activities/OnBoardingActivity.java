package digitalhouse.android.a0317moacns1c_01.View.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.View.Adapters.OnBoardingViewPagerAdapter;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.OnBoardingFragment;
import digitalhouse.android.a0317moacns1c_01.View.Fragments.OnBoardingIntroFragment;

public class OnBoardingActivity extends AppCompatActivity {

    private ViewPager pager;
    private SmartTabLayout indicator;
    private Button skip;
    private Button next;
    private OnBoardingViewPagerAdapter onBoardingViewPagerAdapter;
    private List<Fragment> onBoardingFragmentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);


         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        pager = (ViewPager)findViewById(R.id.pager);
        indicator = (SmartTabLayout)findViewById(R.id.indicator);
        skip = (Button) findViewById(R.id.skip);
        next = (Button) findViewById(R.id.next);

        crearFragments();
        onBoardingViewPagerAdapter= new OnBoardingViewPagerAdapter(getSupportFragmentManager(),onBoardingFragmentList);
        pager.setAdapter(onBoardingViewPagerAdapter);

        pager.setClipToPadding(false);


        
        indicator.setViewPager(pager);

        indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == 8){
                    skip.setVisibility(View.GONE);
                    next.setText("Terminado");
                } else {
                    skip.setVisibility(View.VISIBLE);
                    next.setText("Siguiente");
                }
            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishOnboarding();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pager.getCurrentItem() == 8) {
                    finishOnboarding();
                } else {
                    pager.setCurrentItem(
                            pager.getCurrentItem() + 1,
                            true
                    );
                }
            }
        });



    }


    public void crearFragments(){

       onBoardingFragmentList= new ArrayList<>();


            onBoardingFragmentList.add(OnBoardingIntroFragment.OnBoardingIntroFragmentCreator(R.anim.fade_in,"Bienvenido a","Sala 7"));
            onBoardingFragmentList.add(OnBoardingFragment.OnBoardingFragmentCreator(R.drawable.imagen_1_definitva));
            onBoardingFragmentList.add(OnBoardingFragment.OnBoardingFragmentCreator(R.drawable.imagen_2_definitiva));
            onBoardingFragmentList.add(OnBoardingFragment.OnBoardingFragmentCreator(R.drawable.imagen_3_definitiva));
            onBoardingFragmentList.add(OnBoardingFragment.OnBoardingFragmentCreator(R.drawable.imagen_4_definitiva));
            onBoardingFragmentList.add(OnBoardingFragment.OnBoardingFragmentCreator(R.drawable.imagen_5_definitiva));
            onBoardingFragmentList.add(OnBoardingFragment.OnBoardingFragmentCreator(R.drawable.imagen_6_definitiva));
            onBoardingFragmentList.add(OnBoardingFragment.OnBoardingFragmentCreator(R.drawable.imagen_7_definitiva));
            onBoardingFragmentList.add(OnBoardingIntroFragment.OnBoardingIntroFragmentCreator(R.anim.fade_in,null,"A comenzar!"));

        }


    private void finishOnboarding() {
        // Get the shared preferences
        SharedPreferences preferences =
                getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Set onboarding_complete to true
        preferences.edit()
                .putBoolean("onboarding_complete",true).apply();

        // Launch the main Activity, called MainActivity
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);

        // Close the OnboardingActivity
        finish();
    }


}
