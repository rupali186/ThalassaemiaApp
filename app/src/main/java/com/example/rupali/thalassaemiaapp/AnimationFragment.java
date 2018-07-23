package com.example.rupali.thalassaemiaapp;


import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;


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
                Log.d("RealtimeDatabase","success");
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
