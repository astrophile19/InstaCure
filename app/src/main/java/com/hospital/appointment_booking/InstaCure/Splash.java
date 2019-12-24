package com.hospital.appointment_booking.InstaCure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Message.message(Splash.this,"INSTACURE(care for you always)");
        // Splash screen timer
        int SPLASH_TIME_OUT = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(),"WELCOME TO INSTACURE",Toast.LENGTH_SHORT).show();
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}
