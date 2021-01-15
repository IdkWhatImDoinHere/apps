package com.example.gomeds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.database.core.view.View;

public class ChooseOne extends AppCompatActivity {


    Button phar, Customer, DeliveryPerson;
    Intent intent;
    String type;
    ConstraintLayout bgimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.bg), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img3), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img5), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img7), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img8), 1800);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img9), 1800);


        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);

        bgimage = findViewById(R.id.back3);
        bgimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

        phar = (Button) findViewById(R.id.phar);
        DeliveryPerson = (Button) findViewById(R.id.delivery);
        Customer = (Button) findViewById(R.id.customer);


        phar.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (type.equals("Email")) {
                    Intent loginemail = new Intent(ChooseOne.this, Pharlogin.class);
                    startActivity(loginemail);
                    finish();
                }
                if (type.equals("Phone")) {
                    Intent pharloginphone = new Intent(ChooseOne.this, Pharloginphone.class);
                    startActivity(pharloginphone);
                    finish();
                }
                if (type.equals("SignUp")) {
                    Intent Register = new Intent(ChooseOne.this, PharRegistration.class);
                    startActivity(Register);
                }
            }
        });

       Customer.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (type.equals("Email")) {
                    Intent loginemailcust = new Intent(ChooseOne.this, Login.class);
                    startActivity(loginemailcust);
                    finish();
                }
                if (type.equals("Phone")) {
                    Intent loginphonecust = new Intent(ChooseOne.this, Loginphone.class);
                    startActivity(loginphonecust);
                    finish();
                }
                if (type.equals("SignUp")) {
                    Intent Registercust = new Intent(ChooseOne.this, Registration.class);
                    startActivity(Registercust);
                }

            }
        });


        DeliveryPerson.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                if (type.equals("Email")) {
                    Intent loginemail = new Intent(ChooseOne.this, Delivery_Login.class);
                    startActivity(loginemail);
                    finish();
                }
                if (type.equals("Phone")) {
                    Intent loginphone = new Intent(ChooseOne.this, Delivery_Loginphone.class);
                    startActivity(loginphone);
                    finish();
                }
                if (type.equals("SignUp")) {
                    Intent Registerdelivery = new Intent(ChooseOne.this, Delivery_Registration.class);
                    startActivity(Registerdelivery);
                }
            }
        });


    }
}














