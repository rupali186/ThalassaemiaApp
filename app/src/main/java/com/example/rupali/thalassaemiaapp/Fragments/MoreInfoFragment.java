package com.example.rupali.thalassaemiaapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rupali.thalassaemiaapp.Activities.PoliciesActivity;
import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.R;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreInfoFragment extends Fragment {
    TextView privacyPolicy;
    TextView termsOfService;
    TextView openSourceLibraries;
    public MoreInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_more_info, container, false);
        privacyPolicy=view.findViewById(R.id.privacy_policy_short);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                privacyPolicyClick();
            }
        });
        termsOfService=view.findViewById(R.id.terms_of_service_short);
        termsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                termsOfServiceclick();
            }
        });
        openSourceLibraries=view.findViewById(R.id.open_source_libraries);
        openSourceLibraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSourceLibraries();
            }
        });

        return view;
    }

    private void openSourceLibraries() {
//        Intent intent=new Intent(getContext(), PoliciesActivity.class);
//        intent.putExtra(Constants.POLICY_STRING_HEAD,Constants.OPEN_SOURCE_LIBRARIES);
//        startActivity(intent);
        OssLicensesMenuActivity.setActivityTitle("Open Source Libraries");
        startActivity(new Intent(getContext(), OssLicensesMenuActivity.class));
    }

    private void termsOfServiceclick() {
        Intent intent=new Intent(getContext(), PoliciesActivity.class);
        intent.putExtra(Constants.POLICY_STRING_HEAD,Constants.TERMS_OF_SERVICE);
        startActivity(intent);
    }

    private void privacyPolicyClick() {
        Intent intent=new Intent(getContext(), PoliciesActivity.class);
        intent.putExtra(Constants.POLICY_STRING_HEAD,Constants.PRIVACY_POLICY);
        startActivity(intent);
    }

}
