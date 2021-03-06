package com.example.rupali.thalassaemiaapp.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rupali.thalassaemiaapp.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {
    ImageView googlePlus;
    ImageView twitter;
    ImageView insta;
    ImageView facebook;
    ImageView linkedln;
    ImageView youtube;
    ImageView pinterest;
    ImageView web;
    ImageView mail;
    //TextView highlightTextView;
    TextView textView;
    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
               // highlightTextView=view.findViewById(R.id.about_highlight_text);
        //TextView secondTextView = new TextView(getContext());
        textView=view.findViewById(R.id.about_us_content);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        Shader textShader=new LinearGradient(0, 0, 0, 20,
                new int[]{Color.GREEN, Color.BLUE},
                new float[]{0, 1}, Shader.TileMode.CLAMP);
//        highlightTextView.getPaint().setShader(textShader);
//        highlightTextView.setSelected(true);
        web=view.findViewById(R.id.web);
        mail=view.findViewById(R.id.mail);
        googlePlus=view.findViewById(R.id.goolePlus);
        twitter = view.findViewById(R.id.twitter);
        insta=view.findViewById(R.id.insta);
        facebook = view.findViewById(R.id.facebook);
        linkedln =view.findViewById(R.id.linkedln);
        youtube = view.findViewById(R.id.youtube);
        pinterest=view.findViewById(R.id.pinterest);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.thalassaemia.in"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:fa.thalassaemia@gmail.com"));
                //intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback or subject");
                startActivity(intent);
            }
        });

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
                String url = "https://www.facebook.com/groups/310086002828583/";
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
        googlePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://plus.google.com/116395694777442972322";
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://youtube.com/channel/UCATPmbfDePZNrqjikz_1-0A/videos";
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
