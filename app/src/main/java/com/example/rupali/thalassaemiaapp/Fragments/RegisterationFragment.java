package com.example.rupali.thalassaemiaapp.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.rupali.thalassaemiaapp.JavaClass.Constants;
import com.example.rupali.thalassaemiaapp.JavaClass.Donor;
import com.example.rupali.thalassaemiaapp.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterationFragment extends Fragment {
    EditText nameEditText;
    TextView dobTextView;
    EditText contactNoEditText;
    TextView emailTextView;
    EditText countryEditText;
    EditText stateEditText;
    EditText cityEditText;
    EditText completePostalAdEditText;
    EditText pincodeEditText;
    RadioGroup genderRadioGroup;
    RadioGroup bloodGroupRadioGroup;
    CheckBox declarationCheckbox;
    TextView declarationTextView;
    ImageView dropdown;
    EditText phoneCodeEditText;
    String name;
    String dob;
    String contactNo;
    String email;
    String country;
    String state;
    String city;
    String completePostalAd;
    String pincode;
    String gender;
    String bloodGroup;
    Boolean declarationIsChecked;
    private int mYear, mMonth, mDay;
    Button submitForm;
    int position;
    SharedPreferences sharedPreferences;

    public RegisterationFragment() {
        // Required empty public constructor
    }
    public interface RegFragmentListenerInterface
    {
        void register(Donor donor);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_registeration, container, false);
        Bundle bundle = this.getArguments();
        position=bundle.getInt(Constants.SPINNER_POS);
        nameEditText=view.findViewById(R.id.patient_name);
        declarationTextView=view.findViewById(R.id.declaration_text);
        dobTextView=view.findViewById(R.id.patient_dob);
        contactNoEditText=view.findViewById(R.id.patient_phone);
        phoneCodeEditText=view.findViewById(R.id.donor_phone_code);
        emailTextView=view.findViewById(R.id.patient_email);
        countryEditText=view.findViewById(R.id.patient_address);
        stateEditText=view.findViewById(R.id.donor_state);
        cityEditText=view.findViewById(R.id.donor_city);
        completePostalAdEditText=view.findViewById(R.id.patient_country);
        pincodeEditText=view.findViewById(R.id.patient_pin_code);
        genderRadioGroup=view.findViewById(R.id.patient_gender_radiogroup);
        bloodGroupRadioGroup=view.findViewById(R.id.patient_blood_group_radiogroup);
        declarationCheckbox=view.findViewById(R.id.patient_declaration_checkbox);
        submitForm=view.findViewById(R.id.submit_form);
        dropdown=view.findViewById(R.id.patient_calender_dropdown);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dobTextView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        sharedPreferences=getActivity().getSharedPreferences(Constants.LoginSharedPref.SHARED_PREF_NAME,MODE_PRIVATE);
        email=sharedPreferences.getString(Constants.LoginSharedPref.LOGIN_EMAIL,"");
        emailTextView.setText(email);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"If you want to use another email address, please log in with that email.",Toast.LENGTH_SHORT).show();
            }
        });
