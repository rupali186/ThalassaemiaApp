package com.example.rupali.thalassaemiaapp;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThalassaemiaFragment extends Fragment {
    TextView textView;
    public ThalassaemiaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_thalassaemia, container, false);
        textView=view.findViewById(R.id.thalassaemia_content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        return view;
    }
}
