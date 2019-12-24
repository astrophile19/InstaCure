package com.hospital.appointment_booking.InstaCure;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    Button Bloginregister,BService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SET UP THE DATABASE
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();


        Bloginregister = (Button) findViewById(R.id.bloginregister);
        Bloginregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        BService = findViewById(R.id.services_p);
        BService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Services_provided.class));

            }
        });



    }



    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, Doctors_available.class);
        startActivity(i);
    }
}
