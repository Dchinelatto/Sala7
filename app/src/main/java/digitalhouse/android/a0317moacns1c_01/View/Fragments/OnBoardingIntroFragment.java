package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import digitalhouse.android.a0317moacns1c_01.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnBoardingIntroFragment extends Fragment {


    public static final String ANIMACION= "imagen";
    public static final String TEXTO1= "texto1";
    public static final String TEXTO2= "texto2";

    private Integer animacion;
    private String texto1;
    private String texto2;
    private Animation animation;



    // CREADOR DE FRAGMENTS
    public static OnBoardingIntroFragment OnBoardingIntroFragmentCreator(Integer animacion, String texto1, String texto2) {
        Bundle bundle= new Bundle();
        bundle.putInt(ANIMACION,animacion);
        bundle.putString(TEXTO1,texto1);
        bundle.putString(TEXTO2,texto2);

        OnBoardingIntroFragment onBoardingIntroFragment= new OnBoardingIntroFragment();
        onBoardingIntroFragment.setArguments(bundle);
        return onBoardingIntroFragment;
    }


    public OnBoardingIntroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle= getArguments();
        animacion= bundle.getInt(ANIMACION);
        texto1= bundle.getString(TEXTO1);
        texto2= bundle.getString(TEXTO2);


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_on_boarding_intro, container, false);


        ImageView imageView= (ImageView) view.findViewById(R.id.imagenIntro);
        TextView textview1= (TextView) view.findViewById(R.id.textoIntroduccion);
        TextView textview2= (TextView) view.findViewById(R.id.textoIntroduccion2);



            animation = AnimationUtils.loadAnimation(getContext(), animacion);
            imageView.startAnimation(animation);

        textview1.setText(texto1);
        textview2.setText(texto2);

        return view;
    }



}
