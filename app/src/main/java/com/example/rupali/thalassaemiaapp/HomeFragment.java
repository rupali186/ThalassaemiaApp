package com.example.rupali.thalassaemiaapp;


import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    TextView highlightTextView;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        highlightTextView=view.findViewById(R.id.home_highlight_text);
        //TextView secondTextView = new TextView(getContext());
        Shader textShader=new LinearGradient(0, 0, 0, 20,
                new int[]{Color.GREEN, Color.BLUE},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        highlightTextView.getPaint().setShader(textShader);
       // highlightTextView.startAnimation(AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_in_left));
        return  view;
    }

    @Override
    public void onDestroy() {
       // highlightTextView.clearAnimation();
        super.onDestroy();
    }
}
