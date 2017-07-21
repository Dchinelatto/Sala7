package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import digitalhouse.android.a0317moacns1c_01.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnBoardingFragment extends Fragment {


    public static final String IMAGEN= "imagen";



    // CREADOR DE FRAGMENTS
    public static OnBoardingFragment OnBoardingFragmentCreator(Integer imagen) {
        Bundle bundle= new Bundle();
        bundle.putInt(IMAGEN,imagen);

        OnBoardingFragment nuevoFragment = new OnBoardingFragment();
        nuevoFragment.setArguments(bundle);
        return nuevoFragment;
    }

    public OnBoardingFragment() {
        // Required empty public constructor
    }

    private Integer imagen;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_on_boarding, container, false);




          Bundle bundle = getArguments();
          imagen = bundle.getInt(IMAGEN);



        ImageView imageView = (ImageView) view.findViewById(R.id.imagenOnboarding);
        imageView.setImageResource(imagen);



        return view;

    }



}
