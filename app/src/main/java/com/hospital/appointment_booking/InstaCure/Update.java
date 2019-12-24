package com.hospital.appointment_booking.InstaCure;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class Update extends AppCompatActivity {

    EditText fname, lname, age, dd, yy, city, pincode, mobno, uname, password;
    String tmp, fnames, lnames, ages, sexs, bgroups, dobs, dds, yys, mms, citys, pincodes, mobnos, unames, passwords, utypes, username, pass, date, month, year;
    Button register;
    Spinner mm, sex, bgroup, usertype;

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");

            pass = bb.getString("password");
        }

        //DEFINING ALL VIEWS
        fname = (EditText) findViewById(R.id.etfname);
        lname = (EditText) findViewById(R.id.etlname);
        age = (EditText) findViewById(R.id.etage);
        dd = (EditText) findViewById(R.id.etdd);
        yy = (EditText) findViewById(R.id.etyy);
        city = (EditText) findViewById(R.id.etcity);
        pincode = (EditText) findViewById(R.id.etpin);
        mobno = (EditText) findViewById(R.id.etmobile);
        uname = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        register = (Button) findViewById(R.id.bregister);
        mm = (Spinner) findViewById(R.id.spinnermonth);
        sex = (Spinner) findViewById(R.id.spinnersex);
        usertype = (Spinner) findViewById(R.id.spinnerusertype);
        bgroup = (Spinner) findViewById(R.id.spinnerbgroup);
        dbh = new DatabaseHelper(this);

        List<String> category = new ArrayList<>();
        category.add("Patient");
        category.add("Doctor");
        category.add("Staff Member");
        category.add("Admin");

        List<String> sexc = new ArrayList<>();
        sexc.add("Male");
        sexc.add("Female");

        List<String> bgroupc = new ArrayList<>();
        bgroupc.add("A+");
        bgroupc.add("A-");
        bgroupc.add("B+");
        bgroupc.add("B-");
        bgroupc.add("O+");
        bgroupc.add("O-");
        bgroupc.add("AB+");
        bgroupc.add("AB-");

        List<String> months = new ArrayList<>();
        months.add("Jan");
        months.add("Feb");
        months.add("Mar");
        months.add("Apr");
        months.add("May");
        months.add("Jun");
        months.add("Jul");
        months.add("Aug");
        months.add("Sep");
        months.add("Oct");
        months.add("Nov");
        months.add("Dec");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, category);
        ArrayAdapter<String> adapterm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bgroupc);
        ArrayAdapter<String> adapterb = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sexc);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        usertype.setAdapter(adapter);
        mm.setAdapter(adapterm);
        sex.setAdapter(adapters);
        bgroup.setAdapter(adapterb);

        DatabaseHelper dh = new DatabaseHelper(this);
        Cursor z = dh.checkduplicates_in_user_credentials(username, pass, getResources().getString(R.string.user_credentials));


        z.moveToFirst();
        fname.setText(z.getString(1));
        lname.setText(z.getString(2));
        age.setText(z.getString(3));
        city.setText(z.getString(8));
        pincode.setText(z.getString(9));
        mobno.setText(z.getString(10));
        uname.setText(z.getString(12));
        password.setText(z.getString(11));


        //SET DOB
        tmp = z.getString(5);
        date = tmp.substring(0, 2);
        month = tmp.substring(3, 6);
        year = tmp.substring(7);

        dd.setText(date);
        yy.setText(year);

        //SET SPINNERS
        tmp = z.getString(4);
        if (tmp.equals("Male")) {
            sex.setSelection(0);
        } else {
            sex.setSelection(1);
        }

        switch (month) {
            case "Jan":
                mm.setSelection(0);
                break;
            case "Feb":
                mm.setSelection(1);
                break;
            case "Mar":
                mm.setSelection(2);
                break;
            case "Apr":
                mm.setSelection(3);
                break;
            case "May":
                mm.setSelection(4);
                break;
            case "June":
                mm.setSelection(5);
                break;
            case "July":
                mm.setSelection(6);
                break;
            case "Aug":
                mm.setSelection(7);
                break;
            case "Sep":
                mm.setSelection(8);
                break;
            case "Oct":
                mm.setSelection(9);
                break;
            case "Nov":
                mm.setSelection(10);
                break;
            default:
                mm.setSelection(11);
                break;
        }

        tmp = z.getString(6);

        switch (tmp) {
            case "A+":
                bgroup.setSelection(0);
                break;
            case "A-":
                bgroup.setSelection(1);
                break;
            case "B+":
                bgroup.setSelection(2);
                break;
            case "B-":
                bgroup.setSelection(3);
                break;
            case "O+":
                bgroup.setSelection(4);
                break;
            case "O-":
                bgroup.setSelection(5);
                break;
            case "AB+":
                bgroup.setSelection(6);
                break;
            case "AB-":
                bgroup.setSelection(7);
                break;
        }

        tmp = z.getString(7);

        switch (tmp) {
            case "Patient":
                usertype.setSelection(0);
                break;
            case "Doctor":
                usertype.setSelection(1);
                break;
            case "Staff Member":
                usertype.setSelection(2);
                break;
            default:
                usertype.setSelection(3);
                break;
        }


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lnames = lname.getText().toString();
                fnames = fname.getText().toString();
                ages = age.getText().toString();
                dds = dd.getText().toString();
                yys = yy.getText().toString();
                citys = city.getText().toString();
                pincodes = pincode.getText().toString();
                unames = uname.getText().toString();
                passwords = password.getText().toString();
                mobnos = mobno.getText().toString();
                mms = mm.getSelectedItem().toString();
                sexs = sex.getSelectedItem().toString();
                bgroups = bgroup.getSelectedItem().toString();
                utypes = usertype.getSelectedItem().toString();

                if (fnames.equals("") || lnames.equals("") || ages.equals("") || dds.equals("") ||
                        yys.equals("") || citys.equals("") || pincodes.equals("") || unames.equals("") ||
                        passwords.equals("") || mobnos.equals("")) {
                    Message.message(Update.this, "Please Fill in all your Details");
                } else {
                    //SETUP DATABASE QUERY
                    if (dds.length() == 1)
                        dds = "0" + dds;
                    dobs = dds + " " + mms + " " + yys;

                    boolean b = dbh.update_user_credentials(username, pass, fnames, lnames, ages, dobs, citys, pincodes, unames, passwords, mobnos, utypes, sexs, bgroups);

                    if (b) {
                        Message.message(Update.this, "User Info Updated Sucessfully");
                        startActivity(new Intent(Update.this,Login.class));
                        Message.message(Update.this,"Please Login");
                        finish();
                    } else {
                        Message.message(Update.this, "Error Occured While Updation");
                    }
                }
                      // startActivity(new Intent());
            }
        });
    }
}
