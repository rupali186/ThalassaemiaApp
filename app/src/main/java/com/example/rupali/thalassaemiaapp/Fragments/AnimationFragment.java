package com.example.rupali.thalassaemiaapp.Fragments;


import android.animation.Animator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.JavaClass.GMailSender;
import com.example.rupali.thalassaemiaapp.R;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnimationFragment extends Fragment {
    LottieAnimationView animationView;

    public AnimationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_animation, container, false);
        animationView=view.findViewById(R.id.my_animation);
        animationView.setAnimation(R.raw.success);
        animationView.playAnimation();
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                animationView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animationView.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(),"Form successfully submitted",Toast.LENGTH_SHORT).show();
                Log.d(Constants.TAG,"REaltime database form success");
                getActivity().onBackPressed();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        return view;
    }

}
