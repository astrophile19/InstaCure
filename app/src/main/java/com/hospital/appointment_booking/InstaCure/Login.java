package com.hospital.appointment_booking.InstaCure;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hospital.appointment_booking.InstaCure.Desktop_Admin.Admin;
import com.hospital.appointment_booking.InstaCure.Doctor.Doctor;
import com.hospital.appointment_booking.InstaCure.Patient.Patient;
import com.hospital.appointment_booking.InstaCure.Staff_Member.Staff_Member;


public class Login extends AppCompatActivity {

    EditText username, password;
    String usernames, passwords;
    Button Bregister, Blogin;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        Bregister = (Button) findViewById(R.id.bregister);
        Blogin = (Button) findViewById(R.id.blogin);
        dbh = new DatabaseHelper(this);

        Bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });



        Blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = username.getText().toString();
                passwords = password.getText().toString();
                if (!usernames.matches("^[a-zA-Z0-9_-]{3,15}$")) {

                    username.setError("Invalid Username");

                }
                if (!passwords.matches("^[a-zA-Z0-9_-]{3,15}$")) {

                    password.setError("Invalid  password");

                }
                if  (usernames.equals("") || passwords.equals("")) {
                    Message.message(Login.this, "Please Fill in all your Details");

                }

                Cursor y = dbh.checkduplicates_in_user_credentials(usernames, passwords, getResources().getString(R.string.user_credentials));

                if (y!=null && y.moveToFirst()) {
                    String ut = y.getString(7);
                    Message.message(Login.this, "Welcome");

                    Bundle b = new Bundle();
                    b.putString("username", usernames);
                    b.putString("password", passwords);
                    b.putString("user_type", ut);

                    Intent i;
                    switch (ut) {
                        case "Doctor":
                            i = new Intent(Login.this, Doctor.class);
                            break;
                        case "Patient":
                            i = new Intent(Login.this, Patient.class);
                            break;
                        case "Staff Member":
                            i = new Intent(Login.this, Staff_Member.class);
                            break;
                        default:
                            i = new Intent(Login.this, Admin.class);
                            break;
                    }

                    i.putExtras(b);
                    startActivity(i);
                } else {
                    Message.message(Login.this, "No Such User Exists");
                    Message.message(Login.this, "Please Register Your self");
                }

                if (y != null) {
                    y.close();
                }
            }
        });
        dbh.close();
    }
}
