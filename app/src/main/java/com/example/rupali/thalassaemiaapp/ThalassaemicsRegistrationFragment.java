package com.example.rupali.thalassaemiaapp;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThalassaemicsRegistrationFragment extends Fragment {


    EditText name;
    EditText phoneNo;
    EditText email;
    EditText countryEditText;
    EditText stateEditText;
    EditText cityEditText;
    EditText completePostalAdEditText;
    EditText pincodeEditText;
    EditText phoneCodeEditText;
    TextView dob;
    ImageView dropdown;
    RadioGroup genderRadioGroup;
    RadioGroup bloodGroupRadioGroup;
    RadioGroup typeRadioGroup;
    CheckBox declarationCheckbox;
    String names;
    String dobs;
    String contactNo;
    String emails;
    String country;
    String state;
    String city;
    String completePostalAd;
    String pincode;
    String gender;
    String bloodGroup;
    String types;
    Button submitForm;
    Boolean declarationIsChecked;

    private int mYear, mMonth, mDay;

    DatabaseReference patients;
    FirebaseDatabase patientDatanase;


    public ThalassaemicsRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_thalassaemics_registration, container, false);

        //setContentView(R.layout.fragment_thalassaemics_registration);

        patients = FirebaseDatabase.getInstance().getReference("patients");

        dropdown = view.findViewById(R.id.patient_calender_dropdown);
        dob = view.findViewById(R.id.patient_dob);
        email = view.findViewById(R.id.patient_email);
        phoneNo = view.findViewById(R.id.patient_phone);
        phoneCodeEditText = view.findViewById(R.id.patient_phone_code);
        countryEditText = view.findViewById(R.id.patient_country);
        stateEditText = view.findViewById(R.id.patient_state);
        cityEditText = view.findViewById(R.id.patient_city);
        completePostalAdEditText = view.findViewById(R.id.patient_address);
        pincodeEditText = view.findViewById(R.id.patient_pin_code);
        name = view.findViewById(R.id.patient_name);
        genderRadioGroup = view.findViewById(R.id.patient_gender_radiogroup);
        typeRadioGroup = view.findViewById(R.id.patient_type);
        bloodGroupRadioGroup = view.findViewById(R.id.patient_blood_group_radiogroup);
        declarationCheckbox = view.findViewById(R.id.patient_declaration_checkbox);
        submitForm = view.findViewById(R.id.button_thallaesimic);

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

                                dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatient(view);
            }
        });

        return view;
    }

    private void addPatient(View view)
    {

        names=name.getText().toString();
        if(names.isEmpty()){
            Toast.makeText(getContext(),"Name is required",Toast.LENGTH_SHORT).show();
            return;
        }
        dobs=dob.getText().toString();
        if(dobs.isEmpty()){
            Toast.makeText(getContext(),"DOB is required",Toast.LENGTH_SHORT).show();
            return;
        }
        contactNo=phoneCodeEditText.getText().toString()+phoneNo.getText().toString();
        if(contactNo.length()<13){
            Toast.makeText(getContext(),"Contact No. has to be 10 digits ",Toast.LENGTH_SHORT).show();
            return;
        }
        emails=email.getText().toString();
        if(emails.isEmpty()){
            Toast.makeText(getContext(),"Email is Required ",Toast.LENGTH_SHORT).show();
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
        int typeRadioButtonId = typeRadioGroup.getCheckedRadioButtonId();
        if(typeRadioButtonId==-1)
        {
            Toast.makeText(getContext(),"No type Selected ",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            RadioButton radioButton = typeRadioGroup.findViewById(typeRadioButtonId);
            types = radioButton.getText().toString();
        }
        declarationIsChecked=declarationCheckbox.isChecked();
        if(declarationIsChecked.booleanValue()==false){
            Toast.makeText(getContext(),"Please accept the declaration to continue. ",Toast.LENGTH_SHORT).show();
            return;
        }
        String id = patients.push().getKey();
        Thalassaemics thalassaemic = new Thalassaemics(names,dobs,contactNo, emails, country, state, city, completePostalAd, pincode, gender, bloodGroup, types,declarationIsChecked);
        patients.child(id).setValue(thalassaemic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError==null) {
                    Toast.makeText(getContext(), "Form successfully submitted ", Toast.LENGTH_SHORT).show();
                    Log.d("RealtimeDatabase","success");
                    getActivity().onBackPressed();

                }
                else{
                    Toast.makeText(getContext(), "An error occured while submitting form. Try again", Toast.LENGTH_SHORT).show();
                    Log.d("RealtimeDatabase","Failure "+databaseError.getMessage());
                }
            }
        });



    }

}
