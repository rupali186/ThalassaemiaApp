package com.example.rupali.thalassaemiaapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    ViewPager pattronsViewPager;
    EventsPagerAdapter pattronsPagerAdapter;
    Integer[] eventsImageId = {R.drawable.event_1, R.drawable.event_2, R.drawable.event_3, R.drawable.event_4};
    //String[] eventsImagesName = {"image1","image2","image3","image4"};
    Integer[] pattronsImageId = {R.drawable.event_1, R.drawable.event_2, R.drawable.event_3, R.drawable.event_4};
    //String[] pattronsImageName = {"image1","image2","image3","image4"};
    Timer eventsTimer;
    Timer pattronsTimer;

    public MediaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_media, container, false);
        eventsViewPager=view.findViewById(R.id.events_viewpager);
        pattronsViewPager=view.findViewById(R.id.pattrons_viewpager);
        eventsPagerAdapter = new EventsPagerAdapter(getActivity(),eventsImageId);
        eventsViewPager.setAdapter(eventsPagerAdapter);
        pattronsPagerAdapter = new EventsPagerAdapter(getActivity(),pattronsImageId);
        pattronsViewPager.setAdapter(pattronsPagerAdapter);
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
        eventsTimer.schedule(eventsTimerTask, 3000, 3000);
        TimerTask pattronsTimerTask = new TimerTask() {
            @Override
            public void run() {
                pattronsViewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        pattronsViewPager.setCurrentItem((pattronsViewPager.getCurrentItem()+1)%pattronsImageId.length);
                    }
                });
            }
        };
        pattronsTimer = new Timer();
        pattronsTimer.schedule(pattronsTimerTask, 3000, 3000);
        return  view;
    }

}
