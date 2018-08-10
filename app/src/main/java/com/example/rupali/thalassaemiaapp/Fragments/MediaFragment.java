package com.example.rupali.thalassaemiaapp.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rupali.thalassaemiaapp.Adapters.EventsPagerAdapter;
import com.example.rupali.thalassaemiaapp.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaFragment extends Fragment {
    ViewPager eventsViewPager;
    EventsPagerAdapter eventsPagerAdapter;
//    ViewPager pattronsViewPager;
//    EventsPagerAdapter pattronsPagerAdapter;
    Integer[] eventsImageId = {R.drawable.media7, R.drawable.media8, R.drawable.media9, R.drawable.media10,R.drawable.pic_gallery_1, R.drawable.pic_gallery_10, R.drawable.pic_gallery_11,R.drawable.getting_married_media, R.drawable.media3, R.drawable.media4, R.drawable.media5};
    //String[] eventsImagesName = {"image1","image2","image3","image4"};
//    Integer[] pattronsImageId = {R.drawable.media7, R.drawable.media8, R.drawable.media9, R.drawable.media10};
    //String[] pattronsImageName = {"image1","image2","image3","image4"};
    Timer eventsTimer;
    Timer pattronsTimer;
    //TextView viewMore;
    public MediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_media, container, false);
        eventsViewPager=view.findViewById(R.id.events_viewpager);
//        pattronsViewPager=view.findViewById(R.id.pattrons_viewpager);
        eventsPagerAdapter = new EventsPagerAdapter(getActivity(),eventsImageId,false);
        eventsViewPager.setAdapter(eventsPagerAdapter);
//        pattronsPagerAdapter = new EventsPagerAdapter(getActivity(),pattronsImageId);
//        pattronsViewPager.setAdapter(pattronsPagerAdapter);
//        viewMore=view.findViewById(R.id.view_more_media);
//        viewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri uri = Uri.parse("http://www.thebetterindia.com/155062/ias-sukhsohit-singh-thalassemia-upsc-india-news/"); // missing 'http://' will cause crashed
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(intent);
//            }
//        });
        TimerTask eventsTimerTask = new TimerTask() {
            @Override
            public void run() {
                eventsViewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        eventsViewPager.setCurrentItem((eventsViewPager.getCurrentItem()+1)%eventsImageId.length);
                    }
                });
            }
        };
        eventsTimer = new Timer();
        eventsTimer.schedule(eventsTimerTask, 5000, 5000);
//        TimerTask pattronsTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                pattronsViewPager.post(new Runnable(){
//
//                    @Override
//                    public void run() {
//                        pattronsViewPager.setCurrentItem((pattronsViewPager.getCurrentItem()+1)%pattronsImageId.length);
//                    }
//                });
//            }
//        };
//        pattronsTimer = new Timer();
//        pattronsTimer.schedule(pattronsTimerTask, 5000, 5000);
        return  view;
    }

}
