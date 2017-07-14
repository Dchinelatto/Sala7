package digitalhouse.android.a0317moacns1c_01.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import digitalhouse.android.a0317moacns1c_01.Controller.ControllerMedia;
import digitalhouse.android.a0317moacns1c_01.Model.Trailer;
import digitalhouse.android.a0317moacns1c_01.R;
import digitalhouse.android.a0317moacns1c_01.Utils.ResultListener;
import digitalhouse.android.a0317moacns1c_01.Utils.TMDBHelper;
import digitalhouse.android.a0317moacns1c_01.View.Activities.YouTubeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class YouTubeFragment extends YouTubePlayerSupportFragment {

    private static final int RECOVERY_REQUEST = 1;


    private Integer mediaId;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {


        bundle = getArguments();
        mediaId = bundle.getInt(YouTubeActivity.MEDIA_ID);

        initialize(TMDBHelper.youTubeApiKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    ControllerMedia controllerMedia = new ControllerMedia(getContext());
                    controllerMedia.traerTrailer(mediaId, new ResultListener<Trailer>() {
                        @Override
                        public void finish(Trailer resultado) {
                            youTubePlayer.cueVideo(resultado.getTrailer());
                        }
                    });
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

//        setRetainInstance(true);
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }


}