//        database = FirebaseDatabase.getInstance();
        if(position==Constants.THALASSAEMIA_CARRIER_TEST) {
            declarationTextView.setText(R.string.thali_carrier_declaration_text);
            //myRef = database.getReference("ThalassaemiaCarrierTest");
        }
        else if(position==Constants.BONE_MARROW_MATCHING){
            declarationTextView.setText(R.string.stem_cells_declaration_text);
           // myRef =database.getReference("BoneMarrowMatching");
        }
        else if(position==Constants.STEM_CELLS_DONATION){
            declarationTextView.setText(R.string.stem_cells_declaration_text);
            //myRef=database.getReference("StemCellsDonation");
        }
        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(view);
            }
        });

        return view;
    }

    private void register(View view) {
        name=nameEditText.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(getContext(),"Name is required",Toast.LENGTH_SHORT).show();
            return;
        }
        dob=dobTextView.getText().toString();
        if(dob.isEmpty()){
            Toast.makeText(getContext(),"DOB is required",Toast.LENGTH_SHORT).show();
            return;
        }
        contactNo=phoneCodeEditText.getText().toString()+contactNoEditText.getText().toString();
        if(contactNo.length()<13){
            Toast.makeText(getContext(),"Contact No. has to be 10 digits ",Toast.LENGTH_SHORT).show();
            return;
        }
        email=emailTextView.getText().toString();
        if(email.isEmpty()){
            Toast.makeText(getContext(),"Email is empty. Make sure you are logged in.",Toast.LENGTH_SHORT).show();
            return;
        }
        country=countryEditText.getText().toString();
        if(country.isEmpty()){
            Toast.makeText(getContext(),"Country is Required ",Toast.LENGTH_SHORT).show();
            return;
        }
        state=stateEditText.getText().toString();
        if(state.isEmpty()){
            Toast.makeText(getContext(),"State is Required ",Toast.LENGTH_SHORT).show();
            return;
        }
        city=cityEditText.getText().toString();
        if(city.isEmpty()){
            Toast.makeText(getContext(),"City is Required ",Toast.LENGTH_SHORT).show();
            return;
        }
        completePostalAd=completePostalAdEditText.getText().toString();
        if(completePostalAd.isEmpty()){
            Toast.makeText(getContext(),"Complete Postal Address is Required ",Toast.LENGTH_SHORT).show();
            return;
        }
        pincode=pincodeEditText.getText().toString();
        if(pincode.isEmpty()){
            Toast.makeText(getContext(),"Pincode is Required ",Toast.LENGTH_SHORT).show();
            return;
        }
        int genderCheckedRadioButtonId = genderRadioGroup.getCheckedRadioButtonId();
        if (genderCheckedRadioButtonId == -1) {
            // No item selected
            Toast.makeText(getContext(),"No Gender Selected ",Toast.LENGTH_SHORT).show();
            return;
        }
        else{

            RadioButton radioButton = (RadioButton) genderRadioGroup.findViewById(genderCheckedRadioButtonId);
            gender =radioButton.getText().toString();
        }
        int bGCheckedRadioButonId=bloodGroupRadioGroup.getCheckedRadioButtonId();
        if(bGCheckedRadioButonId==-1){
            Toast.makeText(getContext(),"No BloodGroup Selected ",Toast.LENGTH_SHORT).show();
            return;

        }else{
            RadioButton radioButton = (RadioButton) bloodGroupRadioGroup.findViewById(bGCheckedRadioButonId);
            bloodGroup =radioButton.getText().toString();
        }
        declarationIsChecked=declarationCheckbox.isChecked();
        if(declarationIsChecked.booleanValue()==false){
            Toast.makeText(getContext(),"Please accept the declaration to continue. ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(Constants.database==null){
            Log.d(Constants.TAG,"new database instance ");
            Constants.database = FirebaseDatabase.getInstance();
        }
        if(position==Constants.THALASSAEMIA_CARRIER_TEST) {
            Constants.myRef = Constants.database.getReference("ThalassaemiaCarrierTest");
        }
        else if(position==Constants.BONE_MARROW_MATCHING){
            Constants.myRef  =Constants.database.getReference("BoneMarrowMatching");
        }
        else if(position==Constants.STEM_CELLS_DONATION){
            Constants.myRef =Constants.database.getReference("StemCellsDonation");
        }
        String id=Constants.myRef .push().getKey();
        Donor donor=new Donor(name,dob,contactNo,email,country,state,city,completePostalAd,pincode,gender,bloodGroup,declarationIsChecked);
       // myRef.child(id).setValue(donor);
        Constants.myRef .child(id).setValue(donor,new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError==null) {
//                    Toast.makeText(getContext(), "Form successfully submitted ", Toast.LENGTH_SHORT).show();
//                    Log.d("RealtimeDatabase","success");
//                    getActivity().onBackPressed();
                    AnimationFragment animationFragment= new AnimationFragment();
//                    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                    android.support.v4.app.FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.container_main,animationFragment).commit();
                }
                else{
                    Toast.makeText(getContext(), "An error occured while submitting form. Try again", Toast.LENGTH_SHORT).show();
                    Log.d(Constants.TAG,"Realtime database Failure "+databaseError.getMessage());

                }
            }
        });

    }

}
