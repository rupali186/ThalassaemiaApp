package com.example.rupali.thalassaemiaapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeASupportFragment extends Fragment {
    Button payViaRazorpay;
    EditText amountEdittext;
    int amount;
    public BeASupportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_be_asupport, container, false);
        payViaRazorpay=view.findViewById(R.id.support_pay_buttton);
        amountEdittext=view.findViewById(R.id.support_amount);
        payViaRazorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
        return view;
    }

    private void startPayment() {
        String amountString=amountEdittext.getText().toString();
        if(!amountString.isEmpty()) {
            amount = Integer.parseInt(amountString);
            Checkout checkout=new Checkout();
            checkout.setImage(R.drawable.logo_min);
            final Activity activity = getActivity();

            /**
             * Pass your payment options to the Razorpay Checkout as a JSONObject
             */
            try {
                JSONObject options = new JSONObject();

                /**
                 * Merchant Name
                 * eg: Rentomojo || HasGeek etc.
                 */
                options.put("name", "Foundation Against Thalassaemia");

                /**
                 * Description can be anything
                 * eg: Order #123123
                 *     Invoice Payment
                 *     etc.
                 */
                options.put("description", "Donation");

                options.put("currency", "INR");

                /**
                 * Amount is always passed in PAISE
                 * Eg: "500" = Rs 5.00
                 */
                options.put("amount",amount*100);
//                JSONObject preFill = new JSONObject();
//                preFill.put("email", "thalassaemia12@gmail.com");
//                preFill.put("contact", "8750639133");
//
//                options.put("prefill", preFill);
                checkout.open(activity, options);
            } catch(Exception e) {
                Log.e("RazopayPayment", "Error in starting Razorpay Checkout", e);
            }

        }
        else{
            Toast.makeText(getContext(),"Enter an amount to continue...",Toast.LENGTH_SHORT).show();
            return;
        }
    }


}
