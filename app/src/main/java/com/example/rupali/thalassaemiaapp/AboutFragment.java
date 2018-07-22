package com.example.rupali.thalassaemiaapp;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    ImageView twitter;
    ImageView insta;
    ImageView facebook;
    ImageView linkedln;
    ImageView youtube;
    ImageView pinterest;
    TextView highlightTextView;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
                highlightTextView=view.findViewById(R.id.about_highlight_text);
        //TextView secondTextView = new TextView(getContext());
        Shader textShader=new LinearGradient(0, 0, 0, 20,
                new int[]{Color.GREEN, Color.BLUE},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
        highlightTextView.getPaint().setShader(textShader);        twitter = view.findViewById(R.id.twitter);
        insta=view.findViewById(R.id.insta);
        facebook = view.findViewById(R.id.facebook);
        linkedln =view.findViewById(R.id.linkedln);
        youtube = view.findViewById(R.id.youtube);
        pinterest=view.findViewById(R.id.pinterest);



        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://twitter.com/FA_Thalassaemia/";
                intent.setData(Uri.parse(url));
                //intent.setType("text/plain");
                startActivity(intent);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://www.instagram.com/fa_thalassaemia//";
                intent.setData(Uri.parse(url));
                //intent.setType("text/plain");
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://www.facebook.com/FA.Thalassaemia/";
                intent.setData(Uri.parse(url));
                //intent.setType("text/plain");
                startActivity(intent);
            }
        });
        pinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://pin.it/h5hq32uaefasme";
                intent.setData(Uri.parse(url));
                //intent.setType("text/plain");
                startActivity(intent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://youtube.com/channel/UCATPmbfDePZNrqjikz_1-0A";
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        linkedln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://www.linkedin.com/in/fa-thalassaemia-foundation-against-thalassaemia-9b7584166";
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });



       return view;
    }

}
