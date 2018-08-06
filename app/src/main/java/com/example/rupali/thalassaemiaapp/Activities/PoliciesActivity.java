package com.example.rupali.thalassaemiaapp.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PoliciesActivity extends AppCompatActivity {
    TextView policyTextView;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.policy_act_toolbar_text);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle.setText("Privacy Policy");
        policyTextView=findViewById(R.id.policy_text_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            policyTextView.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        Intent intent=getIntent();
        if(intent.hasExtra(Constants.POLICY_STRING_HEAD)){
            int id=intent.getIntExtra(Constants.POLICY_STRING_HEAD,1);
            switch (id){
                case Constants.PRIVACY_POLICY: policyTextView.setText(R.string.privacy_policy);
                    toolbarTitle.setText("Privacy Policy");
                    break;
                case Constants.OPEN_SOURCE_LIBRARIES: policyTextView.setText(R.string.open_source_libraries);
                    toolbarTitle.setText("Open Source Libraries");
                    break;
                case Constants.TERMS_OF_SERVICE: policyTextView.setText(R.string.terms_of_service);
                    toolbarTitle.setText("Terms and Conditions");
                    break;
                default: policyTextView.setText(R.string.privacy_policy);
                    toolbarTitle.setText("Privacy Policy");
                    break;

            }
        }
    }

}
