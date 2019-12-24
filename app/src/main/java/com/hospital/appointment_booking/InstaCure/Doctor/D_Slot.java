package com.hospital.appointment_booking.InstaCure.Doctor;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.R;

import java.util.ArrayList;



public class D_Slot extends AppCompatActivity {

    Spinner ss, ts, se, te;
    String s, e, hr, ap, username, password, user_type;
    TextView tvs,tve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_slot);

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");
            password = bb.getString("password");
            user_type = bb.getString("user_type");
        }
        ss = (Spinner) findViewById(R.id.ss);
        ts = (Spinner) findViewById(R.id.ts);
        se = (Spinner) findViewById(R.id.se);
        te = (Spinner) findViewById(R.id.te);
        tvs = (TextView) findViewById(R.id.tv_current_slot_s);
        tve = (TextView) findViewById(R.id.tv_current_slot_e);

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_slot));
        if (y!=null && y.moveToFirst()){
            tvs.setText(y.getString(3));
            tve.setText(y.getString(4));
        }

        ArrayList<String> hour = new ArrayList<>();
        ArrayList<String> ampm = new ArrayList<>();

        String ss1;
        for (int i = 1; i <= 12; i++) {

            ss1 = Integer.toString(i);
            if (ss1.length() == 1)
                ss1 = "0" + ss1;

            hour.add(ss1);
        }

        ampm.add("AM");
        ampm.add("PM");

        ArrayAdapter<String> adapterh = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hour);
        ArrayAdapter<String> adaptera = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ampm);
        adapterh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adaptera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ss.setAdapter(adapterh);
        ts.setAdapter(adaptera);
        se.setAdapter(adapterh);
        te.setAdapter(adaptera);
    }

    public void onClick(View view) {

        s = ss.getSelectedItem().toString();
        s+= " ";
        s += ts.getSelectedItem().toString();
        e = se.getSelectedItem().toString();
        e+= " ";
        e += te.getSelectedItem().toString();

        tvs.setText(s);
        tve.setText(e);

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor y = db.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_slot));

        if (y!=null && y.moveToFirst()) {
            boolean b = db.update_slot(username, password, y.getString(2), s, e, "Y");
            if (b) {
                Message.message(D_Slot.this, "Your Slot Has been Updated");
            } else {
                Message.message(D_Slot.this, "Some Error Occured, Try Again");
            }
        } else {
            boolean b = db.insert_slot(username, password, "", s, e, "Y");
            if (b) {
                Message.message(D_Slot.this, "Your Slot Has been Inserted");
            } else {
                Message.message(D_Slot.this, "Some Error Occured, Try Again");
            }
        }

        db.close();
    }


    //LOGOUT  FROM DOCTOR  | MAINMENU//////////
    @Override
    public  boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        MenuInflater inflater1 =getMenuInflater();
        inflater1.inflate(R.menu.menu_backtomain,menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                startActivity(new Intent(D_Slot.this, Login.class));
                Message.message(D_Slot.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(D_Slot.this, Doctor.class));
                Message.message(D_Slot.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
