package com.example.gomeds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class PharRegistration extends AppCompatActivity {
String[] Jharkhand ={"Ranchi","Jamshedpur","Bokaro"};
String[] MadhyaPradesh={"Bhopal","Anuppur"};
TextInputLayout Fname,Lname,Email,pass,cpass,mobileno,houseno,area,pincode;
Spinner Statespin,Cityspin;
Button signup,email,phone;
FirebaseAuth fauth;
DatabaseReference databaseReference;
FirebaseDatabase firebaseDatabase;
String fname,lname,emailid,password,confirmpassword,mobile,housenum,Area,Pincode,role="Phar",statee,cityy;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phar_registration);
Fname=(TextInputLayout) findViewById(R.id.Fname);
    Lname=(TextInputLayout) findViewById(R.id.Lname);
    Email=(TextInputLayout) findViewById(R.id.Email);
    pass=(TextInputLayout) findViewById(R.id.pass);
    cpass=(TextInputLayout) findViewById(R.id.cpass);
    mobileno=(TextInputLayout) findViewById(R.id.mobileno);
    houseno=(TextInputLayout) findViewById(R.id.houseno);
    pincode=(TextInputLayout) findViewById(R.id.pincode);
    Statespin=(Spinner) findViewById(R.id.statee);
    Cityspin=(Spinner) findViewById(R.id.Citys);
    area =(TextInputLayout) findViewById(R.id.Area);

  signup=(Button) findViewById(R.id.signup);
  email= (Button) findViewById(R.id.emailid);
  phone=(Button) findViewById(R.id.phone);


    Statespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Object value = parent.getItemAtPosition(position);
            statee = value.toString().trim();
            if(statee.equals("Jharkhand")){
                ArrayList<String> list = new ArrayList<>();
                for (String cities : Jharkhand){
                    list.add(cities);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PharRegistration.this,android.R.layout.simple_spinner_item,list);
                Cityspin.setAdapter(arrayAdapter);
            }
            if(statee.equals("MadhyaPradesh")){
                ArrayList<String> list = new ArrayList<>();
                for (String cities : MadhyaPradesh){
                    list.add(cities);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PharRegistration.this,android.R.layout.simple_spinner_item,list);
                Cityspin.setAdapter(arrayAdapter);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });

    Cityspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Object value = parent.getItemAtPosition(position);
            cityy = value.toString().trim();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });



    databaseReference = firebaseDatabase.getInstance().getReference("Chef");
    fauth = FirebaseAuth.getInstance();

    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            fname = Objects.requireNonNull(Fname.getEditText()).getText().toString().trim();
            lname = Objects.requireNonNull(Lname.getEditText()).getText().toString().trim();
            emailid = Objects.requireNonNull(Email.getEditText()).getText().toString().trim();
            mobile = Objects.requireNonNull(mobileno.getEditText()).getText().toString().trim();
            password = Objects.requireNonNull(pass.getEditText()).getText().toString().trim();
            confirmpassword = cpass.getEditText().getText().toString().trim();
            Area = area.getEditText().getText().toString().trim();
            housenum = houseno.getEditText().getText().toString().trim();
            Pincode = pincode.getEditText().getText().toString().trim();

            if (isValid()){
                final ProgressDialog mDialog = new ProgressDialog(PharRegistration.this);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage("Registration in progress please wait......");
                mDialog.show();

                fauth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            String useridd = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                            final HashMap<String , String> hashMap = new HashMap<>();
                            hashMap.put("Role",role);
                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    HashMap<String , String> hashMap1 = new HashMap<>();
                                    hashMap1.put("Mobile No",mobile);
                                    hashMap1.put("First Name",fname);
                                    hashMap1.put("Last Name",lname);
                                    hashMap1.put("EmailId",emailid);
                                    hashMap1.put("City",cityy);
                                    hashMap1.put("Area",Area);
                                    hashMap1.put("Password",password);
                                    hashMap1.put("Pincode",Pincode);
                                    hashMap1.put("State",statee);
                                    hashMap1.put("Confirm Password",confirmpassword);
                                    hashMap1.put("House",housenum);

                                    FirebaseDatabase.getInstance().getReference("Chef")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            mDialog.dismiss();

                                            Objects.requireNonNull(fauth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if(task.isSuccessful()){
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(PharRegistration.this);
                                                        builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                        builder.setCancelable(false);
                                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                dialog.dismiss();

                                                            }
                                                        });
                                                        AlertDialog Alert = builder.create();
                                                        Alert.show();
                                                    }else{
                                                        mDialog.dismiss();
                                                        ReusableCodeForALl.ShowAlert(PharRegistration.this,"Error", Objects.requireNonNull(task.getException()).getMessage());
                                                    }
                                                }
                                            });

                                        }
                                    });

                                }
                            });
                        }
                    }
                });
            }
//
        }
    });

}

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cpass.setErrorEnabled(false);
        cpass.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");
        pincode.setErrorEnabled(false);
        pincode.setError("");

        boolean isValid,isValidhouseno=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false,isValidarea=false,isValidpincode=false;
        if(TextUtils.isEmpty(fname)){
            Fname.setErrorEnabled(true);
            Fname.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(lname)){
            Lname.setErrorEnabled(true);
            Lname.setError("Enter Last Name");
        }else{
            isValidlname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            pass.setErrorEnabled(true);
            pass.setError("Enter Password");
        }else{
            if(password.length()<8){
                pass.setErrorEnabled(true);
                pass.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confirmpassword)){
            cpass.setErrorEnabled(true);
            cpass.setError("Enter Password Again");
        }else{
            if(!password.equals(confirmpassword)){
                cpass.setErrorEnabled(true);
                cpass.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile Number Is Required");
        }else{
            if(mobile.length()<10){
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }
        if(TextUtils.isEmpty(Area)){
            area.setErrorEnabled(true);
            area.setError("Area Is Required");
        }else{
            isValidarea = true;
        }
        if(TextUtils.isEmpty(Pincode)){
            pincode.setErrorEnabled(true);
            pincode.setError("Please Enter Pincode");
        }else{
            isValidpincode = true;
        }
        if(TextUtils.isEmpty(housenum)){
            houseno.setErrorEnabled(true);
            houseno.setError("Fields Can't Be Empty");
        }else{
            isValidhouseno = true;
        }

        isValid = isValidarea && isValidconfpassword && isValidpassword && isValidpincode && isValidemail && isValidmobilenum && isValidname && isValidhouseno && isValidlname;
        return isValid;


    }
}



