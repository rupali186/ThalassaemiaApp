package com.example.rupali.thalassaemiaapp.JavaClass;

public class Thalassaemics {

    private String name;
    private String dob;
    private String contactNo;
    private String email;
    private String country;
    private String state;
    private String city;
    private String completeAddress;
    private String pincode;
    private String gender;
    private String bloodGroup;
    private String type;
    boolean isDecChecked;

    public Thalassaemics(String name, String dob, String contactNo, String email, String country, String state, String city, String completeAddress, String pincode, String gender, String bloodGroup, String type, boolean isDecChecked) {
        this.name = name;
        this.dob = dob;
        this.contactNo = contactNo;
        this.email = email;
        this.country = country;
        this.state = state;
        this.city = city;
        this.completeAddress = completeAddress;
        this.pincode = pincode;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.type = type;
        this.isDecChecked = isDecChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDecChecked() {
        return isDecChecked;
    }

    public void setDecChecked(boolean decChecked) {
        isDecChecked = decChecked;
    }
}
