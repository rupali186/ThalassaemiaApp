package com.example.rupali.thalassaemiaapp.Adapters;


import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rupali.thalassaemiaapp.R;

public class EventsPagerAdapter extends PagerAdapter {

    private Activity activity;
    private Integer[] imagesArray;
    boolean picture_type=false;
    //private String[] namesArray;

    public EventsPagerAdapter(Activity activity,Integer[] imagesArray,boolean picture_type){

        this.activity = activity;
        this.imagesArray = imagesArray;
        this.picture_type=picture_type;
//        this.namesArray = namesArray;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = ((Activity)activity).getLayoutInflater();
        View viewItem=null;
        if(picture_type) {
             viewItem = inflater.inflate(R.layout.picture_item, container, false);
        }else{
            viewItem = inflater.inflate(R.layout.media_item, container, false);
        }
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.event_image);
        imageView.setImageResource(imagesArray[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        TextView textView1 = (TextView) viewItem.findViewById(R.id.event_des);
//        textView1.setText(namesArray[position]);
        ((ViewPager)container).addView(viewItem);

        return viewItem;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imagesArray.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}