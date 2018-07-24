package com.example.rupali.thalassaemiaapp;


import android.animation.Animator;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
                SendMail sendMail=new SendMail();
                sendMail.execute("");
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
    private class SendMail extends AsyncTask<String, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(getContext(), "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        protected Void doInBackground(String... params) {
            String[] toArr = {"rupali186@gmail.com", "rupali9@gmail.com"};
            String[] fromArr = {Constants.MAIL_EMAIL};

            Mail m = new Mail(Constants.USERNAME,Constants.MAIL_PASSWORD,toArr,Constants.MAIL_EMAIL,
                    "Registeration confirmation for thalassaemis HbA2 Carrier Test.","Thanks for registering. Your form is successfully submitted.");

//            m.setTo(toArr);
//            m.setFrom("thalassaemia12@gmail");
//            m.setSubject("Registeration confirmation for thalassaemis HbA2 Carrier Test.");
//            m.setBody("Thanks for registering. Your form is successfully submitted.");

            try {
                if(m.send()) {
                    Log.d("MailApp", " send email success");
                    //Toast.makeText(MainActivity.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("MailApp", "mail not sent");
                    //Toast.makeText(MainActivity.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                }
            } catch(Exception e) {
                Log.d("MailApp", "Could not send email+ Message: "+e.getMessage(), e);
            }
            return null;
        }
    }

}
