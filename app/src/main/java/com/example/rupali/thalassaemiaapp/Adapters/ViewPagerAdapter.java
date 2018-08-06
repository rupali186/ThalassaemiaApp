package com.example.rupali.thalassaemiaapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rupali.thalassaemiaapp.R;

public class ViewPagerAdapter extends PagerAdapter {
    LayoutInflater inflater;
    Context context;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    int[] image_list={
            R.drawable.blood_drop_min,
            R.drawable.fab_gi_donate_min,
            R.drawable.view_pager1_min,
            R.drawable.view_pager_min,
            R.drawable.hospital_min,
            R.drawable.blood_donation_background_min
    };
    String [] des_list={
            "A single pint of blood can save upto three lives...",
            "A single gesture can create a million smiles.",
            "Support our mission of 'Helping India become Thalassaemia Free by 2025'.",
            "Register yourself as a Blood Donor today.",
            "Register for the Thalassaemia Carrier (HbA2) test.",
            "Support a Thalassaemia patient for medication and tests."
    };
    @Override
    public int getCount() {
        return des_list.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.viewpager_item,container,false);
        ImageView imageView=view.findViewById(R.id.viewpager_image);
        TextView descriptionTextView=view.findViewById(R.id.viewpager_des);
        imageView.setImageResource(image_list[position]);
        descriptionTextView.setText(des_list[position]);
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
